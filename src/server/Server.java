import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server extends Thread
{
	public static int nbJoueur      = 0;
	
	private int nbDeplacement = 0;
	
	private ServerSocket         serverSocket;
	private List<ServerToClient> lstServerToClient;
	private boolean              running;

	private String               pseudoBlanc;
	private String               pseudoNoir;

	public Controleur ctrl;

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

	public void disconnect()
	{
		this.lstServerToClient.get(0).disconnect();
	}

	public int getNbDeplacement(){return this.nbDeplacement;}

	public int getNbJoueur(){return this.lstServerToClient.size();}

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
			} catch (Exception e) {e.printStackTrace();}
		}
	}

	public void broadcastMovement(int ligDep, int colDep, int ligDest, int colDest, int coulPiece)
	{
		this.nbDeplacement++;
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.sendMovement(ligDep, colDep, ligDest, colDest, coulPiece, this.nbDeplacement);
	}

	public void maj()
	{
		for (ServerToClient serverToClient : lstServerToClient)
			serverToClient.maj(this.pseudoBlanc, this.pseudoNoir);
	} 

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
