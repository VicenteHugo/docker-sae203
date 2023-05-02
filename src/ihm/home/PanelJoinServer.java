package ihm.home;

import java.awt.Color;

import javax.swing.*;

import controleur.Controleur;

public class PanelJoinServer extends JPanel
{
	private Controleur ctrl;

	private JTextField txtIp;
	private JTextField txtUsername;
	private JButton    btnRejoindre;

	public PanelJoinServer(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2 , true));
		this.setLayout(null);

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		JLabel lblIp = new JLabel("Ip : ");
		lblIp.setForeground(Color.WHITE);
		lblIp.setBounds(55 , 100, 60, 15);

		this.txtIp = new JTextField(15);
		this.txtIp.setBounds(90, 100, 120, 15);



		JLabel lblPseudo = new JLabel("Pseudo : ");
		lblPseudo.setForeground(Color.WHITE);
		lblPseudo.setBounds(25 , 150, 60, 15);

		this.txtUsername = new JTextField(15);
		this.txtUsername.setBounds(90, 150, 120, 15);

		this.btnRejoindre    = new JButton("rejoindre un server");
		this.btnRejoindre.setBounds(25, 200, 185, 60);

		
		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(lblIp);
		this.add(this.txtIp);
		this.add(lblPseudo);
		this.add(this.txtUsername);
		this.add(this.btnRejoindre);
		
		
		/*---------------------------*/
		/* Activation des composants */
		/*---------------------------*/
		this.btnRejoindre.addActionListener((e) -> this.rejoindreServer(this.txtUsername.getText(), this.txtIp.getText()));
	}

	public void rejoindreServer(String pseudo, String ip)
	{
		this.ctrl.rejoindreServer(pseudo, ip);
	}
}
