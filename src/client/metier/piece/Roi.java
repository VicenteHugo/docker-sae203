package metier.piece;

import java.util.List;

public class Roi extends Piece
{
	/**
	 * Constructeur du FoRoiu
	 * @param lig ligne   du Roi.
	 * @param col colonne du Roi.
	 */
	public Roi(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}

	/**
	 * Constructeur du roi utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Roi(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour un Roi
	 * @param  ligDest ligne   de destination.
	 * @param  colDest colonne de destination.
	 * @return true si le deplacement est celui d'un Roi (1 case dans toutes les directions) et false dans tout autre cas.
	 */
	public boolean peutDeplacer(int ligDest, int colDest)
	{
		return ((this.getLig() == ligDest && this.getCol() == colDest - 1 || this.getCol() == colDest + 1) ||
		        (this.getCol() == colDest && this.getLig() == ligDest - 1 || this.getLig() == ligDest + 1)  ||
			    (this.getLig() - 1 == ligDest && this.getCol() - 1 == colDest ||
		         this.getLig() - 1 == ligDest && this.getCol() + 1 == colDest ||
		         this.getLig() + 1 == ligDest && this.getCol() - 1 == colDest ||
		         this.getLig() + 1 == ligDest && this.getCol() + 1 == colDest ))
				 &&
				 this.verifPasEchec(this, ligDest, colDest);
	}

	public boolean simulationMouvement(int ligDest, int colDest, List<Piece> lstMouvementSimule){ return false; }

	public char getSymbole(){return 'K';}
}
