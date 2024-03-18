package p1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BrickBreakerGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_RADIUS = 10;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 20;

    private int score = 0;
    private int lives = 3;
    private Paddle paddle;
    private Ball ball;
    private Brick[][] bricks;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        paddle = new Paddle(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 10, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(WIDTH / 2, HEIGHT / 2, BALL_RADIUS);

        // Initialize bricks
        bricks = new Brick[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks[i][j] = new Brick(j * BRICK_WIDTH, i * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
            }
        }

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    paddle.moveLeft();
                    break;
                case RIGHT:
                    paddle.moveRight(WIDTH);
                    break;
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Clear canvas
                gc.clearRect(0, 0, WIDTH, HEIGHT);

                // Draw paddle
                gc.setFill(Color.BLUE);
                gc.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

                // Draw ball
                gc.setFill(Color.RED);
                gc.fillOval(ball.getX() - BALL_RADIUS, ball.getY() - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);

                // Draw bricks
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (!bricks[i][j].isDestroyed()) {
                            gc.setFill(Color.GREEN);
                            gc.fillRect(bricks[i][j].getX(), bricks[i][j].getY(), bricks[i][j].getWidth(), bricks[i][j].getHeight());
                        }
                    }
                }

                // Handle collisions
                handleCollisions();

                // Update ball position
                ball.move();

                // Check game over conditions
                if (lives == 0) {
                    stop();
                    // Game over logic
                }
            }
        }.start();

        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleCollisions() {
        // Ball with paddle
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseY();
        }

        // Ball with bricks
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (!bricks[i][j].isDestroyed() && ball.getBounds().intersects(bricks[i][j].getBounds())) {
                    bricks[i][j].setDestroyed(true);
                    ball.reverseY();
                    score += 10; // Increment score
                    // Check for level completion
                    if (checkLevelCompletion()) {
                        // Proceed to next level
                    }
                }
            }
        }
    }

    private boolean checkLevelCompletion() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (!bricks[i][j].isDestroyed()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Define Paddle, Ball, Brick classes here
}

class Paddle {
    private double x;
    private double y;
    private double width;
    private double height;

    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        x -= 10; // Adjust speed as needed
    }

    public void moveRight(double screenWidth) {
        x += 10; // Adjust speed as needed
        if (x + width > screenWidth) {
            x = screenWidth - width;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }
}

class Ball {
    private double x;
    private double y;
    private double radius;
    private double dx = 5; // Adjust speed as needed
    private double dy = 5;

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void move() {
        x += dx;
        y += dy;
        // Handle collisions with walls
        if (x <= radius || x >= BrickBreakerGame.WIDTH - radius) {
            dx = -dx;
        }
        if (y <= radius) {
            dy = -dy;
        }
    }

    public void reverseY() {
        dy = -dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x - radius, y - radius, radius * 2, radius * 2);
    }
}

class Brick {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean destroyed = false;

    public Brick(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

   
