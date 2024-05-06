package ENGINE;

import java.io.Serializable;

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
}
