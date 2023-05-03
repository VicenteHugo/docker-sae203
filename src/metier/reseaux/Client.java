package metier.reseaux;

import controleur.Controleur;
import metier.piece.Piece;

public class Client 
{
	private int nbDeplacement;

	private ClientToServer clientToServer;

	/**
	 * Constructeur de Client
	 * @param ctrl : Controleur du metier
	 */
	public Client(Controleur ctrl)
	{
		this.clientToServer = new ClientToServer(ctrl, this);
		this.nbDeplacement  = 0;
	}

	/**
	 * Méthode pour obtenir le numéro du joueur
	 * @return le numero du joueur
	 */
	public int getNumJoueur(){return this.clientToServer.getNumJoueur();}

	/**
	 * Méthode pour envoyer le mouvement d'une piece
	 * @param ligDep    : ligne   d'origine de la piece
	 * @param colDep    : colonne d'origine de la piece
	 * @param ligDest   : ligne   de destination de la piece
	 * @param colDest   : colonne de destination de la piece
	 * @param coulPiece : couleur de la piece
	 */
	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest, int coulPiece)
	{
		this.clientToServer.sendMovement(ligDep, colDep, ligDest, colDest, coulPiece);
	}

	/**
	 * Méthode pour savoir si le mouvement est valite
	 * @param p : piece à valider
	 * @return true si le mouvement est bon, false sinon
	 */
	public boolean mouvementValide(Piece p)
	{
		return this.nbDeplacement % 2 == this.clientToServer.getNumJoueur() && p.getCoul() == this.nbDeplacement % 2 ;
	}

	/**
	 * Méthode pour définir le nombre de coup fait
	 * @param nbDeplacement : somme des coup realiser sur le plateau
	 */
	public void setNbDeplacement(int nbDeplacement)
	{
		this.nbDeplacement = nbDeplacement;
	}

	/**
	 * Méthode pour deconnecter le Client
	 */
	public void disconnect()
	{
		this.clientToServer.disconnect();
	}

	/**
	 * Méthode pour connecter le Client
	 * @param ip     : adresse ip du serveur
	 * @param port   : port du serveur
	 * @param pseudo : pseudo du joueur
	 * @return true si la connexion s'est effectuée, false sinon
	 */
	public boolean connect(String ip, int port, String pseudo)
	{
		boolean success = this.clientToServer.connect(ip, port);

		if(success)
		{
			this.clientToServer.start();
			this.clientToServer.sendUsername(pseudo);
			this.clientToServer.sendMaj();
		}

		return success;
	}

	/**
	 * Méthode pour envoyer un message
	 * @param mess :  message à envoyer
	 */
	public void sendMessage(String mess)
	{
		this.clientToServer.sendMessage(mess);
	}
}
