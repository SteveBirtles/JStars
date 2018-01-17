import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main extends Application {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 1024;

    static Set<KeyCode> keysPressed = new HashSet<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        try
        {
            System.out.println("Application Starting...");

            FrameRegulator fr = new FrameRegulator();
            Random rnd = new Random();

            Group root = new Group();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Canvas canvas = new Canvas();

            stage.setTitle("JavaFX Canvas Demo");
            stage.setResizable(false);
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.setOnCloseRequest(we -> {
                System.out.println("Close button was clicked!");
                System.out.println("Terminating Application...");
                System.exit(0);
            });
            stage.show();
            stage.setWidth(WINDOW_WIDTH);
            stage.setHeight(WINDOW_HEIGHT);

            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> keysPressed.add(event.getCode()));
            scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> keysPressed.remove(event.getCode()));

            canvas.setWidth(WINDOW_WIDTH);
            canvas.setHeight(WINDOW_HEIGHT);
            root.getChildren().add(canvas);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.WHITE);
            gc.setFont(new Font("Arial", 14));

            Image[] image = new Image[20];

            image[0] = new Image("stars/01.png");
            image[1] = new Image("stars/02.png");
            image[2] = new Image("stars/03.png");
            image[3] = new Image("stars/04.png");
            image[4] = new Image("stars/05.png");

            image[5] = new Image("stars/06.png");
            image[6] = new Image("stars/07.png");
            image[7] = new Image("stars/08.png");
            image[8] = new Image("stars/09.png");
            image[9] = new Image("stars/10.png");

            image[10] = new Image("stars/11.png");
            image[11] = new Image("stars/12.png");
            image[12] = new Image("stars/13.png");
            image[13] = new Image("stars/14.png");
            image[14] = new Image("stars/15.png");

            image[15] = new Image("stars/16.png");
            image[16] = new Image("stars/17.png");
            image[17] = new Image("stars/18.png");
            image[18] = new Image("stars/19.png");
            image[19] = new Image("stars/20.png");



            Set<Sprite> sprites = new HashSet<>();

            for (int i = 0; i <= 1000; i++)
            {
                Sprite s = new Sprite(rnd.nextInt(WINDOW_WIDTH), rnd.nextInt(WINDOW_HEIGHT), 32, image[i % 20]);
                s.setVelocity(rnd.nextDouble()*100-50, rnd.nextDouble()*100-50);
                sprites.add(s);

            }

            new AnimationTimer() {
                @Override
                public void handle(long now) {

                    /* INPUT */

                    for(KeyCode k : keysPressed)
                    {
                        if (k == KeyCode.ESCAPE) {
                            System.out.println("Terminating Application...");
                            System.exit(0);
                        }
                    }

                    /* PROCESS */

                    for (Sprite s: sprites)
                    {
                        s.update(fr.getFrameLength());
                    }
                    Sprite.clearUpExired(sprites);

                    /* OUTPUT */

                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

                    for (Sprite s : sprites)
                    {
                        gc.drawImage(s.getImage(), s.getX() - s.getImage().getWidth() / 2, s.getY() - s.getImage().getHeight() / 2);
                    }
                    fr.updateFPS(now, gc);

                }
            }.start();

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Terminating Application...");
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}