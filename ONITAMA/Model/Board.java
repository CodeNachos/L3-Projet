package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    List<Piece> board;

    public Board()
    {
        board = new ArrayList<>();
    }

    public void initialiseBoard()
    {
        Piece pawn1_red = new Piece(Type.RED_PAWN, new Position(4, 0));
        Piece pawn2_red = new Piece(Type.RED_PAWN, new Position(4, 1));
        Piece pawn3_red = new Piece(Type.RED_PAWN, new Position(4, 3));
        Piece pawn4_red = new Piece(Type.RED_PAWN, new Position(4, 4));
        Piece RED_KING = new Piece(Type.RED_KING, new Position(4, 2));
        Piece pawn1_blue = new Piece(Type.BLUE_PAWN, new Position(0, 0));
        Piece pawn2_blue = new Piece(Type.BLUE_PAWN, new Position(0, 1));
        Piece pawn3_blue = new Piece(Type.BLUE_PAWN, new Position(0, 3));
        Piece pawn4_blue = new Piece(Type.BLUE_PAWN, new Position(0, 4));
        Piece BLUE_KING = new Piece(Type.BLUE_KING, new Position(0, 2));
        board.add(pawn1_red);
        board.add(pawn2_red);
        board.add(pawn3_red);
        board.add(pawn4_red);
        board.add(RED_KING);
        board.add(pawn1_blue);
        board.add(pawn2_blue);
        board.add(pawn3_blue);
        board.add(pawn4_blue);
        board.add(BLUE_KING);
        return;
    }

    public void updateBoard(Piece old_piece, Piece new_piece)
    {
        List<Position> listPositions = getPositionsList();
        //If piece we want to move in board:
        if (board.contains(old_piece)) {
            if (board.contains(new_piece)) {
                System.out.println("Trying to go to a spot that already contains your own pawn!");
                return;
            }
            //If the new position already exists in the board (regardless of the pawn)
            if (listPositions.contains(new_piece.getPosition())) {
                //Potentially trying to eat your own king or a king trying to eat his own pawn
                //Red case
                if ((old_piece.getType() == Type.RED_KING && new_piece.getType() == Type.RED_PAWN)
                        || (old_piece.getType() == Type.RED_PAWN && new_piece.getType() == Type.RED_KING)) {
                    System.out.println("Trying to go to a spot that already contains your own pawn!");
                    return;
                }
                //Blue case
                else if ((old_piece.getType() == Type.BLUE_KING && new_piece.getType() == Type.BLUE_PAWN)
                        || (old_piece.getType() == Type.BLUE_PAWN && new_piece.getType() == Type.BLUE_KING)) {
                    System.out.println("Trying to go to a spot that already contains your own pawn!");
                    return;
                }
                //If not king eating his servants (or other way around), then it must be a capture case (red eating blue or vice versa)
                else {
                    //Remove piece which will be eaten
                    remove(new_piece.getPosition());
                }
            }
            //Remove oldPiece
            board.remove(old_piece);
            //Add new_piece
            board.add(new_piece);
        } else {
            System.out.println("BOARD DOES NOT CONTAIN PIECE!");
            System.exit(1);
        }
    }
    
    //Method overload
    public void updateBoard(Board cpy,Piece old_piece, Piece new_piece)
    {
        List<Position> listPositions = getPositionsList(cpy);
        //If piece we want to move in board:
        if(cpy.getBoard().contains(old_piece))
        {
            if(cpy.getBoard().contains(new_piece))
            {
                System.out.println("Trying to go to a spot that already contains your own pawn!");
                return;
            }
            //If the new position already exists in the board (regardless of the pawn)
            if(listPositions.contains(new_piece.getPosition()))
            {
                //Potentially trying to eat your own king or a king trying to eat his own pawn
                //Red case
                if ((old_piece.getType() == Type.RED_KING && new_piece.getType() == Type.RED_PAWN)
                        || (old_piece.getType() == Type.RED_PAWN && new_piece.getType() == Type.RED_KING)) {
                    System.out.println("Trying to go to a spot that already contains your own pawn!");
                    return;
                }
                //Blue case
                else if ((old_piece.getType() == Type.BLUE_KING && new_piece.getType() == Type.BLUE_PAWN)
                        || (old_piece.getType() == Type.BLUE_PAWN && new_piece.getType() == Type.BLUE_KING)) {
                    System.out.println("Trying to go to a spot that already contains your own pawn!");
                    return;
                }
                //If not king eating his servants (or other way around), then it must be a capture case (red eating blue or vice versa)
                else {
                    //Remove piece which will be eaten
                    remove(cpy,new_piece.getPosition());
                }
            }
            //Remove oldPiece
                    cpy.getBoard().remove(old_piece);
                    //Add new_piece
                    cpy.getBoard().add(new_piece);
        }
        else {
            System.out.println("BOARD DOES NOT CONTAIN PIECE!");
            System.exit(1);
        }
    }

    public void remove(Position pos)
    {
        for (Iterator<Piece> iterator = board.iterator(); iterator.hasNext();) {
            Piece piece = iterator.next();
            Position piecePos = piece.getPosition();
            if (piecePos.equals(pos)) {
                iterator.remove();
                //System.out.println("Piece removed successfully");
                break;
            }
        }
    }

    //Method overload
    public void remove(Board cpy,Position pos)
    {
        for (Iterator<Piece> iterator = cpy.getBoard().iterator(); iterator.hasNext();) {
            Piece piece = iterator.next();
            Position piecePos = piece.getPosition();
            if (piecePos.equals(pos)) {
                iterator.remove();
                //System.out.println("Piece removed successfully");
                break;
            }
        }
    }

    public List<Position> getPositionsList()
    {
        List<Position> list_pos = new ArrayList<>();
        for (Piece piece : board) {
            Position pos = piece.getPosition();
            list_pos.add(pos);
        }
        return list_pos;
    }

    public List<Position> getRedPositions()
    {
        List<Position> redPos = new ArrayList<>();
        for(Piece piece:board)
        {
            if (piece.getType() == Type.RED_PAWN || piece.getType() == Type.RED_KING)
                redPos.add(piece.getPosition());
        }
        return redPos;
    }

    public List<Position> getBluePositions()
    {
        List<Position> bluePos = new ArrayList<>();
        for(Piece piece:board)
        {
            if (piece.getType() == Type.BLUE_PAWN || piece.getType() == Type.BLUE_KING)
                bluePos.add(piece.getPosition());
        }
        return bluePos;
    }

    public List<Position> getPositionsList(Board cpy)
    {
        List<Position> list_pos = new ArrayList<>();
        for(Piece piece: cpy.getBoard())
        {
            Position pos = piece.getPosition();
            list_pos.add(pos);
        }
        return list_pos;
    }

    public void setBoard(List<Piece> brd)
    {
        this.board = brd;
    }

    public List<Piece> getBoard()
    {
        return board;
    }

    public Position getRedKing()
    {
        for (Piece piece : board) {
            if (piece.getType() == Type.RED_KING) {
                return piece.getPosition();
            }
        }
        //Shouldn't happen...
        return new Position(-1, -1);
    }

    public Position getBlueKing()
    {
        for (Piece piece : board) {
            if (piece.getType() == Type.BLUE_KING) {
                return piece.getPosition();
            }
        }
        //Shouldn't happen...
        return new Position(-1, -1);
    }

    public Type giveType(Position position)
    {
        for (Piece piece : board) {
            if (piece.getPosition().equals(position)) {
                return piece.getType();
            }
        }
        return Type.INCORRECT;
    }
    
    public boolean checkPosition(Position pos)
    {
        for(Piece piece: board)
        {
            if (piece.getPosition().equals(pos))
                return true;
        }
        return false;
    }
    





}
