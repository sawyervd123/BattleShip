import java.util.*;

public class Board {
    private final Cell[][] grid;
    private final List<Ship> ships = new ArrayList<>();
    private final int size;
    private final int[] shipSizes = {5, 4, 3, 3, 2};

    public Board(int size) {
        this.size = size;
        grid = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                grid[i][j] = new Cell(i, j);
    }

    public void placeShips() {
        Random rand = new Random();

        for (int length : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(size);
                int col = rand.nextInt(size);

                if (canPlace(row, col, length, horizontal)) {
                    Ship ship = new Ship(length);
                    for (int i = 0; i < length; i++) {
                        int r = row + (horizontal ? 0 : i);
                        int c = col + (horizontal ? i : 0);
                        grid[r][c].placeShip(ship);
                        ship.addCell(grid[r][c]);
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlace(int row, int col, int length, boolean horizontal) {
        for (int i = 0; i < length; i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (r >= size || c >= size || grid[r][c].hasShip())
                return false;
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean allShipsSunk() {
        return ships.stream().allMatch(Ship::isSunk);
    }
}
