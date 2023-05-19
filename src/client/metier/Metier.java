package metier;

import java.util.List;

import controleur.Controleur;

import java.util.ArrayList;

import metier.piece.Cavalier;
import metier.piece.Fou;
import metier.piece.Piece;
import metier.piece.Pion;
import metier.piece.Reine;
import metier.piece.Roi;
import metier.piece.Tour;
import metier.communication.Client;


public class Metier
{
	private Controleur ctrl;

	private Client client;

	private String joueur;

	private List<Piece> lstPieceBlanche;
	private List<Piece> lstPieceNoir   ;

	public boolean peutJouer;

	public Metier(Controleur ctrl)
	{
		this.peutJouer = false;
		this.ctrl = ctrl;
		this.lstPieceBlanche = Metier.generePiece(Piece.BLANC);
		this.lstPieceNoir    = Metier.generePiece(Piece.NOIR );

		Piece.setMetier(this);
	}

	public String getJoueur() {return this.joueur;}

	public void setJoueur(String pseudo)
	{
		if(this.joueur == null)
			this.joueur = pseudo;
	}


	private static List<Piece> generePiece(int coul)
	{
		List<Piece> lst = new ArrayList<Piece>();
		int x   = 0;
		int y   = coul == 1 ? 0 :  7;
		int pas = coul == 1 ? 1 : -1;

		lst.add(new Tour    (x, y, coul));
		x+=1;
		lst.add(new Cavalier(x, y, coul));
		x+=1;
		lst.add(new Fou     (x, y, coul));
		x+=1;
		lst.add(new Reine   (x, y, coul));
		x+=1;
		lst.add(new Roi     (x, y, coul));
		x+=1;
		lst.add(new Fou     (x, y, coul));
		x+=1;
		lst.add(new Cavalier(x, y, coul));
		x+=1;
		lst.add(new Tour    (x, y, coul));

		y+=pas;

		for (int i = 0; i < 8; i++) 
			lst.add(new Pion(x - (1 * i), y, coul));


		return lst;
	}

	public List<Piece> getLstPiece(int coul)
	{
		return coul == Piece.BLANC ? this.lstPieceBlanche : this.lstPieceNoir;
	}

	public void majPiece(int ligDep, int colDep, int ligDest, int colDest, boolean maj, int nbDeplacement)
	{
		this.client.setNbDeplacement(nbDeplacement);

		boolean mange = false;

		if(!maj)
			return;

		Piece pManger = this.getPiece(ligDest, colDest);
		if(pManger != null)
		{
			mange = this.manger(pManger);
		}
			
	
	
		Piece p = this.getPiece(ligDep, colDep);
		if(p == null)
			return;
			
		p.deplacer(ligDest, colDest, mange);
	}

	public void setPeutJouer(boolean peutJouer){System.out.println(peutJouer); this.peutJouer = peutJouer;}

	public void deplacer(int ligDep, int colDep, int ligDest, int colDest)
	{
		if(!this.peutJouer)
		{
			this.client.getPeutDeplacer();
			return;
		}	
		Piece p = this.getPiece(ligDep, colDep);
		
		if(p == null)
			return;


		if(!this.client.mouvementValide(p))
			return;


		Piece pManger = this.getPiece(ligDest, colDest);
		if(pManger != null && p.getCoul() != pManger.getCoul() && p.deplacer(ligDest, colDest, false))
		{
			if(this.manger(pManger))
			{
				this.client.sendMovement(ligDep, colDep, ligDest, colDest, p.getCoul());
				verifEchec(p);
			}
			return;
		}

		if(pManger == null && p.deplacer(ligDest, colDest, false))
		{
			this.client.sendMovement(ligDep, colDep, ligDest, colDest, p.getCoul());
			verifEchec(p);
			return;
		}
		
		return;
	}

	private void verifEchec(Piece pieceBouge)
	{
		Piece roiMechant = null;
		
		if(pieceBouge.getCoul() == Piece.BLANC)
			for (Piece piece : lstPieceNoir)
			{
				if(piece.getSymbole() == 'K')
					roiMechant = piece;
			}
		else
			for (Piece piece : lstPieceBlanche)
			{
				if(piece.getSymbole() == 'K')
						roiMechant = piece;
			}

		if(pieceBouge.peutDeplacer(roiMechant.getLig(), roiMechant.getCol()))
		{
			if(etMath(pieceBouge, roiMechant))
			{
				client.sendMessage("Le Roi "   + (roiMechant.getCoul() == Piece.NOIR?"Noir":"Blanc") + " est en echec et mat le joureur " + (roiMechant.getCoul() == 1?"Blanc":"Noir") + " remporte la partie");
				client.partieFini("Le joueur " + (roiMechant.getCoul() == Piece.NOIR?"Noir":"Blanc") + " a gagner");
			}
			else
				client.sendMessage("Le Roi " + (roiMechant.getCoul() == Piece.NOIR?"Noir":"Blanc") + " est en echec\n");
		}
	}

