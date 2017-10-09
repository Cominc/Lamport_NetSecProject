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
		Entry alice_data = new Entry(1, "E1F53135E559C253", "IslEV3D1VwUnYAs/6JqlVYK7DDdzhV7b5F1VQ6Y1HJRdxVGcYIGC39ijD00myEIZMInTGTKIwExG/MvZ1Ry5yg==");
		clients.put("Alice", alice_data);
		try
		{
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Authentication Server ready");
			// ciclo infinito, in attesa di connessioni
			while(true)
			{
				// chiamata bloccante, in attesa di una nuova connessione
				Socket client = server.accept();

				// la nuova richiesta viene gestita da un thread indipendente, si ripete il ciclo
				//TODO mi può servire? Connection newConnection = new Connection(client) 
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
