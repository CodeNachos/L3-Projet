import org.json.simple.JSONObject;

import ENGINE.Engine;

public class Onitama {
    public static void main(String[] args) {
        Engine eng = new Engine(5,5);
        //eng.printCards();
       while(!eng.gameOver())
       {
        eng.getGameConfiguration().displayConfig();
        eng.playTurn();
        eng.changePlayer();
       }
    }
}
