package sam.terminal_based_logic;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int width = 9;
        int height = 9;
        int mineCount = 10;

        MineField mineField = new MineField(width, height, mineCount);

        boolean gameOver = false;
        while (!gameOver) {
            mineField.printMineField();
            System.out.println("------------------------------------");
            mineField.printMineFieldCheat();


            Scanner keyboardInput = new Scanner(System.in);
            System.out.println("Enter X Coordinate (0 start): ");
            int x = keyboardInput.nextInt();
            System.out.println("Enter Y Coordinate (0 start): ");
            int y = keyboardInput.nextInt();

            gameOver = mineField.clickTile(x, y);
        }
    }
}