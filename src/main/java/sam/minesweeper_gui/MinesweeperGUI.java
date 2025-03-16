package sam.minesweeper_gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;



public class MinesweeperGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Sets the name of the window
        primaryStage.setTitle("Minesweeper");


        // Create the minefield
        MinefieldGrid mineField = new MinefieldGrid(9, 9 ,10);

        // Creates the scene for Minesweeper
        GridPane root = new GridPane();

        root.setAlignment(Pos.CENTER);

        root.setHgap(5);
        root.setVgap(5);

        // Places all the button tiles in the GridPane
        for (ArrayList<ButtonTile> row : mineField.getGrid()) {
            for (ButtonTile b : row) {
                root.add(b, b.getX(), b.getY());
            }
        }


        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}

