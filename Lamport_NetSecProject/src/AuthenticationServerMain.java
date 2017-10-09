import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class AuthenticationServerMain {
	private final static int PORT = 31;
	
	public static void main(String[] args)
	{
		HashMap<String, Entry> clients = new HashMap<>();
		//TODO Decidere se mantenere salt
		//Ho dovuto inserire un escape
		Entry alice_data = new Entry(5, "E1F53135E559C253", "e���?g:XQ���k�?��1EhVu֣�6�������	�d{�J�'0�??�?\\�u���.��&Ɉ��B");
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
				//TODO mi pu� servire? Connection newConnection = new Connection(client) 
				//TODO controllare come si accede a clients (deve essere accesso unico, qualunque modifica deve essere vista da tutti)
				new Connection(client,clients);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
