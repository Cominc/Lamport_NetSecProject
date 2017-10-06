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
			
			// scrittura del messaggio nel buffer in uscita
			out.println(client.getName());
			
			String serverResponse = in.readLine();
			System.out.println("<< "+clientSocket.getInetAddress()+": "+serverResponse+"\n");
			
			//***************************************************************************************************

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
