
public class CellTable {

    public static int M = 13;

    private Cell[] cells;

    public CellTable() {
        cells = new Cell[M];
    }

    public void register(Body body) {
        AABB box = body.sweptVolume;

        int firstRow = (int) Math.floor(box.bottom / Cell.SIZE);
        int lastRow = (int) Math.ceil(box.top / Cell.SIZE);
        int firstCol = (int) Math.floor(box.left / Cell.SIZE);
        int lastCol = (int) Math.ceil(box.right / Cell.SIZE);

        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstCol; col <= lastCol; col++) {
                add(col, row, body);
            }
        }

    }

    public void add(int col, int row, Body body) {

    }

}
