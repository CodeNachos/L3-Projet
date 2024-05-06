package ENGINE;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    int i;
    int j;

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
