package metier.reseaux;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import controleur.Controleur;

public class ClientToServer extends Thread
{
	private Controleur         ctrl;
	private ObjectOutputStream oos;
	private ObjectInputStream  ois;
	private boolean            running;
	private Socket             socket;
	private Client             client;

	private int                numJoueur;
	


	public ClientToServer(Controleur ctrl, Client client)
	{
		this.ctrl      = ctrl;
		this.client    = client;
	}

	public boolean connect(String ip, int port)
	{
		try
		{
			this.socket = new Socket(ip, port);

			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream (this.socket.getInputStream());

			this.setNumJoueur();

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
		try 
		{
			oos.reset();
			oos.writeObject("disconnect");
			oos.flush();	
		} catch (Exception e) {}
	}

	public void majNbDeplacement()
	{
		try 
		{
			oos.reset();
			oos.writeObject("setNbDeplacement");
			oos.flush();
		} catch (Exception e) {}
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

	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest, int coulPiece)
	{
		try
		{
			oos.reset();
			oos.writeObject("newMovement");
			oos.writeObject(ligDep + ":" + colDep + ":" + ligDest + ":" + colDest + ":" + coulPiece);
			oos.flush();
		}
		catch (Exception e){e.printStackTrace();}
	}

	public void setNumJoueur()
	{
		try 
		{
			oos.reset();
			oos.writeObject("setNumJoueur");
			oos.flush();	
		} catch (Exception e) {}
	}

	public void setNbDeplacement(int nbDeplacement)
	{
		this.client.setNbDeplacement(nbDeplacement);
	}

	public int getNumJoueur() {return this.numJoueur;}

	public boolean estJoueur(){return this.numJoueur == 0 || this.numJoueur == 1;}

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

				if(command.equals("setNbDeplacement"))
					this.setNbDeplacement((int)ois.readObject());

				if(command.equals("disconnect"))
					this.ctrl.fermer();

				if(command.equals("usernameAccepted"))
					JOptionPane.showMessageDialog(null, "Connexion établie");
				
				if(command.equals("usernameRefused"))
				{
					JOptionPane.showMessageDialog(null, "Nom non valide");
				}

				if(this.estJoueur() && command.equals("newMovement"))
				{
					String out = (String)ois.readObject();

					int ligDep  = Integer.parseInt(out.split(":")[0]);
					int colDep  = Integer.parseInt(out.split(":")[1]);
					int ligDest = Integer.parseInt(out.split(":")[2]);
					int colDest = Integer.parseInt(out.split(":")[3]);
					int coulPiece = Integer.parseInt(out.split(":")[4]);
					int nbDeplacement = Integer.parseInt(out.split(":")[5]);

					this.ctrl.majPiece(ligDep, colDep, ligDest, colDest, ! (this.numJoueur == coulPiece) , nbDeplacement);
				}

				if(command.equals("setNumJoueur"))
					this.numJoueur = (int) ois.readObject();
					

				if(command.equals("disconnect"))
					this.ctrl.terminer();
			}
			catch (Exception e){e.printStackTrace();this.ctrl.terminer();}
		}
	}
}