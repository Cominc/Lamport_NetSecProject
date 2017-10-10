
public class Entry {
	
	private int n;
	private String salt;
	private String hashN;

	public Entry(int n, String salt, String hashN) {
		super();
		this.n = n;
		this.salt = salt;
		this.hashN = hashN;
	}
	
	public int getN() {
		return n;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getHashN() {
		return hashN;
	}
	
	public void setHashN(String hashN) {
		this.hashN = hashN;
	}

}