import ENGINE.Engine;
import GLOBAL.Configurations;
import INTERFACE.InterfaceTextuelle;

public class Onitama {
    public static void main(String[] args) {
        Engine eng = new Engine(5, 5);
        Configurations config = new Configurations();
        InterfaceTextuelle it = new InterfaceTextuelle(config, eng);
        //eng.printCards();
       while(!eng.gameOver())
       {
            it.display();
            eng.playTurn(it.getPiece(), it.getCard(), it.getMove());
            eng.changePlayer();
        /*
        eng.getGameConfiguration().displayConfig();
        eng.playTurn();
        eng.changePlayer();
        */
       }
    }
}
