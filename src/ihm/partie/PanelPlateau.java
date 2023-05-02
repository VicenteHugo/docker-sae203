package ihm.partie;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;
import metier.piece.Piece;

public class PanelPlateau extends JPanel 
{
	private Controleur ctrl;
	
	private int cote;

	public PanelPlateau(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setBackground(Color.DARK_GRAY);
		this.setOpaque(true);

		this.addMouseListener(new deplacement());
	}	

	public void dessinerPlateau(Graphics g)
	{
		this.cote = (this.getHeight() - 30) / 8;
		
		int y = 10;
		for (int i = 0; i < 8; i++) 
		{
			int x = (this.getWidth() + 30 - this.getHeight()) / 2;
			for (int j = 0; j < 8; j++) 
			{
				if ((i + j) % 2 == 0)
					g.setColor(new Color(249, 228, 183));
				else
					g.setColor(new Color(89, 102, 67));
				g.fillRect(x, y, cote, cote);
				
				x += cote;
			}	

			y += cote;
		}



		for(Piece p : this.ctrl.getLstPiece(Piece.BLANC))
			this.dessinerPiece(p, g);

		for(Piece p : this.ctrl.getLstPiece(Piece.NOIR))
			this.dessinerPiece(p, g);
	}

	private void dessinerPiece(Piece p, Graphics g)
	{
		int lig = p.getLig();
		int col = p.getCol();

		int x    = (this.getWidth() + 30 - this.getHeight()) / 2;
		int y    = 10;

		x += cote * lig;
		y += cote * col;

		g.drawImage(new ImageIcon(this.ctrl.getPath(p.getSymbole(), p.getCoul())).getImage(), x, y, cote, cote, null);
	}

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		this.dessinerPlateau(g);
	}

	private class deplacement extends MouseAdapter
	{
		private int xDep;
		private int yDep;

		public void mousePressed(MouseEvent e)
		{
			this.xDep = (int) (Math.floor(e.getX() / (cote * 1.0)));
			this.yDep = (int) (Math.floor(e.getY() / (cote * 1.0)));
		}

		public void mouseReleased(MouseEvent e)
		{
			ctrl.deplacer(this.xDep - 2, this.yDep, (int) (Math.floor(e.getX() / (cote * 1.0))) - 2, (int) (Math.floor(e.getY() / (cote * 1.0))));
		}
	}
}
