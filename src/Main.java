public class Main {

	public static void main(String[] args) {
		try {
			(new Connection()).connect("COM6");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
