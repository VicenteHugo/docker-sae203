package ihm.partie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PanelJoueur extends JPanel
{
	private Color       coulFond;
	private Color       coulFont;

	private JLabel      pseudo;

	private JPanel      panelPiece;
	private int         indice;

	/**
	 * Constructeur du PanelJoueur
	 * @param coul   : Couleur des pieces du joueur
	 * @param pseudo : pseudo du joueur
	 * @param frame  : Frame sur laquelle est le Panel
	 */
	public PanelJoueur(Color coul, String pseudo, FramePartie frame)
	{
		this.indice = 0;

		this.coulFond  = coul;
		this.coulFont  = coul.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;


		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.10), this.getHeight()));

		this.setBackground(this.coulFond);
		this.setOpaque(true);

		/*----------------*/
		/* Création       */
		/*----------------*/
		this.pseudo = new JLabel(pseudo, JLabel.CENTER);
		this.pseudo.setForeground(this.coulFont);

		this.pseudo.setBackground(coul);


		this.panelPiece = new JPanel(new GridLayout(4, 4, 5, 5));
		/*----------------*/
		/* Positionnement */
		/*----------------*/
		this.add(this.pseudo, BorderLayout.NORTH);
	}
	
	/**
	 * Méthode pour ajouter une image de piece
	 * @param coul : couleur de la piece
	 * @param img  : image de la piece
	 */
	public void ajouterImage(int coul, ImageIcon img)
	{
		this.panelPiece.add(new JLabel(img), indice++);
	}

	/**
	 * Méthode pour mettre à jour le pseudo du joueur
	 * @param pseudo : nouveau pseudo du joueur
	 */
	public void setPseudo(String pseudo)
	{
		this.pseudo.setText(pseudo);
	}
}
