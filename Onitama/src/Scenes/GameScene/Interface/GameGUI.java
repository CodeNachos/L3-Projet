package Onitama.src.Scenes.GameScene.Interface;

import java.awt.Dimension;

import Engine.Entities.UI.ColorArea;
import Engine.Entities.UI.MenuFrame;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Board.Board;

public class GameGUI extends MenuFrame {

    ColorArea background;
    Board gameBoard;

    public GameGUI(Dimension area) {
        super(area);

        background = new ColorArea(Main.Palette.background, this.getSize());
        add(background);

        
    }
    
}
