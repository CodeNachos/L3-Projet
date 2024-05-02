package ENGINE;

public class Movement {
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
