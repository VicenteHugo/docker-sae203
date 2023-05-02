package metier.reseaux;

import controleur.Controleur;

public class Client 
{
	private ClientToServer clientToServer;

	public Client(Controleur ctrl)
	{
		this.clientToServer = new ClientToServer(ctrl);
	}

	public boolean sendMovement(int ligDep, int colDep, int ligDest, int colDest, int coul)
	{
		this.clientToServer.sendMovement(ligDep, colDep, ligDest, colDest, coul == this.clientToServer.getNumJoueur());
		return coul == this.clientToServer.getNumJoueur();	
	}

	public boolean mouvementValide(int coul)
	{
		return coul == this.clientToServer.getNumJoueur();
	}

	public void disconnect()
	{
		this.clientToServer.disconnect();
	}

	public boolean connect(String ip, int port, String pseudo)
	{
		if(this.clientToServer.isAlive())
			this.clientToServer.disconnect();

		boolean success = this.clientToServer.connect(ip, port);

		if(success)
		{
			this.clientToServer.start();
			this.clientToServer.sendUsername(pseudo);
			this.clientToServer.sendMaj();
		}

		return success;
	}

	public void sendMessage(String mess)
	{
		this.clientToServer.sendMessage(mess);
	}
}
