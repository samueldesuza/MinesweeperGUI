package sam.minesweeper_gui;

import javafx.scene.control.Button;

public class ButtonTile extends Button {
    int x;
    int y;
    boolean _hasMine = false;
    int _minesAdjacent = -1;
    boolean _hasFlag = false;

    public ButtonTile(int x, int y) {
        this.x = x;
        this.y = y;
        this.setText("■");
    }

    public void setMine() {
        _hasMine = true;
    }

    public void setValue(int value) {
        _minesAdjacent = value;
    }

    public int getValue() {
        return _minesAdjacent;
    }

    public boolean getMine() {
        return _hasMine;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getFlag() {
        return _hasFlag;
    }
    public void addFlag() {
        _hasFlag = true;
        this.setText("⚑");

    }
    public void removeFlag() {
        _hasFlag = false;
        this.setText("■");
    }
}
