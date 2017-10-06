import java.net.ServerSocket;
import java.net.Socket;


public class AuthenticationServerMain {
	final static int PORT = 31;
	
	public static void main(String[] args)
	{
		try
		{
			ServerSocket server = new ServerSocket(PORT);

			// ciclo infinito, in attesa di connessioni
			while(true)
			{
				// chiamata bloccante, in attesa di una nuova connessione
				Socket client = server.accept();

				// la nuova richiesta viene gestita da un thread indipendente, si ripete il ciclo
				//TODO mi può servire? Connection newConnection = new Connection(client) 
				new Connection(client);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
