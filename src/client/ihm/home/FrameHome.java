package ihm.home;

import java.awt.GridLayout;

import javax.swing.*;

import controleur.Controleur;

public class FrameHome extends JFrame 
{
	

	public FrameHome(Controleur ctrl)
	{
		this.setTitle("Chess : Home");
		this.setSize(500, 400);
		this.setLocation((int) (Controleur.DIM_SCREEN.getWidth()/2 -250), (int) (Controleur.DIM_SCREEN.getHeight()/2 -200));
		this.setLayout(new GridLayout(1, 2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);

		this.add(new PanelJoinServer  (ctrl));
	
		this.setVisible(true);
	}
}
