package sam.terminal_based_logic;

import java.util.Random;

public class MineField {
    // You're an idiot and going to forget this so grid is grid[y][x] cause of 2D array format

    private final int width;
    private final int height;
    private int mineCount;
    private Tile[][] grid;

    public MineField(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;

        grid = new Tile[height][width];
        // Fill out the grid with Tiles
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Tile();
            }
        }
        // Fill the grid with mines
        Random rand = new Random();
        int x;
        int y;
        boolean emptyTile;

        // Fills the minefield with mines
        for (int i=0; i < mineCount; i++) {
            emptyTile = false;

            // Makes sure the tile doesn't already have a mine
            while (!emptyTile) {
                x = rand.nextInt(width);
                y = rand.nextInt(height);
                if (!grid[y][x].getMine()) {
                    grid[y][x].setMine();
                    emptyTile = true;
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        return grid[y][x];
    }

    public boolean clickTile(int x, int y) {
        // Checks if the location clicked is valid
        if (x >= 0 && x < width && y >= 0 && y < height) {
            if (grid[y][x].getMine()) {
                System.out.println(" YOU GET NOTHING, YOU LOSE, GOOD DAY, SIR");
                return true;
            } else {
                if (getTile(x, y).getValue() == -1) {
                    // if the mine is not found check if it has any adj mines and set it to revealed
                    int adjMineCount = getAdjacentMineCount(x, y);
                    grid[y][x].setValue(adjMineCount);

                    // if there are no mines around the tile reveal the surrounding mines
                    if (adjMineCount == 0) {
                        for (int i = y - 1; i <= y + 1; i++) {
                            for (int j = x - 1; j <= x + 1; j++) {
                                clickTile(j, i);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private int getAdjacentMineCount(int x, int y) {
        int adjMineCount = 0;
        // i is height and j is width
        for (int i = y-1; i <= y+1; i++) {
            for (int j = x-1; j <= x+1; j++) {
                if (j >= 0 && j < width && i >= 0 && i < height && grid[i][j].getMine()) {
                    adjMineCount++;
                }
            }
        }
        return adjMineCount;
    }

    public void printMineField() {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                System.out.print(tile);
            }
            System.out.print("\n");
        }
    }

    public void printMineFieldCheat() {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.getMine()) {
                    System.out.print(" ▲ ");
                } else {
                    System.out.print(tile);
                }
            }
            System.out.print("\n");
        }
    }
}
