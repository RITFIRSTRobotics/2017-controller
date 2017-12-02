package FMS;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by bjohn on 4/3/2016.
 */
public class StatusGUI extends Application {

    private long lastUpdate;
    private long minUpdateInterval = 50000;

    @Override
    public void start(Stage primaryStage) throws Exception {


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate > minUpdateInterval){
                    primaryStage.setScene(makeScene());
                    //System.out.println(model.matchTime/model.getTIME());
                    //System.out.println("Tick "+now);

                }
                lastUpdate = now;
            }
        };


        primaryStage.show();
        
    }

    private Scene makeScene(){
        VBox enableBtns = new VBox();
        for (int i = 0; i < 4; i++) {
            enableBtns.getChildren().add(i, new Button("Robot "+i));
        }
        return new Scene(new BorderPane());
    }
}
