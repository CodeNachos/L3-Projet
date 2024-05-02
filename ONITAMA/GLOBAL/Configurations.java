package GLOBAL;

public class Configurations {
    private static final String Interface = "Textual";
    String AI = "";
    String mode = "Normal"; //Normal = player vs player, AI = player vs AI, Automatic = AI vs AI
    String difficulty = "";
    
    
    public String getMode()
    {
        return mode;
    }

    public void setString(String m)
    {
        this.mode = m;
    }

    public void setAI(String AI)
    {
        this.AI = AI;
    }

    public String getAI()
    {
        return AI;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(String diff)
    {
        this.difficulty = diff;
    }


}