	private boolean etMath(Piece pieceBouge, Piece roiMechant)
	{
		//sa doit vérifier que le roi peut ce déplacer ( verifie si il ne ce met pas en echec au passage ), que le déplacement qu'il fait est dans le plateau et que le mouvement d'il peut faire ne soit pas sur un alier
		if(roiMechant.peutDeplacer(roiMechant.getLig() + 1, roiMechant.getCol() + 1) && estDansPlateau(roiMechant.getLig() + 1, roiMechant.getCol() + 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() + 1, roiMechant.getCol() + 1) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig() + 1, roiMechant.getCol() - 1) && estDansPlateau(roiMechant.getLig() + 1, roiMechant.getCol() - 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() + 1, roiMechant.getCol() - 1) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig() - 1, roiMechant.getCol() - 1) && estDansPlateau(roiMechant.getLig() - 1, roiMechant.getCol() - 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() - 1, roiMechant.getCol() - 1) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig() - 1, roiMechant.getCol() + 1) && estDansPlateau(roiMechant.getLig() - 1, roiMechant.getCol() + 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() - 1, roiMechant.getCol() + 1) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig() - 1, roiMechant.getCol()    ) && estDansPlateau(roiMechant.getLig() - 1, roiMechant.getCol()    ) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() - 1, roiMechant.getCol()    ) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig() + 1, roiMechant.getCol()    ) && estDansPlateau(roiMechant.getLig() + 1, roiMechant.getCol()    ) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig() + 1, roiMechant.getCol()    ) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig()    , roiMechant.getCol() - 1) && estDansPlateau(roiMechant.getLig()    , roiMechant.getCol() - 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig()    , roiMechant.getCol() - 1) == null ||
		   roiMechant.peutDeplacer(roiMechant.getLig()    , roiMechant.getCol() + 1) && estDansPlateau(roiMechant.getLig()    , roiMechant.getCol() + 1) && roiMechant.estConfondu((roiMechant.getCoul() == Piece.NOIR?this.lstPieceNoir:this.lstPieceBlanche), roiMechant.getLig()    , roiMechant.getCol() + 1) == null   )
		{
			return false;
		}

		int lig     = pieceBouge.getLig();
		int col     = pieceBouge.getCol();
		int ligDest = roiMechant.getLig();
		int colDest = roiMechant.getCol();

		for (int i = 0;
		             !((lig + i) == ligDest && (col + i) == colDest ||
		               (lig + i) == ligDest && (col - i) == colDest ||
		               (lig - i) == ligDest && (col + i) == colDest ||
		               (lig - i) == ligDest && (col - i) == colDest   );
		         i++)
		{
			if(pieceBouge.getCoul() == Piece.BLANC)
				for (Piece piece : lstPieceNoir)
				{
					if(piece.getSymbole() != 'K')
						if(piece.peutDeplacer(lig + i, col + i) ||
						   piece.peutDeplacer(lig - i, col + i) ||
						   piece.peutDeplacer(lig + i, col - i) ||
						   piece.peutDeplacer(lig - i, col - i))
						{
							return false;
						}
				}
			else
				for (Piece piece : lstPieceBlanche)
				{
					if(piece.getSymbole() != 'K')
						if(piece.peutDeplacer(lig + i, col + i) ||
						   piece.peutDeplacer(lig - i, col + i) ||
						   piece.peutDeplacer(lig + i, col - i) ||
						   piece.peutDeplacer(lig - i, col - i))
						{
							return false;
						}
				}
		}

		return true;
	}

	private boolean estDansPlateau(int ligDest, int colDest)
	{
		if(ligDest > 7 || ligDest < 0 || colDest > 7 || colDest < 0)
		{
			return false;
		}

		return true;
	}

	public Piece getPiece(int lig, int col)
	{
		for (Piece piece : lstPieceBlanche)
			if(piece.getLig() == lig && piece.getCol() == col)
				return piece;

		for (Piece piece : lstPieceNoir)
			if(piece.getLig() == lig && piece.getCol() == col)
				return piece;

		return null;
	}

	public boolean manger(Piece p)
	{
		if(p.getCoul() == Piece.BLANC)
			return lstPieceBlanche.remove(p);
		else
			return lstPieceNoir.remove(p);
	}



	public boolean rejoindreServer(String ip, String username)
	{
		this.client = new Client(this.ctrl);
		return this.client.connect(ip, 6666, username);
	}

	public void fermer()
	{
		this.client.disconnect();
	}

	public void envoieMessage(String mess)
	{
		this.client.sendMessage(mess);
	}
}