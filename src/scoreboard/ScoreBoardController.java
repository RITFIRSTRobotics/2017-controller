package scoreboard;

import FMS.Robot;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.ArrayList;

/**
 * Created by Brett on 5/2/2016.
 */
public class ScoreBoardController {
    protected int scoreA;
    protected int scoreB;
    private MatchTimer timer;
    private static double TIME = 121; //seconds for match
    public double matchTime;
    public int matchNum = -1;
    public ArrayList<Robot> robots = new ArrayList<Robot>();

    public ScoreBoardController(){
        scoreA = 0;
        scoreB = 0;
        matchTime =  TIME;

    }

    public double getTIME() {
        return TIME;
    }
    public int getScoreB() {
        return scoreB;
    }
    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }
    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }


    public void enableBot(int id){
        robots.get(id).enable();
    }
    public void disableBot(int id){
        robots.get(id).disable();
    }
    public void enableAll(){
        for(Robot r:robots){
            r.enable();
        }
    }
    public void disableAll(){
        for(Robot r:robots){
            r.disable();
        }
    }

    public void connect(){
        /**
         * Connects to robots, adds them to ArrayList robots
         */
    }
    public void matchSetUp(){
        matchTime = TIME;
        scoreA = 0;
        scoreB = 0;
        matchNum++;

    }

    public static void main(String[] args) {
        //run();
    }
}
