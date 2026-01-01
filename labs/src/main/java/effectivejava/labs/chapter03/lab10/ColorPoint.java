package effectivejava.labs.chapter03.lab10;

import java.awt.Color;

/**
 * ============================================================================
 * LAB 10 (Part 2): Transitivity Violation in Inheritance
 * ============================================================================
 * 
 * SCENARIO:
 * Point is fine by itself. ColorPoint extends Point and adds a color field.
 * This creates an unsolvable problem: equals() cannot satisfy both symmetry
 * AND transitivity when comparing Points to ColorPoints.
 * 
 * THE FUNDAMENTAL PROBLEM:
 * There is NO way to extend an instantiable class and add a value component
 * while preserving the equals contract.
 * 
 * YOUR TASK:
 * TODO: Choose ONE of these solutions:
 * 
 * OPTION A - Composition (Recommended):
 *   - ColorPoint HAS-A Point instead of IS-A Point
 *   - ColorPoint.asPoint() returns the contained Point
 *   - ColorPoints only equal other ColorPoints
 * 
 * OPTION B - getClass() instead of instanceof:
 *   - Change Point.equals() to use getClass() == o.getClass()
 *   - This makes Point != ColorPoint always
 *   - Violates Liskov Substitution Principle (trade-off!)
 * 
 * DEMONSTRATION OF THE BUG:
 *   ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
 *   Point p2 = new Point(1, 2);
 *   ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
 *   
 *   p1.equals(p2) → true  (ignores color when comparing to Point)
 *   p2.equals(p3) → true  (Point doesn't see color)
 *   p1.equals(p3) → FALSE (both ColorPoints, different colors!)
 *   
 *   Transitivity VIOLATED: p1==p2 && p2==p3 but p1!=p3
 * ============================================================================
 */
public class ColorPoint extends Point {

    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() { return color; }

    // =========================================================================
    // FIXME: This equals() violates transitivity!
    // It tries to be "nice" by ignoring color when comparing to plain Point,
    // but this breaks the contract.
    // =========================================================================
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }

        // FIXME: This "mixed comparison" approach doesn't work!
        // If o is a plain Point (not ColorPoint), do color-blind comparison
        if (!(o instanceof ColorPoint)) {
            return o.equals(this);  // Delegate to Point.equals
        }

        // Full ColorPoint comparison
        return super.equals(o) && ((ColorPoint) o).color.equals(color);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ColorPoint(%d, %d, %s)", getX(), getY(), color);
    }

    // =========================================================================
    // TODO: Implement composition-based solution
    // =========================================================================
    
    // Example of composition approach (create a new class or refactor this one):
    //
    // public class ColorPoint {  // Does NOT extend Point!
    //     private final Point point;
    //     private final Color color;
    //     
    //     public ColorPoint(int x, int y, Color color) {
    //         this.point = new Point(x, y);
    //         this.color = Objects.requireNonNull(color);
    //     }
    //     
    //     public Point asPoint() {
    //         return point;
    //     }
    //     
    //     @Override
    //     public boolean equals(Object o) {
    //         if (!(o instanceof ColorPoint cp)) return false;
    //         return cp.point.equals(point) && cp.color.equals(color);
    //     }
    // }

    // =========================================================================
    // Test the transitivity violation
    // =========================================================================
    
    public static void main(String[] args) {
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);

        System.out.println("=== TRANSITIVITY TEST ===");
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p2.equals(p3): " + p2.equals(p3));
        System.out.println("p1.equals(p3): " + p1.equals(p3));
        System.out.println();
        System.out.println("If p1==p2 and p2==p3, then p1 should == p3");
        System.out.println("But p1 != p3! TRANSITIVITY VIOLATED!");
    }
}
