package effectivejava.labs.chapter04.lab19;

/**
 * ============================================================================
 * LAB 19: Design and Document for Inheritance or Prohibit It (Item 19)
 * ============================================================================
 * Chapter 4, pp. 93-99
 * 
 * SCENARIO:
 * A class designed for inheritance must document self-use patterns.
 * This class has undocumented self-use that breaks subclasses.
 * 
 * YOUR TASK:
 * TODO #1: Document that removeRange is called by clear()
 * TODO #2: Or make the class final to prohibit inheritance
 * ============================================================================
 */
public class DocumentedList<E> {

    private Object[] elements = new Object[10];
    private int size = 0;

    public void add(E element) {
        if (size >= elements.length) {
            elements = java.util.Arrays.copyOf(elements, size * 2);
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (E) elements[index];
    }

    public int size() { return size; }

    // =========================================================================
    // PROBLEM: Undocumented self-use!
    // clear() calls removeRange(), but subclasses don't know this.
    // =========================================================================

    public void clear() {
        removeRange(0, size);  // Self-use! Not documented!
    }

    /**
     * Removes elements from fromIndex (inclusive) to toIndex (exclusive).
     * 
     * FIXME: Add this documentation:
     * 
     * @implSpec This method is called by {@code clear()} to remove all elements.
     * Subclasses that override this method should be aware that {@code clear()}
     * relies on this implementation.
     */
    protected void removeRange(int fromIndex, int toIndex) {
        // Shift elements left
        int numMoved = size - toIndex;
        System.arraycopy(elements, toIndex, elements, fromIndex, numMoved);
        
        // Null out for GC
        int newSize = size - (toIndex - fromIndex);
        for (int i = newSize; i < size; i++) {
            elements[i] = null;
        }
        size = newSize;
    }

    // =========================================================================
    // Why this matters: A subclass might override removeRange
    // =========================================================================

    static class CountingList<E> extends DocumentedList<E> {
        private int removeCount = 0;

        @Override
        protected void removeRange(int from, int to) {
            removeCount += (to - from);
            super.removeRange(from, to);
        }

        public int getRemoveCount() { return removeCount; }
    }

    public static void main(String[] args) {
        System.out.println("=== Document for Inheritance ===\n");

        CountingList<String> list = new CountingList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        System.out.println("Size before clear: " + list.size());
        list.clear();  // This calls removeRange internally!
        System.out.println("Size after clear: " + list.size());
        System.out.println("Remove count: " + list.getRemoveCount());

        System.out.println("\nThe subclass works ONLY because we happen to know");
        System.out.println("that clear() calls removeRange(). This MUST be documented!");

        System.out.println("\n--- Rules ---");
        System.out.println("1. Document all self-use of overridable methods");
        System.out.println("2. Use @implSpec tag for implementation notes");
        System.out.println("3. Or make the class final to prohibit inheritance");
    }
}
