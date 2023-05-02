package metier.reseaux;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;

public class Server extends Thread
{
	private ServerSocket         serverSocket;
	private List<ServerToClient> lstServerToClient;
	private Controleur           ctrl;	
	private boolean              running;

	private String               pseudoBlanc;
	private String               pseudoNoir;

	public Server(Controleur ctrl)
	{
		this.ctrl = ctrl;

		try
		{
			this.serverSocket   = new ServerSocket(6666);
			this.lstServerToClient = new ArrayList<ServerToClient>(2);
			this.running        = true;
		}
		catch (Exception e){e.printStackTrace();}
	}

	@Override
	public void run()
	{
		while(this.running)
		{
			try 
			{
				Socket         socket         = this.serverSocket.accept();
				ServerToClient serverToClient = new ServerToClient(this, socket);
				
				this.lstServerToClient.add(serverToClient);
				serverToClient.start();	
				if(this.lstServerToClient.size() < 3)
					serverToClient.setJoueur();
			} catch (Exception e) {e.printStackTrace();}
		}
	}

	public void broadcastMovement(int ligDep, int colDep, int ligDest, int colDest)
	{
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.sendMovement(ligDep, colDep, ligDest, colDest);
	}

	public void maj()
	{
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.maj(this.pseudoBlanc, this.pseudoNoir);
	} 

	public void closeServer()
	{
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.disconnect();

		this.running = false;

		try 
		{
			this.serverSocket.close();
		} catch (Exception e) {e.printStackTrace();}
	}

	public Controleur getCtrl(){return this.ctrl;}

	public void setJoueur(String username) 
	{
		if(this.pseudoBlanc == null)
			this.pseudoBlanc = username;
		else if (this.pseudoNoir == null)
			this.pseudoNoir = username;
	}

	public void broadcastMessage(String mess)
	{
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.sendMessage(mess);
	}
}
