package ez430.copy;

public class Message {
    /*
     * Commands
     * Source: http://ez430chronosnet.svn.sourceforge.net/viewvc/ez430chronosnet/
     */

    //Generic Commands
    public final static byte CMD_RESET = 0x01;
    public final static byte CMD_GET_STATUS = 0x00;
    public final static byte CMD_GET_PRODUCT_ID = 0x20;

    // BlueRobin - not implemented
    public final static byte CMD_START_BLUEROBIN = 0x02;
    public final static byte CMD_SET_BLUEROBIN_ID = 0x03;
    public final static byte CMD_GET_BLUEROBIN_ID = 0x04;
    public final static byte CMD_SET_HEARTRATE = 0x05;
    public final static byte CMD_SET_SPEED = 0x0A;
    public final static byte CMD_STOP_BLUEROBIN = 0x06;

    // SimpliciTI acc/ppt mode
    public final static byte CMD_START_SIMPLICITI = 0x07;
    public final static byte CMD_GET_SIMPLICITIDATA = 0x08;
    public final static byte CMD_STOP_SIMPLICITI = 0x09;

    // SimpliciTI sync mode - not implemented
    public final static byte CMD_SYNC_START = 0x30;
    public final static byte CMD_SYNC_SEND_COMMAND = 0x31;
    public final static byte CMD_SYNC_GET_BUFFER_STATUS = 0x32;
    public final static byte CMD_SYNC_READ_BUFFER = 0x33;

    //Wireless BSL - not implemented
    public final static byte CMD_START_WBSL = 0x40;
    public final static byte CMD_GET_WBSL_STATUS = 0x41;
    public final static byte CMD_INIT_OK_WBSL = 0x42;
    public final static byte CMD_INIT_INVALID_WBSL = 0x43;
    public final static byte CMD_TRANSFER_OK_WBSL = 0x44;
    public final static byte CMD_TRANSFER_INVALID_WBSL = 0x45;
    public final static byte CMD_STOP_WBSL = 0x46;
    public final static byte CMD_SEND_DATA_WBSL = 0x47;
    public final static byte CMD_GET_PACKET_STATUS_WBSL = 0x48;
    public final static byte CMD_GET_MAX_PAYLOAD_WBSL = 0x49;

    // Production test commands - not implemented
    public final static byte CMD_INIT_TEST = 0x70;
    public final static byte CMD_NEXT_TEST = 0x71;
    public final static byte CMD_WRITE_BYTE = 0x72;
    public final static byte CMD_GET_TEST_RESULT = 0x73;

    //Status Messages
    public final static byte STATUS_IDLE = 0x00;
    public final static byte STATUS_SIMPLICITI_STOPPED = 0x01;
    public final static byte STATUS_SIMPLICITI_TRYING_TO_LINK = 0x02;
    public final static byte STATUS_SIMPLICITI_LINKED = 0x03;
    public final static byte STATUS_BLUEROBIN_STOPPED = 0x04;
    public final static byte STATUS_BLUEROBIN_TRANSMITTING = 0x05;
    public final static byte STATUS_ERROR = 0x05;
    public final static byte STATUS_NO_ERROR = 0x06;
    public final static byte STATUS_NOT_CONNECTED = 0x07;


    private byte[] msg;
    private boolean response; // Is a Response to a packet?

    public Message(byte Type, byte[] data){
        byte length = (data == null) ? 0 : (byte)data.length;
        /* Create new message with length of data + overhead of commands and length */
        msg = new byte[length + 3];

        msg[0] = (byte) 0xFF; // Start of Packet - always the same
        msg[1] = Type; // Command Type
        msg[2] = (byte)(length + 3); //Total length of packet
        for(int i = 3; i < length + 3; i++){
            msg[i] = data[i - 3];
        }
    }

    public byte[] getBytes(){
        return msg;
    }
}