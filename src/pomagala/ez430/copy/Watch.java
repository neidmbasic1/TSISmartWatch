package ez430.copy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georg
 */
public class Watch {

    private Port port;

    public Watch(Port port) {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        this.port = port;
    }

    public Watch(String port){
        System.setProperty("gnu.io.rxtx.SerialPorts", port);
        try {
            this.port = new Port(port);
        } catch (Exception ex) {
            Logger.getLogger(Watch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Watch() {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        try {
            this.port = new Port("/dev/ttyACM0");
        } catch (Exception ex) {
            Logger.getLogger(Watch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closePort(){
          port.close();
    }

    public byte[] SendAndReceive(Message msg, int delay) throws Exception {
        port.send(msg);
        Thread.sleep(delay);
        byte[] packet = port.receive();
        //System.out.print(Util.getHexStringFromBytes(packet));

        return packet;
    }

    public void sendReset() throws Exception {
        Message msg = new Message(Message.CMD_RESET, new byte[1]);
        SendAndReceive(msg, 1000);
    }

    public void sendStartSTI() throws Exception {
        Message msg = new Message(Message.CMD_START_SIMPLICITI, null);
        SendAndReceive(msg, 2000);
    }

    public byte sendGetStatus() throws Exception {
        Message msg = new Message(Message.CMD_GET_STATUS, new byte[1]);
        byte[] status = SendAndReceive(msg, 20);
        return status[4];
    }

    public byte[] sendGetAccData() throws Exception {
        Message msg = new Message(Message.CMD_GET_SIMPLICITIDATA, new byte[4]);
        byte[] packet = SendAndReceive(msg, 30);
        if(packet.length == 7){
            byte[] xyz = new byte[3];
            xyz[0] = packet[4];
            xyz[1] = packet[5];
            xyz[2] = packet[6];
            return xyz;
        } else {
            return null;
        }
        
    }

    public void sendSync() throws Exception {
        byte[] data = {07, 00, 00, 00, 00, 00, 00, 00,
            00, 00, 00, 00, 00, 00, 00, 00,
            00, 00, 00};


        Message msg = new Message(Message.CMD_SYNC_SEND_COMMAND, data);
        SendAndReceive(msg, 1000);
    }

    public void sendStopSTI() throws Exception {
        Message msg = new Message(Message.CMD_STOP_SIMPLICITI, null);
        SendAndReceive(msg, 1000);
    }
}