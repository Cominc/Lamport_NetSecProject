import java.net.*;
import java.io.*;

public class Client implements Serializable{
	private String name;
	private String password;
	
	public Client(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static void main(String[] args)
	{
		try
		{
			//Creazione socket 
			InetAddress address = InetAddress.getByName("localhost");
			int port = 12345;
			Socket client = new Socket(address, port);
			
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
			ObjectOutputStream outObj = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream inObj = new ObjectInputStream(client.getInputStream());
			
			System.out.println(">> dani, aaab");
			outObj.writeObject(new Client("dani","aaab"));
			
			
			System.out.println("available: "+inObj.available());
			Client obj = (Client)inObj.readObject();
			System.out.println("<< "+obj.getName()+", "+obj.getPassword());
			
			
			//---------------------------------------------------------------------------------------------------
			
			// chiusura socket
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
