package effectivejava.flawed.chapter03.lab10;

/**
 * FLAWED IMPLEMENTATION - Part of the inheritance hierarchy that breaks equals
 * 
 * Point by itself is fine. The problem arises when ColorPoint extends it
 * and adds a value component (color).
 */
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    /**
     * This equals() is correct for Point alone.
     * Problems arise in the subclass ColorPoint.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return String.format("Point(%d, %d)", x, y);
    }
}
