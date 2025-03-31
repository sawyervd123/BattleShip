import java.util.*;

public class Ship {
    private final List<Cell> cells = new ArrayList<>();

    public Ship(int length) {
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public boolean isSunk() {
        return cells.stream().allMatch(Cell::isHit);
    }
}
