package metier.piece;

public class Tour extends Piece
{
	/**
	 * Constructeur de la Tour
	 * @param lig    ligne   de la Tour.
	 * @param col    colonne de la Tour.
	 * @param metier permet d'obtenir la liste de pièce.
	 */
	public Tour(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur d'une Tour utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Tour(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour une Tour
	 * @param  ligDest ligne   de destination.
	 * @param  colDest colonne de destination.
	 * @return true si le deplacement est celui d'une Tour (forme de +) et false dans tout autre cas.
	 */
	@Override
	public boolean peutDeplacer(int ligDest, int colDest) 
	{
		return  (this.getLig() == ligDest && this.getCol() != colDest || this.getCol() == colDest && this.getLig() != ligDest)
				&&
				!this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest);
	}

	@Override
	public char getSymbole() {return 'T';}
	
}
