import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String message = "ciao";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] array = md.digest(message.getBytes()); 
			String hex = DatatypeConverter.printHexBinary(array);
			System.out.println(hex);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}          
        
	}

}
