import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class AuthenticationServerMain {
	
	private static final String AUTH_SERVER_START = "Authentication Server ready";
	private final static int PORT = 31;
	
	public static void main(String[] args)
	{
		HashMap<String, Entry> clients = new HashMap<>();
		Entry alice_data = new Entry(2, "E1F53135E559C253", "wR+EoB2aPC35/KQxJpN1rSh4nPE44Kzp5af6FjOgBvoNc+S3h7LYCphJvEJ689tCjr9PKPjDYmFzcp5WbKTeKQ==");
		clients.put("Alice", alice_data);
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println(AUTH_SERVER_START);
			// ciclo infinito, in attesa di connessioni
			boolean ctrl = true;
			while(ctrl)
			{
				// chiamata bloccante, in attesa di una nuova connessione
				Socket client = server.accept();

				// la nuova richiesta viene gestita da un thread indipendente
				new Connection(client,clients);
			}
			server.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}