package Onitama.src.Scenes.GameScene.Scripts.AI;

import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece.PieceType;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Scenes.GameScene.Scripts.States.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Engine.Structures.Vector2D;
import Onitama.src.JsonReader;

/**
 * Match
 * simulates game for ai training
 */
public class Match {
    static GameScene scene = new GameScene();
    SmartAI red, blue, winner;
    State game;
    boolean captured, conquered;
    int length;

    Match(SmartAI red, SmartAI blue) {
        this.red = red;
        this.blue = blue;
        this.red.selfID = GameScene.RED_PLAYER;
        this.blue.selfID = GameScene.BLUE_PLAYER;
    }

    List<String> generateCards() {
        JsonReader jReader = new JsonReader();
        List<CardInfo> listOfCards = jReader.readJson("Onitama/res/Cards/cards.json");

        List<String> cards = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        Random random = new Random();

        int i = 0;  
        while (i<5)
        {
            int cardIndex = random.nextInt(16);
            if (!set.contains(cardIndex)) {
                CardInfo card = listOfCards.get(cardIndex);
                cards.add(card.getName());
                set.add(cardIndex);
                ++i;
            }
        }
        return cards;
    }

    ArrayList<Piece> generateRed() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(0, 4)));
        pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(1, 4)));
        pieces.add(new Piece(PieceType.RED_KING, new Vector2D(2, 4)));
        pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(3, 4)));
        pieces.add(new Piece(PieceType.RED_PAWN, new Vector2D(4, 4)));
        return pieces;
    }
    
    ArrayList<Piece> generateBlue() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(0, 0)));
        pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(1, 0)));
        pieces.add(new Piece(PieceType.BLUE_KING, new Vector2D(2, 0)));
        pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(3, 0)));
        pieces.add(new Piece(PieceType.BLUE_PAWN, new Vector2D(4, 0)));
        return pieces;
    }

    void fight() {
        State game = new State(generateRed(), generateBlue(), generateCards(), GameScene.RED_PLAYER);
        Action turn;

        length = 0;
        while (!game.isGameOver() && length < 100) {
            if (game.getCurrentPlayer() == GameScene.RED_PLAYER)
                turn = red.play(game);
            else
                turn = blue.play(game);
            game = game.nextConfig(turn);
            length++;
        }

        if (length > 9000)
            winner = null;
        else if (game.getNextPlayer() == GameScene.RED_PLAYER)
            winner = red;
        else
            winner = blue;

        if (game.isKingCaptured()) {
            captured = true;
            conquered = false;
        } else {
            captured = false;
            conquered = true;
        }
    }
}