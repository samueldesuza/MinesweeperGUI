module sam.minesweeper_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sam.minesweeper_gui to javafx.fxml;
    exports sam.minesweeper_gui;
}