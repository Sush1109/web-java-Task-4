package p1;

import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GraphicsDemo extends Application {

    @Override
    public void start(Stack primaryStage) {
        primaryStage.setTitle("JavaFX Graphics Demo");

        // Create a canvas
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw on the canvas
        gc.setFill(Color.BLUE);
        gc.fillRect(100, 100, 200, 200);

        gc.setStroke(Color.RED);
        gc.strokeLine(50, 50, 350, 350);

        // Create a layout and add the canvas to it
        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        // Create a scene with the layout
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
