import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;



public class Main {
	private static final String PASSWORD_ALICE = "password";
	private static final String HASH_ALG_CHOOSED = "SHA-512";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client alice = new Client("Alice",PASSWORD_ALICE);
		int n;
		Scanner reader = new Scanner(System.in);
		do{
			System.out.println("Enter a number: ");
			n = reader.nextInt();
			System.out.println(repeatedHash(n,alice.getPassword()));
		}while(n!=0);
		reader.close();
	}
	
	private static String repeatedHash(int n, String message){
		MessageDigest md;
		String hex ="";
		try {
			md = MessageDigest.getInstance(HASH_ALG_CHOOSED);
			//byte[] array = md.digest(message.getBytes());
			byte[] array = message.getBytes();
			for(int i=0; i< n; i++)
				array = md.digest(array); 
			hex = DatatypeConverter.printHexBinary(array);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hex;
	}

}
