package metier.piece;

public class Cavalier extends Piece
{
	/**
	 * Constructeur du Cavalier utilisant la constructeur de Piece
	 * @param lig  : ligne   du Cavalier.
	 * @param col  : colonne du Cavalier.
	 * @param coul : couleur du Cavalier
	 */
	public Cavalier(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur du Cavalier utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Cavalier(Piece p)
	{
		super(p);
	}

	/**
	 * Méthode pour determiner si le deplacement demander est réalisable pour un Cavalier
	 * @param  ligDest : ligne   de destination.
	 * @param  colDest : colonne de destination.
	 * @return true si le deplacement est celui d'un Cavalier (forme de L) et false dans tout autre cas.
	 */
	@Override
	public boolean peutDeplacer(int ligDest, int colDest) 
	{
		return  (this.getLig() + 2 == ligDest || this.getLig() - 2 == ligDest ) &&
                (this.getCol() - 1 == colDest || this.getCol() + 1 == colDest ) ||	
		        (this.getCol() + 2 == colDest || this.getCol() - 2 == colDest ) &&
		        (this.getLig() - 1 == ligDest || this.getLig() + 1 == ligDest );
	}

	/**
	 * Permet de connaître le symbole de la pièce.
	 * @return le charactère symbolisant la pièce.
	 */
	@Override
	public char getSymbole() {return 'C';}
}
