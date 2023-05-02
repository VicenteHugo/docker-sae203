package metier.reseaux;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import controleur.Controleur;

public class ClientToServer extends Thread
{
	private static int nbJoueur = 0;

	private Controleur         ctrl;
	private ObjectOutputStream oos;
	private ObjectInputStream  ois;
	private boolean            running;
	private Socket             socket;

	private int                numJoueur;
	


	public ClientToServer(Controleur ctrl)
	{
		this.ctrl      = ctrl;
		this.numJoueur  = 0;
	}

	public boolean connect(String ip, int port)
	{
		try
		{
			this.socket = new Socket(ip, port);

			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream (this.socket.getInputStream());

			this.running = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void disconnect()
	{
		this.running = false;
		try {this.socket.close();} catch (Exception e) {}
	}

	public void sendMaj()
	{
		try 
		{
			oos.reset();
			oos.writeObject("maj");
			oos.flush();	
		} catch (Exception e) {e.printStackTrace();}
	}

	public void sendUsername(String username)
	{
		try
		{
			oos.reset();
			oos.writeObject("setUsername");
			oos.writeObject(username);
			oos.flush();
		}
		catch (Exception e){e.printStackTrace();}
	}

	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest, boolean valide)
	{
		try
		{
			oos.reset();
			oos.writeObject("newMovement");
			oos.writeObject(ligDep + ":" + colDep + ":" + ligDest + ":" + colDest);
			oos.writeObject(valide);
			oos.flush();
		}
		catch (Exception e){e.printStackTrace();}
	}

	public int getNumJoueur() {return this.numJoueur;}

	public boolean estJoueur(){return this.numJoueur == 0 && this.numJoueur == 1;}

	public void setJoueur(){this.numJoueur = nbJoueur++;}

	@Override
	public void run()
	{
		while(this.running)
		{
			try
			{
				String command = (String)ois.readObject();
				
				if(command.equals("maj"))
				{
					String pseudo1 = (String)ois.readObject(); 
					String pseudo2 = (String)ois.readObject(); 
					this.ctrl.maj(pseudo1, pseudo2);
				}


				if(command.equals("disconnect"))
					this.disconnect();

				if(command.equals("usernameAccepted"))
					JOptionPane.showMessageDialog(null, "Connexion établie");
				
				if(command.equals("usernameRefused"))
				{
					JOptionPane.showMessageDialog(null, "Nom non valide");
					this.disconnect();
				}

				if(this.estJoueur() && command.equals("newMovement"))
				{
					String out = (String)ois.readObject();

					int ligDep  = Integer.parseInt(out.split(":")[0]);
					int colDep  = Integer.parseInt(out.split(":")[1]);
					int ligDest = Integer.parseInt(out.split(":")[2]);
					int colDest = Integer.parseInt(out.split(":")[3]);

					this.ctrl.deplacer(ligDep, colDep, ligDest, colDest);
				}

				if(command.equals("setJoueur"))
					this.setJoueur();
					
			}
			catch (Exception e){e.printStackTrace();this.disconnect();}
		}
	}
}