package Onitama.src.lib.json;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Engine.Structures.Vector2D;
import Onitama.src.Scenes.GameScene.Scripts.Card.CardInfo;

public class JsonReader {
    List<CardInfo> jsonCards;
    public JsonReader()
    {
        jsonCards = new ArrayList<>();
        //readJson(path);
    }

    public List<CardInfo> readJson(String path)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            //Parse Json file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            //Get Json Array
            JSONArray cards = (JSONArray) jsonObject.get("cards");

            for(Object cardObject:cards )
            {
                CardInfo card = new CardInfo();
                JSONObject jCard = (JSONObject) cardObject;
                String cardName = (String) jCard.get("name");
                JSONArray configArray = (JSONArray) jCard.get("config");

                //System.out.println("CardInfo Name: " + cardName);
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
                        Vector2D movement = new Vector2D(row, col);
                        if (player.equals("Blue"))
                            card.addBlue(movement);
                        else
                            card.addRed(movement);
                    }
                }
                /* 
                System.out.println("CardInfo name is: " + card.getName());
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
            e.printStackTrace();
        }
        return jsonCards;
    }
}