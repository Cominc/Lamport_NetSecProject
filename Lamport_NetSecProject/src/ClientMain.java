import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientMain {
	private final static String AS_ADDRESS = "localhost";
	private final static int PORT = 31;
	
	private final static String SEPARATOR = ":";
	
	private final static String NEW_LINE = "\n";
	private final static String SEND_LABEL = "send:   ";
	private final static String RECIVE_LABEL = "recive: ";
	private final static String CLIENT_START = "Client ready.\n";
	
	public static void main(String[] args) {
		
		Client client = new Client("Alice","alice_pwd");
		//Client client = new Client("Bob","bob_pwd");
		//Trudy cerca di autenticarsi come Alice ma non ne conosce la password
		//Client client = new Client("Alice","trudy_pwd");
		try
		{
			//Creazione socket 
			InetAddress address = InetAddress.getByName(AS_ADDRESS);
			Socket clientSocket = new Socket(address, PORT);
			
			System.out.println(CLIENT_START);
			
			// creazione buffer di scrittura e lettura
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			// Invio messaggio con la propria identità
			out.println(client.getName());
			System.out.println(SEND_LABEL+client.getName()+NEW_LINE);
			
			//Ricezione risposta dal server
			String serverResponse = in.readLine();
			System.out.println(RECIVE_LABEL+serverResponse+NEW_LINE);
			
			//Estraggo dal messaggio ricevuto n e salt (sono separati dal carattere separatore)
			if(serverResponse.contains(SEPARATOR)){
				String salt = serverResponse.substring(serverResponse.indexOf(SEPARATOR)+1);
				int n = Integer.parseInt(serverResponse.substring(0,serverResponse.indexOf(SEPARATOR)));

				String hash = client.computeHashN(n-1, salt);
				
				// Invio messaggio con la risposta al server
				out.println(hash);
				System.out.println(SEND_LABEL+hash+NEW_LINE);
				
				//Esito autenticazione
				serverResponse = in.readLine();
				System.out.println(RECIVE_LABEL+serverResponse+NEW_LINE);
				
			}else {
				System.out.println("No separator found.");
			}

			//SCAMBIO DI MESSAGGI SOLO OGGETTI-------------------------------------------------------------------
			/*
			ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inObj = new ObjectInputStream(clientSocket.getInputStream());
			
			System.out.println(">> "+client.getName()+", "+client.getPassword());
			outObj.writeObject(client);
			
			
			System.out.println("available: "+inObj.available());
			Client obj = (Client)inObj.readObject();
			System.out.println("<< "+obj.getName()+", "+obj.getPassword());
			*/
			//---------------------------------------------------------------------------------------------------
			
			// chiusura socket
			clientSocket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
