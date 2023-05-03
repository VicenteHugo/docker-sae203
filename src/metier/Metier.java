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
import metier.reseaux.Client;
import metier.reseaux.Server;


public class Metier
{
	private Controleur ctrl;

	private Server server;
	private Client client;

	private String joueur;

	private List<Piece> lstPieceBlanche;
	private List<Piece> lstPieceNoir   ;

	public Metier(Controleur ctrl)
	{
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


	public void deplacer(int ligDep, int colDep, int ligDest, int colDest)
	{
		Piece p = this.getPiece(ligDep, colDep);
		
		if(p == null)
			return;


		if(!this.client.mouvementValide(p))
			return;


		Piece pManger = this.getPiece(ligDest, colDest);
		if(pManger != null && p.getCoul() != pManger.getCoul() && p.deplacer(ligDest, colDest, false))
		{
			if(this.manger(pManger))
				this.client.sendMovement(ligDep, colDep, ligDest, colDest, p.getCoul());
			return;
		}
		
		if(pManger == null && p.deplacer(ligDest, colDest, false))
		{
			this.client.sendMovement(ligDep, colDep, ligDest, colDest, p.getCoul());
			return;
		}

		return;
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


	/*RESEAUX */
	public void creerServer()
	{
		this.server = new Server(this.ctrl);
		this.server.start();
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