import ENGINE.Engine;
import GLOBAL.Configurations;
import INTERFACE.InterfaceTextuelle;

public class Onitama {
    public static void main(String[] args) {
        Engine eng = new Engine(5, 5);
        Configurations config = new Configurations();
        InterfaceTextuelle it = new InterfaceTextuelle(config, eng);
        //eng.printCards();
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();
        System.out.println("Before undoing:");
        eng.getGameConfiguration().displayConfig();
        System.out.println("About to undo:");
        eng.undo();
        System.out.println("Before undoing:");
        eng.getGameConfiguration().displayConfig();
        System.out.println("About to undo:");
        eng.undo();
        System.out.println("Before redoing:");
        eng.getGameConfiguration().displayConfig();
        System.out.println("About to redo:");
        eng.redo();
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();



        /*
       while(!eng.gameOver())
       {
            it.display();
            eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
            eng.changePlayer();
        
        eng.getGameConfiguration().displayConfig();
        eng.playTurn();
        eng.changePlayer();
        */
       }
    }

