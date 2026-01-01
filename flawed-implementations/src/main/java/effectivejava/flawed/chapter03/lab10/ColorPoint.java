package effectivejava.flawed.chapter03.lab10;

import java.awt.Color;

/**
 * FLAWED IMPLEMENTATION - extends Point and breaks equals contract
 * 
 * This demonstrates the fundamental problem: there's no way to extend an
 * instantiable class and add a value component while preserving equals contract.
 * 
 * Try 1: Ignore color when comparing to Point - violates symmetry
 * Try 2: Include color for all comparisons - violates transitivity
 */
public class ColorPoint extends Point {

    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() { return color; }

    /**
     * BROKEN VERSION - Choose one of the two broken implementations:
     * 
     * Version A: Violates symmetry
     * - ColorPoint ignores Point's color-less nature
     * 
     * Version B: Violates transitivity
     * - Mixed comparisons ignore color, same-type comparisons include it
     */
    @Override
    public boolean equals(Object o) {
        // Version B (broken transitivity):
        // This tries to be "nice" by ignoring color when comparing to plain Point
        // but this breaks transitivity!
        
        if (!(o instanceof Point)) {
            return false;
        }

        // If o is a plain Point (not ColorPoint), do color-blind comparison
        if (!(o instanceof ColorPoint)) {
            return o.equals(this);  // Delegate to Point.equals
        }

        // Full ColorPoint comparison
        return super.equals(o) && ((ColorPoint) o).color.equals(color);
    }

    /*
     * To see the transitivity violation:
     * 
     * ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
     * Point p2 = new Point(1, 2);
     * ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
     * 
     * p1.equals(p2)  // true (ignores color)
     * p2.equals(p3)  // true (Point doesn't see color)
     * p1.equals(p3)  // FALSE! (both ColorPoints, different colors)
     * 
     * Transitivity requires: if p1==p2 and p2==p3, then p1==p3
     * But p1 != p3, so transitivity is BROKEN!
     */

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ColorPoint(%d, %d, %s)", getX(), getY(), color);
    }
}
