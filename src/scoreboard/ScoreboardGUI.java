package scoreboard;

import FMS.StatusGUI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScoreboardGUI extends Application {

    private ScoreBoardController model = new ScoreBoardController();
    private SerialController reader = new SerialController(model);
    private Stage stage;
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    private Font scoreFont = Font.font(.3*primaryScreenBounds.getHeight());
    private Font infoFont = Font.font(primaryScreenBounds.getHeight()/20);
    private long lastUpdate;
    private long minUpdateInterval = 50000;
    private ProgressBar countDown = new ProgressBar();
    private double progress = model.matchTime/model.getTIME();
    private double lastProg = 1.0;
    public ScoreboardGUI() {
        super();
    }

    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        primaryStage.setTitle("Score Board v.1");
        primaryStage.setMaximized(true);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate > minUpdateInterval){
                    stage.setScene(makeScene());
                    //System.out.println(model.matchTime/model.getTIME());
                    //System.out.println("Tick "+now);

                }
                lastUpdate = now;
            }
        };

        reader.start();
        timer.start();
        Scene scene = makeScene();
        primaryStage.setScene(scene);
        primaryStage.show();
        //play();

    }

    private Scene makeScene(){
        //Background ImageView
        ImageView back  = new ImageView("logo-back.png");       //back.scaleXProperty().set(primaryScreenBounds.getMaxY()/1080);
                                                                //back.scaleYProperty().set(primaryScreenBounds.getMaxY()/1080);
        ImageView left  = new ImageView("bricks.jpg");          //left.scaleXProperty().set(primaryScreenBounds.getMaxY()/1080);
                                                                //left.scaleYProperty().set(primaryScreenBounds.getMaxY()/1080);
        ImageView right = new ImageView("bricksFlipped.jpg");   //right.scaleXProperty().set(primaryScreenBounds.getMaxY()/1080);
                                                                //right.scaleYProperty().set(primaryScreenBounds.getMaxY()/1080);

        //Background HBox
        HBox background = new HBox(
                left,
                back,
                right
        );
        background.setAlignment(Pos.CENTER);

        //Full Board to be passed in
        VBox main = new VBox(
                background,
                makeScoresInfo()
        );
        updateBar();
        return new Scene(main);
    }

    private Label makeScore(Font font, int score){
        Label a = new Label(((Integer) score).toString());
        a.setFont(font);
        a.setAlignment(Pos.CENTER);
        Insets inset = new Insets(40.0);
        a.setPadding(inset);
        return a;
    }


    private BorderPane makeScoresInfo(){
        //Score A display
        Label scoreA = makeScore(scoreFont, model.getScoreA());
        scoreA.alignmentProperty().setValue(Pos.CENTER_LEFT);
        scoreA.setTextFill(Color.BLUE);
        //Score B display
        Label scoreB = makeScore(scoreFont, model.getScoreB());
        scoreB.alignmentProperty().setValue(Pos.CENTER_RIGHT);
        scoreB.setTextFill(Color.RED);
        //Border pane holder
        BorderPane borderPane = new BorderPane();
        VBox matchinfo = makeMatchInfo();
        matchinfo.alignmentProperty().setValue(Pos.CENTER);
        borderPane.setCenter(matchinfo);
        borderPane.setLeft(scoreA);
        borderPane.setRight(scoreB);
        formatBar();
        //timeBar.setAlignment(Pos.CENTER);
        borderPane.setTop(countDown);
        BorderPane.setAlignment(borderPane.getTop(), Pos.CENTER);
        return borderPane;

    }

    private VBox makeMatchInfo(){
        Label matchNum = new Label("Match Number: "+model.matchNum);
        matchNum.setFont(infoFont);
        Label timeRemain = new Label("Time Remaining: "+((Double)model.matchTime).intValue());
        timeRemain.setFont(infoFont);
        GridPane bots = new GridPane();
        return new VBox(
                //update,
                matchNum,
                timeRemain);
    }

    private void formatBar(){
        countDown.rotateProperty().set(180.0);
        countDown.setProgress(1.0);
        countDown.setMinSize(.99*primaryScreenBounds.getWidth(), 40.0);
        countDown.autosize();


    }

    public void updateBar(){
        countDown.setProgress(model.matchTime/model.getTIME());
        //System.out.println(model.matchTime);
    }




    public static void main(String[] args) {
        launch(args);

    }
}
