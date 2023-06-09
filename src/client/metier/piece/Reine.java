package metier.piece;

import java.util.List;

public class Reine extends Piece
{
	/**
	 * Constructeur de la Reine
	 * @param lig    ligne   de la Reine.
	 * @param col    colonne de la Reine.
	 * @param metier permet d'obtenir la liste de pièce.
	 */
	public Reine(int lig, int col, int coul)
	{
		super(lig, col, coul);
	}
	
	/**
	 * Constructeur d'une Reine utilisant le constructeur de Piece
	 * Celui-ci permet la copie d'une pièce passer en paramètre
	 * @param p Piece à copier.
	 */
	public Reine(Piece p)
	{
		super(p);
	}

	/**
	 * Permet de determiner si le deplacement demander est réalisable pour une Reine
	 * @param  ligDest ligne   de destination.
	 * @param  colDest colonne de destination.
	 * @return true si le deplacement est celui d'une Reine (diagonale ou ligne droite) et false dans tout autre cas.
	 */
	public boolean peutDeplacer(int ligDest, int colDest)
	{
		        //vérifie si le déplacement, correspond à celui de la tour 
		return (this.getLig() == ligDest && this.getCol() != colDest || this.getCol() == colDest && this.getLig() != ligDest 
		        || 
		        //vérifie si le déplacement, correspond à celui du fou
		        Math.abs(this.getLig() - ligDest) == Math.abs(this.getCol() - colDest)
			   )
				&&
				!this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest)
		        &&
		        this.verifPasEchec(this, ligDest, colDest);
	}

	public boolean simulationMouvement(int ligDest, int colDest, List<Piece> lstMouvementSimule)
	{
		        //vérifie si le déplacement, correspond à celui de la tour 
		return (this.getLig() == ligDest && this.getCol() != colDest || this.getCol() == colDest && this.getLig() != ligDest 
		        || 
		        //vérifie si le déplacement, correspond à celui du fou
		        Math.abs(this.getLig() - ligDest) == Math.abs(this.getCol() - colDest)
			   )
				&&
				!this.autresPieces(Piece.metier.getLstPiece(Piece.BLANC), Piece.metier.getLstPiece(Piece.NOIR), ligDest, colDest, lstMouvementSimule);
	}

	public char getSymbole(){return 'Q';}
}
