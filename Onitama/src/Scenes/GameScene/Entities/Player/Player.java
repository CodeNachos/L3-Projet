package Onitama.src.Scenes.GameScene.Entities.Player;

import java.util.ArrayList;

import Engine.Core.Renderer.Scene;
import Engine.Entities.GameObject;
import Engine.Global.Util;
import Engine.Structures.Sprite;
import Engine.Structures.Texture;
import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.GameScene;
import Onitama.src.Scenes.GameScene.Entities.Card.Card;
import Onitama.src.Scenes.GameScene.Scripts.AI.AI;
import Onitama.src.Scenes.GameScene.Scripts.AI.RandomAI;
import Onitama.src.Scenes.GameScene.Scripts.AI.SmartAI;
import Onitama.src.Scenes.GameScene.Scripts.States.Action;
import Onitama.src.Main;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece;
import Onitama.src.Scenes.GameScene.Entities.Board.Piece.PieceType;
import Onitama.src.Scenes.GameScene.Entities.Board.PieceMap;

public class Player extends GameObject {
    int playerId;

    Card card1, card2, standBy;
    ArrayList<Piece> pieces;
    PieceMap pieceMap;
    AI ai = null;

    private Action aiAction = null;
    private int counter;

    public Sprite idleCardSprite;
    public Sprite selectedCardSprite;

    // Turn info
    Card selectedCard = null;

    public Player(int playerId) {
        this.playerId = playerId;
    }


