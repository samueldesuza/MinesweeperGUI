package sam.minesweeper_gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;



public class MinesweeperGUI extends Application {
    // Settings for the minefield
    public static int height = 9;
    public static int width = 9;
    public static int mineCount = 10;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Sets the name of the window
        primaryStage.setTitle("Minesweeper");

        ArrayList<ArrayList<ButtonTile>> grid = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                ButtonTile b = new ButtonTile(j, i);
                b.setText("■");
                // Method for when a Tile is Clicked
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        boolean gameOver = clickTile(b.getX(), b.getY(), grid);
                        if (gameOver) {
                            primaryStage.close();
                        }
                    }
                });
                grid.get(i).add(b);
            }
        }


        // Set random amount of mines in the minefield
        int counter = 0;
        int x;
        int y;
        Random rand = new Random();
        while (counter < mineCount) {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
            // Check to see that the grid dosent already have a mine
            if (!grid.get(y).get(x).getMine()) {
                grid.get(y).get(x).setMine();
                counter++;
            }
        }


        // Creates the scene for Minesweeper
        GridPane root = new GridPane();

        root.setAlignment(Pos.CENTER);

        root.setHgap(5);
        root.setVgap(5);

        // Creates the tiles for Minesweeper
        int i = 0;
        int j = 0;
        for (ArrayList<ButtonTile> row : grid) {
            for (Button b : row) {
                root.add(b, j, i);
                j++;
            }
            i++;
            j=0;
        }


        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private boolean clickTile(int j, int i, ArrayList<ArrayList<ButtonTile>> grid) {
        if (i >= 0 && i < width && j >= 0 && j < height) {
            ButtonTile b = grid.get(i).get(j);
            if (b.getMine()) {
                System.out.println("LOSS MESSAGE");
                b.setText("X");
                return true;
            } else {
                // Checks if the mine has been discovered
                if (b.getValue() == -1) {
                    int adjMineCount = getAdjacentMineCount(b.getX(), b.getY(), width, height, grid);
                    if (adjMineCount == 0) {
                        b.setText(" ");
                        b.setDisable(true);
                    } else {
                        b.setText("" + adjMineCount);
                    }
                    b.setValue(adjMineCount);

                    // If there is now surrounding spread the check to adjacent tiles

                    if (adjMineCount == 0) {
                        for (int k = b.getY() - 1; k <= b.getY() + 1; k++) {
                            for (int l = b.getX() - 1; l <= b.getX() + 1; l++) {
                                clickTile(l, k, grid);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private int getAdjacentMineCount(int x, int y, int width, int height, ArrayList<ArrayList<ButtonTile>> grid) {
        int adjMineCount = 0;
        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                if (j >= 0 && j < width && i >= 0 && i < height && grid.get(i).get(j).getMine()) {
                    adjMineCount++;
                }
            }
        }
        return adjMineCount;
    }
}

