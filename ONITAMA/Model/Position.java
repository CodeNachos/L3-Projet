package Model;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    int i;
    int j;

    public Position()
    {
        this.i = -1;
        this.j = -1;
    }
    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setI(int i)
    {
        this.i = i;
    }

    public void setJ(int j)
    {
        this.j = j;
    }

     @Override
     public boolean equals(Object o) {
         if (this == o)
             return true;
         if (o == null || getClass() != o.getClass())
             return false;
         Position position = (Position) o;
         return i == position.i && j == position.j;
     }
    
    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }


}
