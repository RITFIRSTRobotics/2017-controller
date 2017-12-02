package FMS;

/**
 * Created by Brett on 4/2/2016.
 */
public class Robot {
    int id;
    boolean connected;
    boolean enabled;

    public Robot(int id){
        this.id = id;
    }
    public void enable(){
        enabled = true;
    }
    public void disable(){
        enabled = false;
    }
}
