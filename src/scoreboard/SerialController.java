package scoreboard;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Scanner;

/**
 * Created by bjohn on 5/3/2016.
 */
public class SerialController extends Thread {
    private ScoreBoardController model;
    private MatchTimer timer;
    private int largeInc;
    private int smallInc;

    public SerialController(ScoreBoardController model){
        this.model = model;
    }
    public void run(){
        Scanner scanner = new Scanner(System.in);
        Integer portNum = 1;
        for (SerialPort p:SerialPort.getCommPorts()){
            System.out.println(portNum.toString()+". "+p.getDescriptivePortName());
            portNum++;
        }
        int portChoice = scanner.nextInt();
        SerialPort port = SerialPort.getCommPorts()[portChoice-1];
        System.out.println("Using: "+port.getDescriptivePortName());
        System.out.println("Enter small point value: ");
        smallInc = scanner.nextInt();
        System.out.println("Enter large point value: ");
        largeInc = scanner.nextInt();
        port.openPort();
        byte[] writeData = new byte[1];
        writeData[0]='a';

        port.writeBytes(writeData, 1);
        port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[port.bytesAvailable()];
                int numRead = port.readBytes(newData, newData.length);
                //System.out.println("Read " + numRead + " bytes." + (char)newData[0]);
                try {
                    switch (newData[0]) {
                        case 'S':
                            model.matchSetUp();
//                            if(timer.isAlive()){
//                                timer.stop();
//                            }
                            timer = new MatchTimer(model.getTIME(), model);
                            timer.start();
                            break;
                        case '1':
                            model.scoreB += smallInc;
                            break;
                        case '0':
                            model.scoreB += largeInc;
                            break;
                        case '3':
                            model.scoreA += smallInc;
                            break;
                        case '2':
                            model.scoreA += largeInc;
                            break;
                        case 'M':
                            timer.interrupt();
                            break;

                    }
                } catch(ArrayIndexOutOfBoundsException ae){
                    System.out.println("###  HANDLED  ###");
                }
            }
        });
    }
}
