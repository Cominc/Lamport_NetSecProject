import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class AuthenticationServerMain {
	private final static int PORT = 31;
	
	public static void main(String[] args)
	{
		HashMap<String, Entry> clients = new HashMap<>();
		//TODO Decidere se mantenere salt
		Entry alice_data = new Entry(5, "E1F53135E559C253", "65B680C59D673A5851F7A4CC6B973FD2FE314505685675D6A3FB36CFC0C4D4CFFC8609E4647BB54A9A2730D78F81BF905CFF7584EED62E92F326C988A5A40742");
		clients.put("Alice", alice_data);
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
