package metier.piece;

public class Fou extends Piece
{
	/**
	 * Constructeur du Fou
	 * @param lig  : ligne   du Fou.
	 * @param col  : colonne du Fou.
	 * @param coul : couleur du fou.
	 */
	public Fou(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur du Fou utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p : Piece à copier.
	 */
	public Fou(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour un Fou
	 * @param  ligDest : ligne   de destination.
	 * @param  colDest : colonne de destination.
	 * @return true si le deplacement est celui d'un Fou (Diagonale) et false dans tout autre cas.
	 */
	public boolean peutDeplacer(int ligDest, int colDest)
	{
		return  Math.abs(this.getLig() - ligDest) == Math.abs(this.getCol() - colDest)
		        &&
		        !this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest)     ;
	}
/**
	 * Permet de connaître le symbole de la pièce.
	 * @return le charactère symbolisant la pièce.
	 */
	public char getSymbole(){return 'F';}
}
