public class Cell {
    private Ship ship;
    private boolean hit = false;

    public Cell(int row, int col) {
    }

    public void placeShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public Ship getShip() {
        return ship;
    }

    public void markHit() {
        this.hit = true;
    }

    public boolean isHit() {
        return hit;
    }
}
