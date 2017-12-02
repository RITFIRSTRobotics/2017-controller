package scoreboard;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.InputStream;

/**
 * Created by Brett on 4/17/2016.
 */
public class CerealRead extends Thread {
    @Override
    public void run(){
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        byte[] writeData = new byte[1];
        writeData[0]='a';
        comPort.writeBytes(writeData, 1);
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                for(byte b : newData){
                    System.out.print((char)b);
                }
                System.out.println("");
            }
        });
        //comPort.closePort();
    }

    public static void main(String[] args) {
    }


}
