package metier.reseaux;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerToClient extends Thread
{
	private Server             server;
	private ObjectInputStream  ois;
	private ObjectOutputStream oos;
	private boolean            running;

	public ServerToClient(Server server, Socket socket)
	{
		this.server = server;

		try 
		{
			this.ois     = new ObjectInputStream (socket.getInputStream ());
			this.oos     = new ObjectOutputStream(socket.getOutputStream());
			this.running = true;
		} catch (Exception e) {e.printStackTrace();}
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

	public Server getServer() {return this.server;}
	
	public void setNumJoueur(int numJoueur)
	{
		try
		{
			oos.reset();
			oos.writeObject("setNumJoueur");
			oos.writeObject(numJoueur);
			oos.flush();
		}
		catch(Exception e){}
	}


	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest, int coulPiece, int nbDeplacement)
	{
		System.out.println(nbDeplacement);
		try
		{
			oos.reset();
			oos.writeObject("newMovement");
			oos.writeObject(ligDep + ":" + colDep + ":" + ligDest + ":" + colDest + ":" + coulPiece + ":" + nbDeplacement);
			oos.flush();
		}catch(Exception e){e.printStackTrace();}
	}

	public void setNbDeplacement(int nbDeplacement)
	{
		try
		{
			oos.reset();
			oos.writeObject("setNbDeplacement");
			oos.writeObject(nbDeplacement);
			oos.flush();
		}	
		catch(Exception e){}
	}

	public void maj(String pseudo1, String pseudo2)
	{
		try 
		{
			oos.reset();
			oos.writeObject("maj");
			oos.writeObject(pseudo1);
			oos.writeObject(pseudo2);
			oos.flush();
		} catch (Exception e) {e.printStackTrace();}
	}


	@Override
	public void run()
	{
		while(this.running)
		{
			try 
			{
				String command = (String)ois.readObject();

				if(command.equals("setNbDeplacement"))
					this.setNbDeplacement(this.server.getNbDeplacement());

				if(command.equals("maj"))
				{
					this.server.maj();
				}


				if(command.equals("setUsername"))
				{
					String username = (String)ois.readObject();
					if(this.server.getCtrl().registerName(username))
					{
						oos.writeObject("usernameAccepted");
						oos.flush();

						this.server.setJoueur(username);
					}
					else
					{
						oos.writeObject("usernameRefused");
						oos.flush();
					}
				}

				if(command.equals("newMovement"))
				{
					String  out    = (String)ois.readObject();

					int ligDep  = Integer.parseInt(out.split(":")[0]);
					int colDep  = Integer.parseInt(out.split(":")[1]);
					int ligDest = Integer.parseInt(out.split(":")[2]);
					int colDest = Integer.parseInt(out.split(":")[3]);
					int coulPiece = Integer.parseInt(out.split(":")[4]);

					this.server.broadcastMovement(ligDep, colDep, ligDest, colDest, coulPiece);

				}

				if(command.equals("setNumJoueur"))
					this.setNumJoueur(this.server.getNbJoueur() - 1);

				if(command.equals("disconnect"))
					this.server.disconnect();

			} catch (Exception e) {}
		}
	}

}
