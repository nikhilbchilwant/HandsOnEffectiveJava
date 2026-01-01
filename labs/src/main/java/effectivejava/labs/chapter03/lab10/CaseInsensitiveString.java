package effectivejava.labs.chapter03.lab10;

/**
 * ============================================================================
 * LAB 10: Obey the equals() Contract (Item 10)
 * ============================================================================
 * 
 * SCENARIO:
 * This CaseInsensitiveString class attempts to interoperate with String in
 * its equals() method. This BREAKS the symmetry contract!
 * 
 * PROBLEM:
 * - cis.equals("hello") returns TRUE (we implemented it)
 * - "hello".equals(cis) returns FALSE (String doesn't know about us)
 * - This violates: x.equals(y) must equal y.equals(x)
 * 
 * YOUR TASK:
 * TODO: Fix equals() to ONLY compare with other CaseInsensitiveString objects
 *       Do NOT try to interoperate with String!
 * 
 * THE CONTRACT (must satisfy all):
 * - Reflexive:  x.equals(x) is true
 * - Symmetric:  x.equals(y) == y.equals(x)
 * - Transitive: if x.equals(y) && y.equals(z), then x.equals(z)
 * - Consistent: multiple calls return same result (if objects unchanged)
 * - Non-null:   x.equals(null) is false
 * 
 * VALIDATION:
 * Run this before/after:
 *   CaseInsensitiveString cis = new CaseInsensitiveString("Hello");
 *   String s = "hello";
 *   System.out.println(cis.equals(s));  // Before: true, After: false
 *   System.out.println(s.equals(cis));  // Always: false
 *   // Symmetry: both should return the same value!
 * ============================================================================
 */
public class CaseInsensitiveString {

    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = java.util.Objects.requireNonNull(s);
    }

    // =========================================================================
    // FIXME: This equals() violates symmetry by trying to work with String!
    // =========================================================================
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        // FIXME: Remove this block! String doesn't know about us!
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }

    // TODO: After fixing equals(), verify hashCode is still consistent
    @Override
    public int hashCode() {
        return s.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return s;
    }

    public String getValue() {
        return s;
    }

    // =========================================================================
    // Test your fix
    // =========================================================================
    
    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Hello");
        String s = "hello";

        System.out.println("=== SYMMETRY TEST ===");
        System.out.println("cis.equals(s): " + cis.equals(s));
        System.out.println("s.equals(cis): " + s.equals(cis));
        System.out.println("Symmetric? " + (cis.equals(s) == s.equals(cis)));

        System.out.println("\n=== SAME TYPE TEST ===");
        CaseInsensitiveString cis2 = new CaseInsensitiveString("HELLO");
        System.out.println("cis.equals(cis2): " + cis.equals(cis2));
        System.out.println("cis2.equals(cis): " + cis2.equals(cis));
        System.out.println("Symmetric? " + (cis.equals(cis2) == cis2.equals(cis)));

        System.out.println("\n=== HASHSET BEHAVIOR ===");
        java.util.Set<CaseInsensitiveString> set = new java.util.HashSet<>();
        set.add(new CaseInsensitiveString("Hello"));
        set.add(new CaseInsensitiveString("HELLO"));
        System.out.println("Set size (should be 1): " + set.size());
    }
}
