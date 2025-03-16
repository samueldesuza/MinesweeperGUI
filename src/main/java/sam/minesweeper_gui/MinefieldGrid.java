package sam.minesweeper_gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MinefieldGrid {
    private final int width;
    private final int height;
    private final int mineCount;
    private boolean generatedMines = false;

    ArrayList<ArrayList<ButtonTile>> grid = new ArrayList<>();

    public MinefieldGrid(int width, int height, int mineCount) {
        //Constructor for creating a grid of Tiles to click
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;

        // Fills the 2D array with tiles
        for (int i = 0; i < height; i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                ButtonTile b = new ButtonTile(j, i);
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // Calls this function when  the player clicks on a mine
                        handleClick(event, b);
                    }
                });
                grid.get(i).add(b);
            }
        }

    }

    private void handleClick(MouseEvent event, ButtonTile tile) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // The primary click of the mouse reveals a tile if it is not flagged and on the
            if (!generatedMines) {
                generateMines(tile.getX(), tile.getY());
                generatedMines = true;
            }

            if (!tile.getFlag()) {
                clickTile(tile);
            }


        }  if (event.getButton() == MouseButton.SECONDARY) {
            // The secondary click of the mouse sets a flag marker
            if (tile.getFlag()) {
                tile.removeFlag();
            } else {
                tile.addFlag();
            }
        }
    }

    private void generateMines(int x, int y) {
        // This function generates mines in locations that do not already have mines
        HashSet<Point> invalidSpaces = new HashSet<>();
        int mineCounter = 0;
        int mineX;
        int mineY;

        invalidSpaces.add(new Point(x, y));

        Random rand = new Random();
        while (mineCounter < mineCount) {
            mineX = rand.nextInt(width);
            mineY = rand.nextInt(height);
            // Check to see that the possible mine location is not invalid
            if (!invalidSpaces.contains(new Point(mineX, mineY))) {
                invalidSpaces.add(new Point(mineX, mineY));
                grid.get(mineY).get(mineX).setMine();
                mineCounter++;
            }

        }

    }

    private void clickTile(ButtonTile tile) {
        if (tile.getMine()) {
            // When a click is called on a tile with a Mine end the game.
            System.out.println("LOSS MESSAGE");
        } else {
            // Checks if the mine has been discovered
            if (tile.getValue() == -1) {
                int adjMineCount = getAdjacentMineCount(tile.getX(), tile.getY());

                if (adjMineCount == 0) {
                    // If there are no adjacent mines set the mine to be blanked out
                    tile.setText(" ");
                    tile.setDisable(true);
                } else {
                    // if there is an adjacent mine set the mine value to the number of adjacent mines
                    tile.setText("" + adjMineCount);
                }
                tile.setValue(adjMineCount);

                // Spread function to adjacent tiles when there is no mines in the adjacent tiles
                if (adjMineCount == 0) {
                    for (int y = tile.getY() - 1; y <= tile.getY() + 1; y++) {
                        for (int x = tile.getX() - 1; x <= tile.getX() + 1; x++) {
                            if (x >= 0 && x < width && y >= 0 && y < height) {
                                // Check to make sure that the value is not out of Bounds
                                clickTile(grid.get(y).get(x));
                            }
                        }
                    }
                }
            }
        }
    }

    private int getAdjacentMineCount(int x, int y) {
        int adjMineCount = 0;
        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                if (j >= 0 && j < width && i >= 0 && i < height && grid.get(i).get(j).getMine()) {
                    // this is a check to see if the value is within the grid of the minefield
                    adjMineCount++;
                }
            }
        }
        return adjMineCount;
    }

    public ArrayList<ArrayList<ButtonTile>> getGrid() {
        return grid;
    }
}
