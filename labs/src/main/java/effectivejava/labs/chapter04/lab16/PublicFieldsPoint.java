package effectivejava.labs.chapter04.lab16;

/**
 * ============================================================================
 * LAB 16: In Public Classes, Use Accessor Methods, Not Public Fields (Item 16)
 * ============================================================================
 * Chapter 4, pp. 78-80
 * 
 * SCENARIO:
 * A Point class exposes public fields. This breaks encapsulation and
 * makes it impossible to change the internal representation later.
 * 
 * YOUR TASK:
 * TODO: Replace public fields with private fields + getters/setters
 * ============================================================================
 */
public class PublicFieldsPoint {

    // =========================================================================
    // FIXME: Public fields break encapsulation!
    // =========================================================================
    
    public double x;  // Anyone can access and modify directly!
    public double y;

    public PublicFieldsPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // PROBLEM: Can't add validation, logging, or change representation!
    
    // TODO: Refactor to:
    // private double x;
    // private double y;
    //
    // public double getX() { return x; }
    // public double getY() { return y; }
    //
    // public void setX(double x) {
    //     // Can now add validation!
    //     this.x = x;
    // }

    public static void main(String[] args) {
        System.out.println("=== Public Fields Problem ===\n");

        PublicFieldsPoint p = new PublicFieldsPoint(1, 2);

        // Direct field access - no validation possible
        p.x = Double.NaN;  // Invalid value!
        p.y = Double.NEGATIVE_INFINITY;

        System.out.println("Set x to NaN, y to -Infinity");
        System.out.println("No way to prevent this with public fields!");

        System.out.println("\n--- Solution ---");
        System.out.println("Use private fields + accessors");
        System.out.println("Then you can add validation in setters");
    }
}
