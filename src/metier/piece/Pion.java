package metier.piece;


public class Pion extends Piece
{
	private int nbDeplacement = 0;
	/**
	 * Constructeur du Pion
	 * @param lig  : ligne   du Pion.
	 * @param col  : colonne du Pion.
	 * @param coul : couleur du pion
	 */
	public Pion(int lig, int col, int coul) 
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur d'un Pion utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Pion(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour un Pion
	 * @param  ligDest ligne   de destination.
	 * @param  colDest colonne de destination.
	 * @return true si le deplacement est celui d'un Pion (1 en haut ou diagonal vers le haut) et false dans tout autre cas.
	 */
	@Override
	public boolean peutDeplacer(int ligDest, int colDest) 
	{
		int coul = this.getCoul() == Piece.BLANC ? Piece.NOIR : Piece.BLANC;

		int pas = this.getCoul() == Piece.NOIR ? 1 : -1;

		if(nbDeplacement == 0 && this.getLig() == ligDest && this.getCol() + pas * 2 == colDest && this.estConfondu(Piece.metier.getLstPiece(Piece.BLANC), ligDest, colDest) == null && this.estConfondu(Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest) == null)
		{
			this.nbDeplacement++;
			return true;
		}	

		if  (
				this.getCol() + pas == colDest &&
		        (
					this.getLig() == ligDest && this.estConfondu(Piece.metier.getLstPiece(coul), ligDest, colDest) == null 
					||
					(
						(this.getLig() + 1 == ligDest || this.getLig() - 1 == ligDest) 
						&&
						this.estConfondu(Piece.metier.getLstPiece(coul), ligDest, colDest) != null
					)
				)
			)
		{
			this.nbDeplacement++;
			return true;
		}

		return false;
	}

	/**
	 * Permet de connaître le symbole de la pièce.
	 * @return le charactère symbolisant la pièce.
	 */
	@Override
	public char getSymbole() {return 'P';}
		
}
