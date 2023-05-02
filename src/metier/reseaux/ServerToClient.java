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

	public Server getServer() {return this.server;}

	public void disconnect()
	{
		this.running = false;
	}
	
	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest)
	{
		try
		{
			oos.reset();
			oos.writeObject("newMovement");
			oos.writeObject(ligDep + ":" + colDep + ":" + ligDest + ":" + colDest);
			oos.flush();
		}catch(Exception e){e.printStackTrace();}
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

	public void setJoueur()
	{
		try
		{	
			System.out.println("estJoueur");
			oos.reset();
			oos.writeObject("setJoueur");
			oos.flush();
		}	
		catch(Exception e){e.printStackTrace();}
	}

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
					System.out.println("maj");
					this.server.maj();
				}

				if(command.equals("disconnect"))
				{
					this.disconnect();
					break;
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
						this.disconnect();
					}
				}

				if(command.equals("newMovement"))
				{

					String  out    = (String)ois.readObject();
					boolean valide = (boolean)ois.readObject();

					System.out.println(valide);

					int ligDep  = Integer.parseInt(out.split(":")[0]);
					int colDep  = Integer.parseInt(out.split(":")[1]);
					int ligDest = Integer.parseInt(out.split(":")[2]);
					int colDest = Integer.parseInt(out.split(":")[3]);

					if(valide)
						this.server.broadcastMovement(ligDep, colDep, ligDest, colDest);
				}

				if(command.equals("newMessage"))
				{
					this.server.broadcastMessage((String)ois.readObject());
				}

			} catch (Exception e) {}
		}
	}

	public void sendMessage(String mess)
	{
		try
		{
			oos.reset();
			oos.writeObject("newMessage");
			oos.writeObject(mess);
			oos.flush();
		}
		catch (Exception e){e.printStackTrace();}
	}

}
