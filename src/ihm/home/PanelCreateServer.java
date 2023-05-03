package ihm.home;

import java.awt.Color;

import javax.swing.*;

import controleur.Controleur;

public class PanelCreateServer extends JPanel
{
	private Controleur ctrl;

	private JTextField txtUsername;
	private JButton    btnCreer;

	/**
	 * Constructeur de PanelCreateServer
	 * @param ctrl : Controleur de l'IHM
	 */
	public PanelCreateServer(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2 , true));
		this.setLayout(null);

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		JLabel lblPseudo = new JLabel("Pseudo : ");
		lblPseudo.setForeground(Color.WHITE);
		lblPseudo.setBounds(25 , 150, 60, 15);

		this.txtUsername = new JTextField(15);
		this.txtUsername.setBounds(90, 150, 120, 15);

		this.btnCreer    = new JButton("Créer un server");
		this.btnCreer.setBounds(25, 200, 185, 60);

		
		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(lblPseudo);
		this.add(this.txtUsername);
		this.add(this.btnCreer);
		
		
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnCreer.addActionListener((e) -> this.creerServer(this.txtUsername.getText()));
	}

	/**
	 * Méthode pour creer un server
	 * @param pseudo : pseudo du joueur créant le server
	 */
	public void creerServer(String pseudo)
	{
		this.ctrl.creerServer(pseudo);
	}
}
