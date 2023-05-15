package ihm.partie;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;

public class PanelBordureHaut extends JPanel
{
	private JLabel      title;
	private FramePartie frame;

	public PanelBordureHaut(BorderLayout lt, Controleur ctrl, FramePartie frame)
	{
		super(lt);

		this.frame = frame;
		this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		this.setBackground(Color.GRAY);
		this.setOpaque(true);


		this.title = new JLabel("Echec");
		this.title.setForeground(Color.WHITE);
		
		JPanel panel2 = new JPanel(new GridLayout(1,3));
		JButton btnFermer = new JButton("X");
		btnFermer.setBackground(Color.RED);
		btnFermer.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JButton btnReduire = new JButton("━");
		btnReduire.setBackground(Color.WHITE);
		btnReduire.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JButton btnResize = new JButton("□");
		btnResize.setBackground(Color.WHITE);
		btnResize.setCursor(new Cursor(Cursor.HAND_CURSOR));

		
		//Positionnement
		panel2.add(btnReduire);
		panel2.add(btnResize);
		panel2.add(btnFermer);

		this.add(this.title, BorderLayout.WEST);
		this.add(panel2    , BorderLayout.EAST);
		
		
		//Activation
		btnReduire.addActionListener((e) -> ctrl.reduire());
		btnResize .addActionListener((e) -> ctrl.resize() );
		btnFermer .addActionListener((e) -> ctrl.fermer() );

		GereBordure gb = new GereBordure();
		this.addMouseMotionListener(gb);
	}

	public void setTitle(String title)
	{
		if(title == null)
		{
			this.title.setText("");
			return;
		}

		this.title.setText(title);
	}

	private class GereBordure extends MouseAdapter
	{
		private int xDep;
		private int yDep;

		public void mouseMoved(MouseEvent e)
		{
			this.xDep = e.getX();
			this.yDep = e.getY();
		}

		public void mouseDragged(MouseEvent e)
		{
			int x = e.getXOnScreen() - this.xDep;
			int y = e.getYOnScreen() - this.yDep;

			if(x < 0)
				x = 0;

			if(y < 0)
				y = 0;

			if(x + frame.getWidth() > Controleur.DIM_SCREEN.getWidth())
				x = (int)Controleur.DIM_SCREEN.getWidth() - frame.getWidth();

			if(y + frame.getHeight() > Controleur.DIM_SCREEN.getHeight())
				y = (int)Controleur.DIM_SCREEN.getHeight() - frame.getHeight();

			PanelBordureHaut.this.frame.setLocation(x, y);
		}
	}
}
