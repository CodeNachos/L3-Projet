package JSON;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ENGINE.*;
public class JsonReader {
    List<Card> jsonCards;
    public JsonReader()
    {
        jsonCards = new ArrayList<>();
        readJson();
    }

    public List<Card> readJson()
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("cards.json")) {
            //Parse Json file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Get Json Array
            JSONArray cards = (JSONArray) jsonObject.get("cards");

            for(Object cardObject:cards )
            {
                Card card = new Card();
                JSONObject jCard = (JSONObject) cardObject;
                String cardName = (String) jCard.get("name");
                JSONArray configArray = (JSONArray) jCard.get("config");

                //System.out.println("Card Name: " + cardName);
                card.setString(cardName);
                for (Object configObj : configArray) {
                    JSONObject config = (JSONObject) configObj;
                    String player = (String) config.get("player");
                    JSONArray moves = (JSONArray) config.get("moves");

                    //System.out.println("  Player: " + player);
                    //System.out.println("  Moves:");

                    // Iterate over moves
                    for (Object moveObj : moves) {
                        JSONObject move = (JSONObject) moveObj;
                        long rowl = (long) move.get("row");
                        long coll = (long) move.get("col");
                        int row = (int) rowl;
                        int col = (int) coll;
                        //System.out.println("    Row: " + row + ", Col: " + col);
                        Movement movement = new Movement(row, col);
                        if (player.equals("Blue"))
                            card.addBlue(movement);
                        else
                            card.addRed(movement);
                    }
                }
                /* 
                System.out.println("Card name is: " + card.getName());
                for (Movement movement : card.getBlueMovement()) {
                    System.out.println("Movement for blue: " + movement.getDeltaRow() + " " + movement.getDeltaCol());
                }
                for (Movement movement : card.getRedMovement()) {
                    System.out.println("Movement for red: " + movement.getDeltaRow() + " " + movement.getDeltaCol());
                }
                */
                jsonCards.add(card);
                //System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonCards;
    }
}
