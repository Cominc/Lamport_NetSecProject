import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Connection extends Thread
{
	// dichiarazione delle variabili socket e dei buffer
	Socket client;
	
	BufferedReader in;
	PrintWriter out;
	
	ObjectOutputStream outObj;
	ObjectInputStream inObj;

	public Connection(Socket client)
	{
		this.client = client;

		// invoca automaticamente il metodo run()
		this.start();
	}

	public void run()
	{
		try
		{
			System.out.println("Sto servendo il client che ha indirizzo "+client.getInetAddress());
			/*
			// inizializza i buffer in entrata e uscita per stringhe
			
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			

			// eventuali elaborazioni e scambi di informazioni con il client

			System.out.println(">> "+client.getInetAddress()+": "+in.readLine()+"\n");
			
			System.out.println("<< Ciao client");
			out.println("<< Ciao client");
			//out.flush();
			// chiusura dei buffer e del socket
			in.close();
 			out.close();
 			*/
			outObj = new ObjectOutputStream(client.getOutputStream());
			inObj = new ObjectInputStream(client.getInputStream());
			
			Client objRecived = (Client)inObj.readObject();
			System.out.println(">> "+objRecived.getName()+", "+objRecived.getPassword()+"\n");
			Client objSend = new Client("Server","pwd");
			System.out.println("<< "+objSend.getName()+", "+objSend.getPassword()+"\n");
			outObj.writeObject(objSend);
 			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}