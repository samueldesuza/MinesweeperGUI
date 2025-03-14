package sam.terminal_based_logic;

public class Tile {
    boolean _hasMine = false;
    int _minesAdjacent = -1;

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

    public String toString() {
        if (_minesAdjacent == -1) {
            return " ■ ";
        } if (_minesAdjacent == 0) {
            return " □ ";
        } else {
            return " " + _minesAdjacent + " ";
        }
    }

}
