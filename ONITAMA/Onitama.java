import Model.Engine;
import Controller.RandomAI;
import GLOBAL.Configurations;
import View.InterfaceTextuelle;

public class Onitama {
    public static void main(String[] args) throws Exception {
        Engine eng = new Engine(5, 5);
        Configurations config = new Configurations();
        RandomAI Ai = new RandomAI(eng);
        InterfaceTextuelle it = new InterfaceTextuelle(config, eng,Ai);
        
        //eng.printCards();

        /*
        eng.load("ONITAMA/gameSaveTest.txt");
        System.out.println("Before undoing:");
        eng.getGameConfiguration().displayConfig();
        System.out.println("About to undo:");
        eng.undo();
        it.display();
        eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
        eng.changePlayer();
        */
        
        
        /* 
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
        eng.save("ONITAMA/gameSaveTest.txt");
        */
        
        
        
        
        while(!eng.isGameOver())
        {
            it.display();
            eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
            eng.changePlayer();
        }
        /*
        eng.getGameConfiguration().displayConfig();
        eng.playTurn();
        eng.changePlayer();
        */
       }
    }

