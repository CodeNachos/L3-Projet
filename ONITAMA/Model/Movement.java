package Model;

import java.io.Serializable;
import java.util.Objects;

public class Movement implements Serializable {
    int deltaRow;
    int deltaCol;

    public Movement(int i, int j) {
        this.deltaRow = i;
        this.deltaCol = j;
    }

    public int getDeltaRow()
    {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }

     @Override
     public boolean equals(Object o) {
         if (this == o)
             return true;
         if (o == null || getClass() != o.getClass())
             return false;
         Movement movement= (Movement) o;
         return deltaRow == movement.deltaRow &&  deltaCol== movement.deltaCol;
     }
    
    @Override
    public int hashCode() {
        return Objects.hash(deltaRow, deltaCol);
    }
}
