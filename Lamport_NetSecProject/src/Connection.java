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
	private final static String MEX_NEW_SETUP_NEEDED = "Maximum number of authentication reached, you need a new setup.";
	
	// dichiarazione delle variabili socket e dei buffer
	Socket client;
	
	BufferedReader in;
	PrintWriter out;
	
	HashMap<String, Entry> clients;

	public Connection(Socket client,HashMap<String, Entry> clients)
	{
		this.client = client;
		this.clients = clients;
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

			// Scambio messaggi con il client
			String firstMexRecived = in.readLine();
			System.out.println(RECIVE_LABEL+firstMexRecived+NEW_LINE);
			
			Entry clientToServeData = clients.get(firstMexRecived);
			String firstMexSend;
			if(clientToServeData!=null) {
				if(clientToServeData.getN()<2) {
					System.out.println(SEND_LABEL+MEX_NEW_SETUP_NEEDED+NEW_LINE);
					out.println(MEX_NEW_SETUP_NEEDED);
				}else {
					firstMexSend = clientToServeData.getN()+SEPARATOR+clientToServeData.getSalt();
					System.out.println(SEND_LABEL+firstMexSend+NEW_LINE);
					out.println(firstMexSend);
					
					String secondMexRecived = in.readLine();
					System.out.println(RECIVE_LABEL+secondMexRecived+NEW_LINE);
					
					
					//TODO effettuare refactor?
					MessageDigest md;
					String hashN = "";
	
					try {
						md = MessageDigest.getInstance(HASH_ALG_CHOOSED);
						byte[] array = md.digest(Base64.getDecoder().decode(secondMexRecived)); 
						hashN = Base64.getEncoder().encodeToString(array);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(clientToServeData.getHash_n().equals(hashN)) {
						clientToServeData.setN(clientToServeData.getN()-1);
						clientToServeData.setHash_n(secondMexRecived);
						clients.replace(firstMexRecived, clientToServeData);
						
						System.out.println(SEND_LABEL+MEX_AUTH_OK+NEW_LINE);
						out.println(MEX_AUTH_OK);
					}else {
						System.out.println(SEND_LABEL+MEX_AUTH_FAIL+NEW_LINE);
						out.println(MEX_AUTH_FAIL);
					}
				}
			}else {
				System.out.println(SEND_LABEL+MEX_CLIENT_NOT_FOUND+NEW_LINE);
				out.println(MEX_CLIENT_NOT_FOUND);
			}
			
			//out.flush();
			// chiusura dei buffer e del socket
			in.close();
 			out.close();
 			
 			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}