    public Player(int playerId, String card1, String card2) {
        this.playerId = playerId;
        createCards(card1, card2);
        createPieces();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void enableAI(int difficulty) {
        if (standBy != null) {
            standBy.setinteractable(false);
        }

        if (difficulty < 1) {
            ai = new RandomAI();
        } else {
            ai = new SmartAI(difficulty, playerId);
        }
    }

    public void disableAI() {
        ai = null;
    }

    public boolean isAiEnabled() {
        return ai != null;
    }

    @Override
    public void process(double delta) {
        if (GameScene.gameOver() || ai == null)
            return;
        
        if (aiAction != null) {
            if (counter > 0) {
                counter--;
                return;
            }
            GameScene.updateMatch();
            aiAction = null;
        }

        else if (GameScene.getCurrentPlayer() == this.playerId) {
            aiAction = ai.play();
            GameScene.setAction(aiAction);
            counter = 60; // 1 second delay
            return;
            
        } else {
            aiAction = null;
        }
        
    }

    public void setCardsInteractable(boolean state) {
        card1.setinteractable(state);
        card2.setinteractable(state);
        if (standBy != null) {
            standBy.setinteractable(state);
        }
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    

    public Piece checkPiecePresence(int l, int c) {
        return (Piece)(pieceMap.getPiece(l, c));
    }

    public String getFirstCard() {
        if (card1 == null) {
            return null;
        }
        return card1.getName();
    }

    public String getSecondCard() {
        if (card2 == null) {
            return null;
        }
        return card2.getName();
    }


    public boolean isCardSelected() {
        return selectedCard != null;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card c) {
        selectedCard = c;
    }

    public void setSelectedCardByName(String name) {
        if (card1.getName().equals(name)) {
            setSelectedCard(card1);
        } else if (card2.getName().equals(name)) {
            setSelectedCard(card2);
        } else {
            Util.printError("Card does not belong to player");
        }
    }

    public void movePiece(Piece p, Vector2D dest) {
        if (!pieces.contains(p)) {
            Util.printError("Piece is not owned by player");
            return;
        }
        
        p.setMapPosition(dest);

        update();
    }

    public void update() {
        pieceMap.updatePieces();
    }

    public Card getStandByCard() {
        if (standBy == null)
            return null;
        
        return standBy;
    }

    public void setStandBy(Card stb) {
        standBy = stb;
        if (standBy != null) {
            standBy.setPlayer(this);
        }
    }

    public void createStandBy(Scene scene, String name) {
        Vector2D cardPos = new Vector2D(
            (Main.engine.getResolution().width/2) - (int)(idleCardSprite.getWidth()/2),
            (int)(Main.engine.getResolution().height) -(int)(1.2*idleCardSprite.getHeight())
        );

        standBy = new Card(name, cardPos, idleCardSprite, this);
        standBy.setStandBy(true);
        standBy.addCardToScene(scene);

    }

    public void removeStandBy() {
        standBy = null;
    }

    private void createCards(String card1Name, String card2Name) {
        idleCardSprite = new Sprite(
            new Texture(
                Main.Palette.background, 
                (int)(Main.engine.getResolution().getHeight()/5),
                (int)(Main.engine.getResolution().getHeight()/5))
        );
        idleCardSprite.setBorder(5, playerId == GameScene.PLAYER1 ? Main.Palette.red : Main.Palette.highlight, 10);

        selectedCardSprite = new Sprite(
            new Texture(
                Main.Palette.selection,
                (int)(Main.engine.getResolution().getHeight()/5),
                (int)(Main.engine.getResolution().getHeight()/5))
        );
        selectedCardSprite.setBorder(5, playerId == GameScene.PLAYER1 ? Main.Palette.red.brighter() : Main.Palette.cyan.brighter(), 10);

        Vector2D cardPos;

        if (playerId == GameScene.PLAYER1) {
            cardPos = new Vector2D(
                
                (int)(GameScene.gameBoard.getPos().getIntX()/2) - (int)(selectedCardSprite.getWidth()/2),
                (int)(GameScene.gameBoard.getPos().getIntY() + (GameScene.gameBoard.getSize().height / 2) - (1.1*selectedCardSprite.getHeight()))
            );
        } else {
            cardPos = new Vector2D(
                (int)((GameScene.gameBoard.getPos().getIntX()/2) - (int)(selectedCardSprite.getWidth()/2) + GameScene.gameBoard.getPos().getIntX() + GameScene.gameBoard.getSize().height),
                (int)(GameScene.gameBoard.getPos().getIntY() + (GameScene.gameBoard.getSize().height / 2) - (1.1*idleCardSprite.getHeight()))
            );
        }

        this.card1 = new Card(card1Name, cardPos, idleCardSprite, this);

        if (playerId == GameScene.PLAYER1) {
            cardPos = new Vector2D(
                (int)(GameScene.gameBoard.getPos().getIntX()/2) - (int)(idleCardSprite.getWidth()/2),
                (int)(GameScene.gameBoard.getPos().getIntY() + (GameScene.gameBoard.getSize().height / 2) + (0.1*idleCardSprite.getHeight()))
            );
        } else {
            cardPos = new Vector2D(
                (int)((GameScene.gameBoard.getPos().getIntX()/2) - (int)(idleCardSprite.getWidth()/2) + GameScene.gameBoard.getPos().getIntX() + GameScene.gameBoard.getSize().height),
                (int)(GameScene.gameBoard.getPos().getIntY() + (GameScene.gameBoard.getSize().height / 2) + (0.1*idleCardSprite.getHeight()))
                
            );
        }

        this.card2 = new Card(card2Name, cardPos, idleCardSprite, this);
    }

    private void createPieces() {
        Sprite kingSprite, pawnSprite;
        if (playerId == GameScene.PLAYER1) {
            kingSprite = new Sprite(Util.getImage("Onitama/res/Sprites/redKing.png"));   
            pawnSprite = new Sprite(Util.getImage("Onitama/res/Sprites/redPawn.png"));   
        } else {
            kingSprite = new Sprite(Util.getImage("Onitama/res/Sprites/blueKing.png"));   
            pawnSprite = new Sprite(Util.getImage("Onitama/res/Sprites/bluePawn.png"));
        }
        pieceMap = new PieceMap(GameScene.gameBoard.getSize(), GameScene.gameBoard.getPos(), this);

        int line = playerId == GameScene.PLAYER1 ? 4 : 0;

        pieces = new ArrayList<>();
        pieces.add(new Piece(pieceMap, (playerId == GameScene.PLAYER1 ? PieceType.RED_PAWN : PieceType.BLUE_PAWN), new Vector2D(0, line), pawnSprite));
        pieces.add(new Piece(pieceMap, (playerId == GameScene.PLAYER1 ? PieceType.RED_PAWN : PieceType.BLUE_PAWN), new Vector2D(1, line), pawnSprite));
        pieces.add(new Piece(pieceMap, (playerId == GameScene.PLAYER1 ? PieceType.RED_KING : PieceType.BLUE_KING), new Vector2D(2, line), kingSprite));
        pieces.add(new Piece(pieceMap, (playerId == GameScene.PLAYER1 ? PieceType.RED_PAWN : PieceType.BLUE_PAWN), new Vector2D(3, line), pawnSprite));
        pieces.add(new Piece(pieceMap, (playerId == GameScene.PLAYER1 ? PieceType.RED_PAWN : PieceType.BLUE_PAWN), new Vector2D(4, line), pawnSprite));

        pieceMap.updatePieces();

    }

    public void addToScene(Scene s) {
        s.addComponent(this);
        s.addComponent(pieceMap);
        card1.addCardToScene(s);
        card2.addCardToScene(s);
    }

     
}

