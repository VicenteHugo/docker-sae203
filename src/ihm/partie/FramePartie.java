package ihm.partie;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.Controleur;

public class FramePartie extends JFrame
{
	private Controleur       ctrl;

	private PanelBordureHaut panelBordure;

	private PanelPlateau     panelPlateau    ;
	private PanelJoueur      panelJoueurBlanc;
	private PanelJoueur      panelJoueurNoir ;
	private PanelInformation panelInformation;

	private PanelChat        panelChat;

	/**
	 * Constructeur de FramePartie
	 * @param ctrl : Controleur de l'IHM
	 * @param j1   : pseudo du joueur 1
	 * @param j2   : pseudo du joueur 2
	 */
	public FramePartie(Controleur ctrl, String j1, String j2)
	{
	

		/*-----------------------*/
		/* PRINCIPALE            */
		/*-----------------------*/

		this.setUndecorated(true);
		this.ctrl = ctrl;
		

		this.setUndecorated(true);
		this.setSize(Controleur.DIM_SCREEN);
		this.setBackground(Color.BLACK);

		this.setLayout(new BorderLayout());

		/*-------------------*/
		/* Bordure du haut   */
		/*-------------------*/
		//Creation
		this.panelBordure = new PanelBordureHaut(new BorderLayout(), ctrl, this);
		
		//Positionnement
		this.add(this.panelBordure, BorderLayout.NORTH);

		/*----------------*/
		/* Jeu            */
		/*----------------*/
		//Creation
		JPanel panelJeu       = new JPanel(new BorderLayout());
		this.panelPlateau     = new PanelPlateau    (this.ctrl);
		this.panelJoueurBlanc = new PanelJoueur     (Color.WHITE, j1, this);
		this.panelJoueurNoir  = new PanelJoueur     (Color.BLACK, j2, this);
		this.panelInformation = new PanelInformation();

		this.panelChat        = new PanelChat(ctrl, this);

		//Positionnement
		panelJeu.add(this.panelPlateau    , BorderLayout.CENTER);
		panelJeu.add(this.panelJoueurBlanc, BorderLayout.EAST  );
		panelJeu.add(this.panelJoueurNoir , BorderLayout.WEST  );
		panelJeu.add(this.panelInformation, BorderLayout.SOUTH );

		this.add(panelJeu);
		
		this.add(this.panelChat, BorderLayout.EAST);

		this.setVisible(true);
	}	

	/**
	 * Méthode pour changer le titre de la Frame
	 */
	public void setTitle(String title)
	{
		this.panelBordure.setTitle(title);
	}

	/**
	 * Méthode pour changer le pseudo des joueurs
	 */
	public void setPseudo(String pseudo1, String pseudo2)
	{
		this.panelJoueurBlanc.setPseudo(pseudo1);
		this.panelJoueurNoir .setPseudo(pseudo2);
	}

	/**
	 * Méthode pour obtenir la largeur du PanelJoueur
	 * @return : largeur du PanelJoueur
	 */
	public int getWidhtPanelJoueur(){return this.panelJoueurNoir.getWidth();}

	/**
	 * Méthode pour actualiser le chat
	 * @param mess : message ajouter au chat
	 */
	public void updateChat(String mess)
	{
		this.panelChat.updateChat(mess);
	}

	/**
	 * Méthode pour mettre à jour la taille des Panel qui compose la frame
	 */
	public void updateTaille()
	{
		this.panelChat.updateTailleChat();
		this.panelPlateau.changerDecalage();
	}
}