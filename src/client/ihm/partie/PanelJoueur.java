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
	
	
	public void ajouterImage(int coul, ImageIcon img)
	{
		this.panelPiece.add(new JLabel(img), indice++);
	}

	public void setPseudo(String pseudo)
	{
		this.pseudo.setText(pseudo);
	}
}
