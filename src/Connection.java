import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SerializablePermission;

public class Connection {
	public Connection() {
		super();
	}

	/**
	 * Funkcija sa kojom se postavljaju parametri porta. Za default vrijednosti
	 * su uzete DATABITS_8, STOPBITS_1, PARITY_NONE, dok se mijenja samo
	 * BaudRate. (Prilagodjeno je za Smart Watch i BaudRate za njega je 115200)
	 * 
	 * @param commPort
	 *            port koji se tretira
	 * @param baudRate
	 *            vrijednost baud rate za ulazni port
	 */

	@SuppressWarnings("restriction")
	public void setParamsOfPort(CommPort commPort, int baudRate)
			throws Exception {

		if (commPort instanceof SerialPort) {
			System.out.println("Connect 2/2");
			SerialPort serialPort = (SerialPort) commPort;

			serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			System.out.println("BaudRate: " + serialPort.getBaudRate());
			System.out.println("DataBIts: " + serialPort.getDataBits());
			System.out.println("StopBits: " + serialPort.getStopBits());
			System.out.println("Parity: " + serialPort.getParity());
			System.out.println("FlowControl: "
					+ serialPort.getFlowControlMode());
			

		} else {
			System.out
					.println("Error: Only serial ports are handled.");
			
		}
	}

	/**
	 * Funkcija za ispisivanje parametara porta.
	 * 
	 * @param commPort
	 *            port za koji se ispisuje informacija
	 * 
	 * 
	 */
	@SuppressWarnings("restriction")
	public void printParamsOfPort(CommPort commPort) {

		SerialPort serialPort = (SerialPort) commPort;
		System.out.println("BaudRate: " + serialPort.getBaudRate());
		System.out.println("DataBIts: " + serialPort.getDataBits());
		System.out.println("StopBits: " + serialPort.getStopBits());
		System.out.println("Parity: " + serialPort.getParity());
		System.out.println("FlowControl: " + serialPort.getFlowControlMode());
	}

	/**
	 * Funkcija koja prima ime porta i u zavisnosti da li postoji taj port baca
	 * izuzetak ili zauzima taj port.
	 * 
	 * @param portName
	 *            Ime porta koji se koristi, npr. COM6
	 * 
	 * */
	@SuppressWarnings("restriction")
	void connect(String portName) throws Exception {
		
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName); // otvaranje trazenog porta

		if (portIdentifier.isCurrentlyOwned()) {

			System.out.println("Error: Port is currently in use");

		} else {
			System.out.println("Connect 1/2");
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					6000);
			int baudRate = 115200; // Prilagodjeno za Smart Watch
			setParamsOfPort(commPort, baudRate);
			
			SerialPort serialPort = (SerialPort) commPort;
			
			InputStream in = serialPort.getInputStream();
			OutputStream out = serialPort.getOutputStream();

			(new Thread(new SerialReader(in))).start();
			(new Thread(new SerialWriter(out))).start();
			 
			
		}
	}
	}
