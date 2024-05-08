package Model;

import java.util.LinkedList;

public class History {
    LinkedList<GameConfiguration> past;
    LinkedList<GameConfiguration> futur;
    Engine eng;

    public History(Engine eng)
    {
        this.eng = eng;
        past = new LinkedList<>();
        futur = new LinkedList<>();
    }

    public boolean canUndo()
    {
        return !past.isEmpty();
    }

    public boolean canRedo()
    {
        return !futur.isEmpty();
    }

    public void undo()
    {
        if (canUndo()) {
            futur.addFirst(eng.getGameConfiguration().copyConfig());
            eng.setConfig(past.removeFirst());
            eng.setPlayer(eng.getGameConfiguration().getCurrentPlayer());
        }
        return;
    }
    
    public void redo()
    {
        if (canRedo()) {
            past.addFirst(eng.getGameConfiguration().copyConfig());
            eng.setConfig(futur.removeFirst());
            eng.setPlayer(eng.getGameConfiguration().getCurrentPlayer());
        }
    }
    
    public LinkedList<GameConfiguration> getPast()
    {
        return past;
    }

    public LinkedList<GameConfiguration> getFutur()
    {
        return futur;
    }

    public void setPast(LinkedList<GameConfiguration> pst)
    {
        this.past = pst;
    }

    public void setFutur(LinkedList<GameConfiguration> ftr)
    {
        this.futur = ftr;
    }





    
    
}
