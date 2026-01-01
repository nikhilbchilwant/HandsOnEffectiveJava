package effectivejava.labs.chapter04.lab20;

/**
 * ============================================================================
 * LAB 20: Prefer Interfaces to Abstract Classes (Item 20)
 * ============================================================================
 * Chapter 4, pp. 99-104
 * 
 * SCENARIO:
 * A Singer and Songwriter are modeled as abstract classes.
 * A person who is BOTH cannot extend two classes! Interfaces solve this.
 * 
 * YOUR TASK:
 * TODO: Convert abstract classes to interfaces (with default methods if needed)
 * ============================================================================
 */
public class InterfacesVsAbstract {

    // =========================================================================
    // BAD: Abstract classes - can't have a SingerSongwriter!
    // =========================================================================

    static abstract class AbstractSinger {
        abstract void sing(String song);
    }

    static abstract class AbstractSongwriter {
        abstract String compose(String theme);
    }

    // Can't do this! Java doesn't support multiple inheritance of classes
    // static class SingerSongwriter extends AbstractSinger, AbstractSongwriter { }

    // =========================================================================
    // GOOD: Interfaces - can implement multiple!
    // =========================================================================

    interface Singer {
        void sing(String song);
    }

    interface Songwriter {
        String compose(String theme);
    }

    // Combining interfaces - no problem!
    interface SingerSongwriter extends Singer, Songwriter {
        // Can add new methods specific to singer-songwriters
        void performOwn();  // Sing your own composition
    }

    // Now we can implement the combined interface
    static class TaylorSwift implements SingerSongwriter {
        @Override
        public void sing(String song) {
            System.out.println("Taylor sings: " + song);
        }

        @Override
        public String compose(String theme) {
            return "A song about " + theme;
        }

        @Override
        public void performOwn() {
            String song = compose("love");
            sing(song);
        }
    }

    // =========================================================================
    // Skeletal Implementation pattern (AbstractInterface)
    // =========================================================================

    // Combine interface flexibility with abstract class code reuse
    interface Vending {
        void insertMoney(int cents);
        void selectItem(String item);
        void dispense();
    }

    // Skeletal implementation provides default behavior
    static abstract class AbstractVending implements Vending {
        protected int balance = 0;
        protected String selected = null;

        @Override
        public void insertMoney(int cents) {
            balance += cents;
            System.out.println("Balance: " + balance + " cents");
        }

        @Override
        public void selectItem(String item) {
            selected = item;
            System.out.println("Selected: " + item);
        }

        // Subclasses implement specifics
        @Override
        public abstract void dispense();
    }

    public static void main(String[] args) {
        System.out.println("=== Interfaces vs Abstract Classes ===\n");

        TaylorSwift taylor = new TaylorSwift();
        taylor.performOwn();

        System.out.println("\n--- Key Points ---");
        System.out.println("1. Interfaces allow multiple inheritance of type");
        System.out.println("2. Interfaces can have default methods (Java 8+)");
        System.out.println("3. Abstract classes for shared implementation");
        System.out.println("4. Skeletal implementation: AbstractInterface pattern");
    }
}
