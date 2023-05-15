public class Controleur 
{
	private Server server;

	public Controleur()
	{
		

		this.server = new Server(this);
		this.server.start();
	}


	public boolean registerName(String name)
	{
		return true;
	}

	public static void main(String[] args) 
	{
		new Controleur();
	}
}
