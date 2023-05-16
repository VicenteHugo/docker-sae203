package metier.piece;

import java.util.List;

public class Fou extends Piece
{
	/**
	 * Constructeur du Fou
	 * @param lig    ligne   du Fou.
	 * @param col    colonne du Fou.
	 * @param metier permet d'obtenir la liste de pièce.
	 */
	public Fou(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur du Fou utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Fou(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour un Fou
	 * @param  ligDest ligne   de destination.
	 * @param  colDest colonne de destination.
	 * @return true si le deplacement est celui d'un Fou (Diagonale) et false dans tout autre cas.
	 */
	public boolean peutDeplacer(int ligDest, int colDest)
	{
		return  Math.abs(this.getLig() - ligDest) == Math.abs(this.getCol() - colDest)
		        &&
		        !this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest)
		        &&
		        this.verifPasEchec(this, ligDest, colDest);
	}

	public boolean simulationMouvement(int ligDest, int colDest, List<Piece> lstMouvementSimule)
	{
		return  Math.abs(this.getLig() - ligDest) == Math.abs(this.getCol() - colDest)
		        &&
		        !this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest, lstMouvementSimule);
	}

	public char getSymbole(){return 'F';}
}
