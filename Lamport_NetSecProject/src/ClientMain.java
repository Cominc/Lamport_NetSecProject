import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class ClientMain {
	final static String AS_ADDRESS = "localhost";
	final static int PORT = 31;
	
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
			
			System.out.println("Client ready.\n");
			
			//SCAMBIO DI MESSAGGI SOLO STRINGHE *****************************************************************
			/*
			// creazione buffer di scrittura
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			*/
			//------------------------------------------------------------------------------------
			/*
			 * istruzione commentata riceve testo da inviare come argomento
			 *
			System.out.println("Buffer ready, sending message \""+args[0]+"\"...\n");
			
			// scrittura del messaggio (passato come parametro) nel buffer in uscita
			out.println(args[0]);
			*/
			//------------------------------------------------------------------------------------
			/*
			String messaggio = "Hello server, i'm the client 2";
			System.out.println("Buffer ready, sending message \""+messaggio+"\"...\n");
			
			// scrittura del messaggio (passato come parametro) nel buffer in uscita
			out.println(messaggio);
			
			System.out.println("Message sent.\n");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("<< "+client.getInetAddress()+": "+in.readLine()+"\n");
			*/
			//***************************************************************************************************

			//SCAMBIO DI MESSAGGI SOLO OGGETTI-------------------------------------------------------------------
			ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inObj = new ObjectInputStream(clientSocket.getInputStream());
			
			System.out.println(">> "+client.getName()+", "+client.getPassword());
			outObj.writeObject(client);
			
			
			System.out.println("available: "+inObj.available());
			Client obj = (Client)inObj.readObject();
			System.out.println("<< "+obj.getName()+", "+obj.getPassword());
			
			
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
