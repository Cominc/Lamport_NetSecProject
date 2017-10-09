import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

class Connection extends Thread
{
	private final static String SEPARATOR = ":";
	
	private final static String NEW_LINE = "\n";
	private final static String SEND_LABEL = "send:   ";
	private final static String RECIVE_LABEL = "recive: ";
	
	// dichiarazione delle variabili socket e dei buffer
	Socket client;
	
	BufferedReader in;
	PrintWriter out;
	
	HashMap<String, Entry> clients;
	/*
	ObjectOutputStream outObj;
	ObjectInputStream inObj;
	 */
	public Connection(Socket client,HashMap<String, Entry> clients)
	{
		this.client = client;
		this.clients = clients;
		// invoca automaticamente il metodo run()
		this.start();
	}

	public void run()
	{
		try
		{
			System.out.println("Sto servendo il client che ha indirizzo "+client.getInetAddress());
			
			// inizializza i buffer in entrata e uscita per stringhe
			
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
			

			// eventuali elaborazioni e scambi di informazioni con il client
			String first_mex_recived = in.readLine();
			System.out.println(RECIVE_LABEL+first_mex_recived+NEW_LINE);
			
			Entry clientToServeData = clients.get(first_mex_recived);
			if(clientToServeData!=null){
				String firstMexSend= clientToServeData.getN()+SEPARATOR+clientToServeData.getSalt();
				System.out.println(SEND_LABEL+firstMexSend+NEW_LINE);
				out.println(firstMexSend);
				//out.flush();
			}
			// chiusura dei buffer e del socket
			in.close();
 			out.close();
 			
			
			
			/*
			//SCAMBIO DI MESSAGGI SOLO OGGETTI
			outObj = new ObjectOutputStream(client.getOutputStream());
			inObj = new ObjectInputStream(client.getInputStream());
			
			Client objRecived = (Client)inObj.readObject();
			System.out.println(">> "+objRecived.getName()+", "+objRecived.getPassword()+"\n");
			Client objSend = new Client("Server","pwd");
			System.out.println("<< "+objSend.getName()+", "+objSend.getPassword()+"\n");
			outObj.writeObject(objSend);
			*/
 			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}