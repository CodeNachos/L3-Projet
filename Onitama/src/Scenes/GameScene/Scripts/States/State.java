package Onitama.src.Scenes.GameScene.Scripts.States;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Engine.Global.Util;
import Engine.Structures.Vector2D;
import Old.src.Scenes.GameScene.Scripts.GameConfiguration;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece.PieceType;

public class State implements Serializable {
    public PieceType[][] board;
    public List<String> gameCards;
    public int currentPlayer;


    public State(ArrayList<Piece> p1p, ArrayList<Piece> p2p, List<String> cards, int currentPlayer) {
        if (Main.gameScene == null) {
            Util.printWarning("Game Scene was not instantiated");
        }

        this.currentPlayer = currentPlayer;
        this.gameCards = cards;
        createBoard(p1p, p2p);
    }

    public State(PieceType[][] board, List<String> cards, int currentPlayer) {
        if (Main.gameScene == null) {
            Util.printWarning("Game Scene was not instantiated");
        }

        this.currentPlayer = currentPlayer;
        this.gameCards = cards;
        this.board = copyBoard(board);

    }

    public void createBoard(ArrayList<Piece> p1p, ArrayList<Piece> p2p) {
        board = new PieceType[5][5];

        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                board[l][c] = PieceType.EMPTY;
            }
        }

        for (Piece p : p1p) {
            board[p.getLine()][p.getColumn()] = p.getType();
        }
        
        for (Piece p : p2p) {
            board[p.getLine()][p.getColumn()] = p.getType();
        }
    }

    public boolean isPlayerPiece(PieceType piece, int player) {
        return (
            (player == GameScene.PLAYER1 && (piece == PieceType.RED_KING || piece == PieceType.RED_PAWN)) ||
            (player == GameScene.PLAYER2 && (piece == PieceType.BLUE_KING || piece == PieceType.BLUE_PAWN))
        );
    }

    public boolean isPlayerKing(PieceType piece, int player) {
        return (
            (player == GameScene.PLAYER1 && (piece == PieceType.RED_KING)) ||
            (player == GameScene.PLAYER2 && (piece == PieceType.BLUE_KING))
        );
    }

    
    /* Start of AI's interface */
    /* Please discuss with Duc if you want to change something */

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        Vector2D ally = allyKing();
        Vector2D enemy = enemyKing();

        if (ally == null || enemy == null) {
            return true;
        }
        
        if (ally != null) {
            if (ally.equals(currentPlayer == GameScene.PLAYER1 ? GameScene.BLUE_THRONE : GameScene.RED_THRONE)) {
                return true;
            }
        }
        
        if (enemy != null) {
            if (enemy.equals(currentPlayer == GameScene.PLAYER1 ? GameScene.BLUE_THRONE : GameScene.RED_THRONE)) {
                return true;
            }
        }

        return false;
    } 

    public List<Vector2D> allyPositions() {
        List<Vector2D> positions = new ArrayList<>();
        
        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                if (isPlayerPiece(board[l][c], currentPlayer)) {
                    positions.add(new Vector2D(c, l));
                }
            }
        }

        return positions;
    }

    public List<Vector2D> enemyPositions() {
        List<Vector2D> positions = new ArrayList<>();
        
        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                if (!isPlayerPiece(board[l][c], currentPlayer)) {
                    positions.add(new Vector2D(c, l));
                }
            }
        }

        return positions;
    }

    public Vector2D allyKing() {
        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                if (isPlayerKing(board[l][c], currentPlayer)) {
                    return new Vector2D(c,l);
                }
            }
        }
        return null;
    }

    public Vector2D enemyKing() {
        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                if (!isPlayerKing(board[l][c], currentPlayer)) {
                    return new Vector2D(c,l);
                }
            }
        }
        return null;
    }

    public Vector2D allyGoal() {
        if (currentPlayer == GameScene.PLAYER1)
            return GameScene.RED_THRONE;
        else
            return GameScene.BLUE_THRONE;
    }

    public Vector2D enemyGoal() {
        if (currentPlayer == GameScene.PLAYER2)
            return GameScene.RED_THRONE;
        else
            return GameScene.BLUE_THRONE;
    }

    public List<String> availableCards() {
        List<String> cards = new ArrayList<>();
        
        if (currentPlayer == GameScene.PLAYER1) {
            cards.add(gameCards.get(0));
            cards.add(gameCards.get(1));
        } else {
            cards.add(gameCards.get(2));
            cards.add(gameCards.get(3));
        }

        return cards;
    }

    public List<Vector2D> possiblePositions(Vector2D piece, String card) {
        List<Vector2D> allies = allyPositions();
        List<Vector2D> curMovement;
        if (currentPlayer == GameConfiguration.PLAYER1)
            curMovement = GameScene.getGameCards().get(card).getRedMovement();
        else
            curMovement = GameScene.getGameCards().get(card).getBlueMovement();

        List<Vector2D> newPossiblePos = new ArrayList<>();
        
        for (Vector2D movement : curMovement) {
            Vector2D possPosition = new Vector2D(
                piece.getIntX() + movement.getIntY(),
                piece.getIntY() + movement.getIntX()
            );
            if (possPosition.getIntX() < 5 && possPosition.getIntY() < 5 &&
                possPosition.getIntX() >= 0 && possPosition.getIntY() >= 0  &&
                !allies.contains(possPosition)   
            ) {
                newPossiblePos.add(possPosition);
            }
        }

        return newPossiblePos;
    }

    public State nextConfig(Action turn) {
        State next = new State(board, gameCards, currentPlayer);
        
        if (enemyPositions().contains(turn.getMove())) {
            next.board[turn.getMove().getIntY()][turn.getMove().getIntX()] = PieceType.EMPTY;
        }

        next.board[turn.getMove().getIntY()][turn.getMove().getIntX()] = board[turn.getPiece().getIntY()][turn.getPiece().getIntX()];
        next.board[turn.getPiece().getIntY()][turn.getPiece().getIntX()] = PieceType.EMPTY;

        String tmp;
        if (next.currentPlayer == GameScene.PLAYER1) {
            if (turn.getCard().equals(next.gameCards.get(0))) {
                tmp = next.gameCards.get(0);
                next.gameCards.set(0, next.gameCards.get(4));
                next.gameCards.set(4, tmp);
            } else {    
                tmp = next.gameCards.get(1);
                next.gameCards.set(1, next.gameCards.get(4));
                next.gameCards.set(4, tmp);
            }
        } else {
            if (turn.getCard().equals(next.gameCards.get(2))) {
                tmp = next.gameCards.get(2);
                next.gameCards.set(2, next.gameCards.get(4));
                next.gameCards.set(4, tmp);
            } else {    
                tmp = next.gameCards.get(3);
                next.gameCards.set(3, next.gameCards.get(4));
                next.gameCards.set(4, tmp);
            }
        }

        next.currentPlayer = (currentPlayer + 1) % 2;

        return next;
    }

    private PieceType[][] copyBoard(PieceType[][] board) {
        PieceType[][] copy = new PieceType[5][5];

        for (int l = 0; l < 5; l++) {
            for (int c = 0; c < 5; c++) {
                copy[l][c] = board[l][c];
            }
        }

        return copy;
    }
}
