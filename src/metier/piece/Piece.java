package metier.piece;

import java.util.ArrayList;
import java.util.List;

import metier.Metier;

public abstract class Piece
{
	public static final int BLANC = 0;
	public static final int NOIR  = 1;

	protected static Metier metier;

	private int coul;
	private int lig ;
	private int col ;

	/**
	 * Constructeur de Piece
	 * @param lig ligne   de la piece.
	 * @param col colonne de la piece.
	 */
	public Piece(int lig, int col, int coul)
	{		
		this.coul = coul;
		this.lig  = lig ;
		this.col  = col ;
	}


	/**
	 * Constructeur d'une Piece utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Piece(Piece p)
	{
		this(p.lig, p.col, p.coul);
	}

	/**
	 * Méthode pour déplacer une piece
	 * @param ligDest ligne   de destination.
	 * @param colDest colonne de destination.
	 */
	public boolean deplacer(int ligDest, int colDest, boolean force)
	{
		if(force)
		{
			this.lig = ligDest ;
			this.col = colDest ;
			return true;
		}

		if(ligDest > 7 || ligDest < 0 || colDest > 7 || colDest < 0)
		{
			return false;
		}

		if (this.peutDeplacer(ligDest, colDest))
		{
			this.lig = ligDest ;
			this.col = colDest ;
			return true;
		}

		return false;
	}

	/**
	 * Méthode pour déterminer si une piece est deja à la place de notre piece
	 * @param lstPiece liste des pieces existantes.
	 * @return la piece avec laquelle nous sommes confondus.
	 */
	public Piece estConfondu(List<Piece> lstPiece, int ligDest, int colDest)
	{
		for (Piece piece : lstPiece)
		if(piece != this && piece.lig == ligDest && piece.col == colDest)
			return piece;

		return null;
	}

	/**
	 * Permet de vérifier si un pièce vas être sur le chemin de notre pièce.
	 * @param lstPiece, liste de tout les pièce contenue sur le plateau.
	 * @param ligDest, ligne sur la quelle on veut déplacer la pièce.
	 * @param colDest, colonne sur la quelle on veut déplacer la pièce.
	 * @return si une pièce est sur le chemin la méthode retourne vrai sinon elle retourne faux
	 */
	public boolean autresPieces(List<Piece> lstPieceBlanc, List<Piece> lstPieceNoir, int ligDest, int colDest)
	{
		int ligPas = 0;
		int colPas = 0;

		if (this.lig > ligDest) ligPas = -1;
		if (this.lig < ligDest) ligPas =  1;
		if (this.col > colDest) colPas = -1;
		if (this.col < colDest) colPas =  1;


		for (int cptLig = this.lig + ligPas, cptCol = this.col + colPas ;
		     !(cptCol == colDest && cptLig == ligDest)                  ; 
			 cptLig += ligPas, cptCol += colPas                           )
		{
			for (Piece p: lstPieceBlanc)
				if (p.lig == cptLig && p.col == cptCol)
					return true;
			
			for (Piece p : lstPieceNoir)
				if (p.lig == cptLig && p.col == cptCol)
					return true;

		}

		return false;
	}

	public boolean autresPieces(List<Piece> lstPieceBlanc, List<Piece> lstPieceNoir, int ligDest, int colDest, List<Piece> lstMouvementSimule)
	{
		int ligPas = 0;
		int colPas = 0;

		if (this.lig > ligDest) ligPas = -1;
		if (this.lig < ligDest) ligPas =  1;
		if (this.col > colDest) colPas = -1;
		if (this.col < colDest) colPas =  1;


		for (int cptLig = this.lig + ligPas, cptCol = this.col + colPas ;
		     !(cptCol == colDest && cptLig == ligDest)                  ; 
			 cptLig += ligPas, cptCol += colPas                           )
		{
			if(lstMouvementSimule.get(0).getCoul() == Piece.BLANC)
			{
				for (Piece p: lstMouvementSimule)
					if (p.lig == cptLig && p.col == cptCol)
						return true;
			
				for(Piece p : lstPieceNoir)
					if (p.lig == cptLig && p.col == cptCol)
						return true;
			}
			else
			{
				for (Piece p: lstPieceBlanc)
					if (p.lig == cptLig && p.col == cptCol)
						return true;
			
				for(Piece p : lstMouvementSimule)
					if (p.lig == cptLig && p.col == cptCol)
						return true;
			}
		}

		return false;
	}

	/**
	 * Permet de connaître la couleur de la pièce
	 * @return la couleur de la pièce
	 */
	public int getCoul() { return this.coul ;}


	/**
	 * Permet de connaître la colonne actuel de la pièce
	 * @return un int qui représente la colonne actuel de la pièce
	 */
	public int getCol() { return this.col ;}

	/**
	 * Permet de connaître la ligne actuel de la pièce
	 * @return un int qui représente la ligne actuel de la pièce
	 */
	public int getLig() { return this.lig ;}

	public static void setMetier(Metier metier)
	{
		Piece.metier = metier;
	}


	/**
	 * Permet de vérifier si une pièce peut se déplacer à la position de lig et col.
	 * @param lig, ligne sur la quelle on veut déplacer la pièce.
	 * @param col, colonne sur la quelle on veut déplacer la pièce.
	 * @return si la piece peut se deplacer
	 */
	public abstract boolean peutDeplacer(int lig, int col);

	public abstract boolean simulationMouvement(int lig, int col, List<Piece> lstMouvementSimule);

	/**
	 * Permet de connaître le symbole de la pièce.
	 * @return le charactère symbolisant la pièce.
	 */
	public abstract char getSymbole();

	public boolean verifPasEchec(Piece pieceBouge, int ligDest, int colDest)
	{
		Piece monRoi = null;
		List<Piece> lstMouvementSimule = new ArrayList<Piece>();

		for (Piece piece : metier.getLstPiece(pieceBouge.getCoul()))
		{
			if(piece != pieceBouge)
				lstMouvementSimule.add(piece);

			if(piece.getSymbole() == 'K')
				monRoi = piece;
		}

		Piece mouvementSimule = new Pion(ligDest, colDest, pieceBouge.getCoul());
		lstMouvementSimule.add(mouvementSimule);


		if(pieceBouge.getSymbole() == 'K')
			for (Piece piece : metier.getLstPiece((pieceBouge.getCoul() == Piece.BLANC ? Piece.NOIR : Piece.BLANC)))
			{
				if(piece.simulationMouvement(ligDest, colDest, lstMouvementSimule) && !(piece.lig == mouvementSimule.lig && piece.col == mouvementSimule.col))
				{
					return false;
				}
			}
//si autre chose que roi est bouger
		else
			for (Piece piece : metier.getLstPiece((pieceBouge.getCoul() == Piece.BLANC ? Piece.NOIR : Piece.BLANC)))
			{
				if(piece.simulationMouvement(monRoi.getLig(), monRoi.getCol(), lstMouvementSimule) && !(piece.lig == mouvementSimule.lig && piece.col == mouvementSimule.col))
				{
					return false;
				}
				else
					System.out.println("type : " + piece.getSymbole() + (!(piece.lig == mouvementSimule.lig && piece.col == mouvementSimule.col)) + "\n");
			}

		return true;
	}
}