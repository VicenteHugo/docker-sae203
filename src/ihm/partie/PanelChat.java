package ihm.partie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Controleur;

public class PanelChat extends JPanel implements ActionListener, KeyListener
{
	Controleur ctrl;

	JFrame mere;

	JPanel panelChat;
	JPanel panelChaton;

	JTextField messageEnvoi;
	JButton    envoie;

	JTextArea  chat;

	public PanelChat(Controleur ctrl, JFrame mere)
	{
		this.ctrl = ctrl;
		this.mere = mere;

		this.panelChat   = new JPanel();
		this.panelChaton = new JPanel();

		this.messageEnvoi = new JTextField((int)(this.mere.getSize().getWidth()/10 - 9)/6 - 5);
		this.envoie       = new JButton("Envoyer");

		this.chat = new JTextArea();
		this.chat.setEditable(false);
		this.chat.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);




		this.setLayout(new BorderLayout());

		this.add(panelChat);
		this.panelChat.setLayout(new BorderLayout());
		this.panelChat.add(scrollPane);
		this.panelChat.setBackground(Color.DARK_GRAY);

		this.add(panelChaton, BorderLayout.SOUTH);
		this.panelChaton.add(messageEnvoi);
		this.panelChaton.add(envoie);
		this.panelChaton.setBackground(Color.DARK_GRAY);

		this.envoie.addActionListener(this);
		this.messageEnvoi.addKeyListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!messageEnvoi.getText().equals(""))
		{
			this.ctrl.envoieMessage(this.ctrl.getPseudo() + " : " + this.messageEnvoi.getText() + "\n");
			this.messageEnvoi.setText("");
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Enter"))
		{
			this.ctrl.envoieMessage(this.ctrl.getPseudo() + " : " + this.messageEnvoi.getText() + "\n");
			this.messageEnvoi.setText("");
		}
	}

	public void keyTyped   (KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public void updateChat(String mess)
	{
		this.chat.append(mess);
	}

	public void updateTailleChat()
	{
		this.messageEnvoi.setColumns((int)(this.mere.getSize().getWidth()/10 - 9)/6 - 5);
		this.chat.setColumns((int)(this.mere.getSize().getWidth()/10)/6 - 1);
	}
}
