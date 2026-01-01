package effectivejava.labs.chapter04.lab23;

/**
 * ============================================================================
 * LAB 23: Prefer Class Hierarchies to Tagged Classes (Item 23)
 * ============================================================================
 * Chapter 4, pp. 109-112
 * 
 * SCENARIO:
 * A "tagged class" uses a field to indicate its type, with switch statements
 * everywhere. This is verbose, error-prone, and violates OOP principles.
 * 
 * YOUR TASK:
 * TODO: Replace tagged class with a proper class hierarchy
 * ============================================================================
 */
public class TaggedFigure {

    // Tag indicating the type
    enum Shape { RECTANGLE, CIRCLE }

    final Shape shape;

    // Fields for rectangle
    double length;
    double width;

    // Fields for circle
    double radius;

    // =========================================================================
    // BAD: Tagged class constructors
    // =========================================================================
    
    // Constructor for circle
    TaggedFigure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // Constructor for rectangle
    TaggedFigure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    // =========================================================================
    // BAD: Switch on tag everywhere
    // =========================================================================
    
    double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * radius * radius;
            default:
                throw new AssertionError(shape);
        }
    }

    // Problems:
    // 1. Rectangle has unused radius field
    // 2. Circle has unused length/width fields
    // 3. Every method needs switch statement
    // 4. Adding new shape requires changing ALL methods
    // 5. Compiler can't enforce completeness

    // =========================================================================
    // TODO: Replace with class hierarchy
    // =========================================================================
    
    // abstract class Figure {
    //     abstract double area();
    // }
    //
    // class Circle extends Figure {
    //     final double radius;
    //     
    //     Circle(double radius) { this.radius = radius; }
    //     
    //     @Override
    //     double area() { return Math.PI * radius * radius; }
    // }
    //
    // class Rectangle extends Figure {
    //     final double length;
    //     final double width;
    //     
    //     Rectangle(double length, double width) {
    //         this.length = length;
    //         this.width = width;
    //     }
    //     
    //     @Override
    //     double area() { return length * width; }
    // }

    public static void main(String[] args) {
        System.out.println("=== Tagged Class Problems ===\n");

        TaggedFigure circle = new TaggedFigure(5);
        TaggedFigure rectangle = new TaggedFigure(3, 4);

        System.out.println("Circle area: " + circle.area());
        System.out.println("Rectangle area: " + rectangle.area());

        // Problem: Circle has unused length/width fields
        System.out.println("\nCircle's length field (unused): " + circle.length);
        System.out.println("Circle's width field (unused): " + circle.width);

        System.out.println("\n--- Convert to: ---");
        System.out.println("abstract class Figure { abstract double area(); }");
        System.out.println("class Circle extends Figure { ... }");
        System.out.println("class Rectangle extends Figure { ... }");
    }
}
