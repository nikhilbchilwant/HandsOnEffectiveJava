package effectivejava.labs.chapter05.lab28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * ============================================================================
 * LAB 28: Prefer Lists to Arrays (Item 28)
 * ============================================================================
 * Chapter 5, pp. 126-130
 * 
 * SCENARIO:
 * Arrays are covariant and reified. Generics are invariant and erased.
 * Mixing them causes problems. Lists are usually a better choice.
 * 
 * YOUR TASK:
 * TODO: Replace problematic array usage with List
 * ============================================================================
 */
public class ListsVsArrays {

    // =========================================================================
    // PROBLEM 1: Arrays are covariant (dangerous!)
    // =========================================================================

    public void arrayCovariance() {
        // Arrays: String[] is a subtype of Object[]
        Object[] objectArray = new String[10];  // Legal but dangerous!
        
        // This compiles but throws ArrayStoreException at runtime!
        // objectArray[0] = Integer.valueOf(42);
        
        // Generics: List<String> is NOT a subtype of List<Object>
        // List<Object> objectList = new ArrayList<String>();  // Compile error! GOOD!
    }

    // =========================================================================
    // PROBLEM 2: Generic array creation is illegal
    // =========================================================================

    public <E> void genericArrayProblem(List<E> list) {
        // ILLEGAL: Cannot create generic array
        // E[] array = new E[10];  // Compile error
        
        // ILLEGAL: Cannot create array of parameterized type
        // List<String>[] listArray = new List<String>[10];  // Compile error
        
        // Workaround (ugly and suppressed)
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[10];  // Works but unsafe
    }

    // =========================================================================
    // SOLUTION: Use List<E> instead of E[]
    // =========================================================================

    // BAD: Uses array - can't work with generics properly
    static class ChooserBad {
        private final Object[] choiceArray;

        public ChooserBad(Collection<?> choices) {
            choiceArray = choices.toArray();
        }

        public Object choose() {
            Random rnd = new Random();
            return choiceArray[rnd.nextInt(choiceArray.length)];
            // Caller must cast! Unsafe!
        }
    }

    // GOOD: Uses List - type-safe
    static class Chooser<T> {
        private final List<T> choiceList;

        public Chooser(Collection<T> choices) {
            choiceList = new ArrayList<>(choices);
        }

        public T choose() {
            Random rnd = new Random();
            return choiceList.get(rnd.nextInt(choiceList.size()));
            // No cast needed! Type-safe!
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Lists vs Arrays ===\n");

        // Using bad chooser - must cast
        ChooserBad bad = new ChooserBad(List.of("A", "B", "C"));
        String choice1 = (String) bad.choose();  // Cast required!
        System.out.println("Bad chooser: " + choice1);

        // Using good chooser - type-safe
        Chooser<String> good = new Chooser<>(List.of("A", "B", "C"));
        String choice2 = good.choose();  // No cast!
        System.out.println("Good chooser: " + choice2);

        System.out.println("\n--- Key Differences ---");
        System.out.println("Arrays: covariant, reified, can fail at runtime");
        System.out.println("Lists: invariant, erased, fail at compile time (better!)");
    }
}
