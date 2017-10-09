import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

class Connection extends Thread
{
	private static final String HASH_ALG_CHOOSED = "SHA-512";
	
	private final static String SEPARATOR = ":";
	
	private final static String NEW_LINE = "\n";
	private final static String SEND_LABEL = "send:   ";
	private final static String RECIVE_LABEL = "recive: ";
	private final static String MEX_CLIENT_NOT_FOUND = "Unknown Client.";
	private final static String MEX_AUTH_FAIL = "Authentication fail.";
	private final static String MEX_AUTH_OK = "Authentication Success.";
	
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
			String firstMexSend;
			if(clientToServeData!=null){
				firstMexSend= clientToServeData.getN()+SEPARATOR+clientToServeData.getSalt();
				System.out.println(SEND_LABEL+firstMexSend+NEW_LINE);
				out.println(firstMexSend);
				
				String second_mex_recived = in.readLine();
				System.out.println(RECIVE_LABEL+second_mex_recived+NEW_LINE);
				
				MessageDigest md;
				String hashN ="";
				try {
					md = MessageDigest.getInstance(HASH_ALG_CHOOSED);
					byte[] array = md.digest(second_mex_recived.getBytes());
					array = md.digest(array); 
					//hashN = new String(array);
					hashN = new String(Base64.getDecoder().decode(DatatypeConverter.printBase64Binary(array)));
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(clientToServeData.getHash_n());
				System.out.println(hashN);
				
				if(clientToServeData.getHash_n()==hashN) {
					clientToServeData.setN(clientToServeData.getN()-1);
					clientToServeData.setHash_n(second_mex_recived);
					clients.replace(first_mex_recived, clientToServeData);
					
					System.out.println(SEND_LABEL+MEX_AUTH_OK+NEW_LINE);
					out.println(MEX_AUTH_OK);
				}else {
					System.out.println(SEND_LABEL+MEX_AUTH_FAIL+NEW_LINE);
					out.println(MEX_AUTH_FAIL);
				}
			}else {
				System.out.println(SEND_LABEL+MEX_CLIENT_NOT_FOUND+NEW_LINE);
				out.println(MEX_CLIENT_NOT_FOUND);
			}
			
			//out.flush();
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