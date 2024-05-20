package Onitama.src.Scenes.GameScene.Entities.Board;

import java.awt.*;

import Engine.Entities.TileMap.TileMap;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Entities.Player.Player;

public class PieceMap extends TileMap {
    Player player;

    public PieceMap(Dimension area, Vector2D offset, Player player) {
        super(5, 5, area, offset);

        this.player = player;
    }

    public void updatePieces() {
        clearMap();

        for (Piece p : player.getPieces()) {
            addTile(p.getLine(), p.getColumn(), p);
        }

    }

    public Piece getPiece(int l, int c) {
        return (Piece)getTile(l, c);
    }
}
