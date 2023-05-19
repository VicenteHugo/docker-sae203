package metier.communication;

import controleur.Controleur;
import metier.piece.Piece;

public class Client 
{
	private int nbDeplacement;

	private ClientToServer clientToServer;

	public Client(Controleur ctrl)
	{
		this.clientToServer = new ClientToServer(ctrl, this);
		this.nbDeplacement  = 0;
	}

	public int getNumJoueur(){return this.clientToServer.getNumJoueur();}

	public void sendMovement(int ligDep, int colDep, int ligDest, int colDest, int coulPiece)
	{
		this.clientToServer.sendMovement(ligDep, colDep, ligDest, colDest, coulPiece);
	}

	public boolean mouvementValide(Piece p)
	{
		return this.nbDeplacement % 2 == this.clientToServer.getNumJoueur() && p.getCoul() == this.nbDeplacement % 2;
	}

	public void setNbDeplacement(int nbDeplacement)
	{
		this.nbDeplacement = nbDeplacement;
	}

	public void disconnect()
	{
		this.clientToServer.disconnect();
	}

	public boolean connect(String ip, int port, String pseudo)
	{
		boolean success = this.clientToServer.connect(ip, port);

		if(success)
		{
			this.clientToServer.start();
			this.clientToServer.sendUsername(pseudo);
			this.clientToServer.sendMaj();
		}

		return success;
	}


	public void getPeutDeplacer()
	{
		this.clientToServer.getPeutDeplacer();
	}

	public void sendMessage(String mess)
	{
		this.clientToServer.sendMessage(mess);
	}

	public void partieFini(String messFinPartie)
	{
		clientToServer.partieFini(messFinPartie);
	}
}
