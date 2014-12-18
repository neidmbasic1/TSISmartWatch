package ez430.copy;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georg
 */
public class Port {
    SerialPort port;
    private SerialReader reader;
    private SerialWriter writer;

    public Port(String portName) throws Exception{
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                this.port = serialPort;

                serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                reader = new SerialReader(in);
                writer = new SerialWriter(out);

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public void send(Message msg){
        writer.writePacket(msg.getBytes());
    }

    public byte[] receive(){
        return reader.readPacket();
    }

    public void close(){
        port.close();
    }
    private class SerialReader
    {
        InputStream in;

        public SerialReader ( InputStream in )
        {
            this.in = in;
        }

        private byte[] read(int numBytes)
        {
            byte[] buffer = new byte[numBytes];
            for (int i=0; i < numBytes; i++){
                buffer[i] = readByte();
            }

            return buffer;
        }

        private byte readByte(){
            byte[] packet = new byte[1];
            try {
                this.in.read(packet, 0, 1);
            } catch (IOException ex) {
                Logger.getLogger(Port.class.getName()).log(Level.SEVERE, null, ex);
            }
            return packet[0];
        }
        public byte[] readPacket(){
            byte[] header = read(3);

            int data_length = header[2] - 3;
            int packet_length = header[2];

            byte[] data = read(data_length);

            byte[] packet = new byte[packet_length];


            packet[0] = header[0]; packet[1] = header[1]; packet[2] = header[2];
            for(int i = 3; i < packet_length; i++)
                packet[i] = data[i-3];

            return packet;
        }
    }

    /** */
    private class SerialWriter
    {
        OutputStream out;

        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }

        public void writePacket(byte[] packet)
        {
            try
            {
                this.out.write(packet);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
}