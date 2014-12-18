import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class SerialReader implements Runnable {
	InputStream in;

	public SerialReader(InputStream in) {
		this.in = in;
	}

	public void run() {
		byte[] buffer = new byte[10];
		int len = -1;
		try {
			PrintStream out = new PrintStream(
					new FileOutputStream("output.txt"));
			//System.setOut(out);
			//System.out.println("citam");
			
			while (true) {
				//System.out.println("citam");
				int k= this.in.read(buffer);
				//System.out.println(k);
				// System.out.println("Received a signal.");
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < k; i++) {
					//System.out.println(i);
					sb.append(String.format("%02X ", buffer[i]));
					sb.append("");
//					out.print((int) (char) (byte) buffer[i]);
//					out.print(" ");
				}
				if(k!=0){
					System.out.println("Reading");
					out.println(sb.toString());
					System.out.println(sb.toString());
				}
			}
			//out.close();
		} catch (IOException e) {
			System.out.println("ovdje pada");
			e.printStackTrace();
		}
	}
}