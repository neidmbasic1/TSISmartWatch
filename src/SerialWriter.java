import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class SerialWriter implements Runnable {
	OutputStream out;

	public SerialWriter(OutputStream out) {
		this.out = out;
	}

	public void run() {
		try {
//			this.out.write(new byte[] { (byte) 255, 0x30, 0x04,0x00 });
//			this.out.flush();
//			Thread.sleep(500);
//			this.out.write(new byte[] { (byte) 255, 0x31, 0x04,0x00 });
//			this.out.flush();
//			Thread.sleep(500);
			byte[] byteToSend=new byte[] { (byte) 255, 0x07, 0x04,0x00 };
			System.out.println("sending");
			System.out.println(Arrays.toString(byteToSend));
			this.out.write(byteToSend);
			this.out.flush();
			
			while(true){

			
				Thread.sleep(500);
				System.out.println("sending");
				byteToSend=new byte[] { (byte) 255, 0x08, 0x07,0x00,0x00,0x00,0x00 };
				System.out.println(Arrays.toString(byteToSend));
				this.out.write(byteToSend); //0xff 0x08 0x07 0x00 0x00 0x00
				this.out.flush();

			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}