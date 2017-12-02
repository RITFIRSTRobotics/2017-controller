package scoreboard;

import static java.lang.System.currentTimeMillis;

/**
 * Created by bjohn on 5/3/2016.
 */
public class MatchTimer extends Thread {

    public ScoreBoardController model;

    private double MATCH_LEN;
    private double timeRemaining;
    public double PROGRESS;
    private double timeStep = .01; //seconds


    public MatchTimer(double mLen, ScoreBoardController model){
        super();
        MATCH_LEN = mLen;
        this.model = model;
    }
    public MatchTimer(){
        super();
        MATCH_LEN = 12;
    }
    public void run(){
        timeRemaining = MATCH_LEN;
        System.out.println("Run Called, Time: " + timeRemaining);
        long startTime = currentTimeMillis();
        while(model.matchTime>0.01){
            model.matchTime = MATCH_LEN - (currentTimeMillis()-startTime)/1000;
            //model.matchTime = timeRemaining;
            //System.out.println(timeRemaining);

        }
        try {
            this.interrupt();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
