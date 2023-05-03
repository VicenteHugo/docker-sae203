package controleur;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JOptionPane;

import ihm.home.FrameHome;
import ihm.partie.FramePartie;
import metier.Metier;
import metier.piece.Piece;

public class Controleur 
{
	public static final Dimension DIM_SCREEN = Toolkit.getDefaultToolkit().getScreenSize();


	private Metier      metier;
	private FrameHome   ihmHome;
	private FramePartie ihmPartie;

	/**
	 * Constructeur du Controleur
	 */
	public Controleur()
	{
		this.metier    = new Metier(this);
		this.ihmHome   = new FrameHome(this);
		this.ihmPartie = null;
	}
	/*-------------*/
	/*   RESEAUX   */
	/*-------------*/

	/**
	 * Méthode pour creer un serveur
	 * @param pseudo : pseudo du premier joueur
	 */
	public void creerServer(String pseudo)
	{
		if(this.registerName(pseudo))
		{
			this.metier .setJoueur(pseudo);
			this.metier .creerServer();
			this.metier .rejoindreServer("localhost", pseudo);
			this.ihmHome.dispose();
			this.ihmHome = null;

			this.ihmPartie = new FramePartie(this, pseudo, null);
			
		}
		this.ihmPartie.setTitle("Echec : " + pseudo);
	}

	/**
	 * Méthode pour rejoindre un serveur
	 * @param pseudo : pseudo du joueur qui rejoint
	 * @param ip     : ip du premier joueur (donc, du serveur)
	 */
	public void rejoindreServer(String pseudo, String ip)
	{
		if(this.registerName(pseudo))
		{
			this.metier.setJoueur(pseudo);
			if(!this.metier.rejoindreServer(ip, pseudo))
			{
				JOptionPane.showMessageDialog(null, "Server introuvable");
				return;
			}

			this.ihmHome.dispose();
			this.ihmHome = null;

			this.ihmPartie = new FramePartie(this, null, null);
		}
		this.ihmPartie.setTitle("Echec : " + pseudo);
	}

	/*-----------------*/
	/*   DEPLACEMENT   */
	/*-----------------*/

	public void majPiece(int ligDep, int colDep, int ligDest, int colDest, boolean maj, int nbDeplacement)
	{
		this.metier.majPiece(ligDep, colDep, ligDest, colDest, maj, nbDeplacement);
		this.ihmPartie.repaint();
	}

	/**
	 * Méthode pour déplacer une piece d'une case, vers une autre
	 * @param ligDep  : ligne   de départ
	 * @param colDep  : colonne de départ
	 * @param ligDest : ligne   de destination
	 * @param colDest : colonne de destination
	 */
	public void deplacer(int ligDep, int colDep, int ligDest, int colDest)
	{
		this.ihmPartie.repaint();
		this.metier.deplacer(ligDep, colDep, ligDest, colDest);
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean registerName(String username)
	{
		if(username.equals(""))
			return false;

		return true;
	}

	/*-----------*/
	/*   FRAME   */
	/*-----------*/

	/**
	 * Méthode pour finir la connexion
	 */
	public void terminer()
	{
		JOptionPane.showMessageDialog(null, "Connexion perdue");
		this.ihmPartie.dispose();
		System.exit(0);
	}

	/**
	 * Méthode pour fermer la Frame
	 */
	public void fermer()
	{
		this.metier.fermer();
	}

	/**
	 * Méthode pour mettre à jour les pseudo des joueurs
	 * @param pseudo1 : Pseudo joueur 1
	 * @param pseudo2 : Pseudo joueur 1
	 */
	public void maj(String pseudo1, String pseudo2)
	{
		this.ihmPartie.setPseudo(pseudo1, pseudo2);
	}

	/**
	 * Méthode pour obtenir la Frame de la partie
	 * @return : FramePartie
	 */
	public FramePartie getFramePartie(){return this.ihmPartie;}
	
	/**
	 * Méthode pour réduire l'application en bas de l'écran
	 */
	public void reduire()
	{
		
	}

	/**
	 * Méthode pour modifier la taille de la Frame
	 */
	public void resize()
	{
		if(this.ihmPartie == null)
			return;

		if(this.ihmPartie.getSize().equals(Controleur.DIM_SCREEN))
			this.ihmPartie.setSize((int) (Controleur.DIM_SCREEN.getWidth() * (2.0/3)), (int) (Controleur.DIM_SCREEN.getHeight() * (2.0/3)));
		else
			this.ihmPartie.setSize(Controleur.DIM_SCREEN);

		this.ihmPartie.setLocation(0, 0);

		this.ihmPartie.updateTaille();
	}

	/**
	 * Méthode pour obtenir la liste des pieces d'une couleur
	 * @return : la liste des pieces
	 */
	public List<Piece> getLstPiece(int coul)
	{
		return this.metier.getLstPiece(coul);
	}

	/**
	 * Méthode pour obtenir le chemin vers l'image correspondant au Symbole et à la Couleur de la piece
	 * @param symb : Symbole de la piece
	 * @param coul : Couleur de la piece
	 * @return : chemin absolu
	 */
	public String getPath(char symb, int coul)
	{
		String sRet = "./donnees/images/";

		if (coul == Piece.BLANC)
			sRet += "blanc/";
		else
			sRet += "noir/";

		switch (symb) 
		{
			case 'C' -> sRet += "Cavalier";
			case 'F' -> sRet += "Fou";
			case 'K' -> sRet += "Roi";
			case 'P' -> sRet += "Pion";
			case 'Q' -> sRet += "Reine";
			case 'T' -> sRet += "Tour";
		}

		return sRet + ".png";
	}

	/**
	 * Méthode pour notre pseudo de joueur
	 * @return : notre pseudo
	 */
	public String getPseudo(){return this.metier.getJoueur();}

	/**
	 * Méthode pour envoyer un message 
	 * @param mess : message à envoyer
	 */
	public void envoieMessage(String mess)
	{
		this.metier.envoieMessage(mess);
	}

	/**
	 * Méthode pour actualiser le chat
	 */
	public void updateChat(String mess)
	{
		this.ihmPartie.updateChat(mess);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
