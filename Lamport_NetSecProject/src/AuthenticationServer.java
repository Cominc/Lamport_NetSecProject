import java.net.*;

public class AuthenticationServer {
	public static void main(String[] args)
	{
		try
		{
			int port = 12345;
			ServerSocket server = new ServerSocket(port);

			// ciclo infinito, in attesa di connessioni
			while(true)
			{
				// chiamata bloccante, in attesa di una nuova connessione
				Socket client = server.accept();

				// la nuova richiesta viene gestita da un thread indipendente, si ripete il ciclo
				Connection newConnection = new Connection(client);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
