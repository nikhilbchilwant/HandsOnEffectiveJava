package effectivejava.flawed.chapter03.lab10;

/**
 * FLAWED IMPLEMENTATION - equals() violates symmetry with String
 * 
 * This class attempts to interoperate with java.lang.String in equals(),
 * but String doesn't know about CaseInsensitiveString, breaking symmetry.
 */
public class CaseInsensitiveString {

    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = java.util.Objects.requireNonNull(s);
    }

    /**
     * BROKEN: Attempts to be symmetric with String, but String doesn't know
     * about CaseInsensitiveString, so:
     * 
     * CaseInsensitiveString cis = new CaseInsensitiveString("Hello");
     * String s = "hello";
     * cis.equals(s)  // true!
     * s.equals(cis)  // false! (String doesn't know about us)
     * 
     * This breaks the symmetry contract: x.equals(y) should equal y.equals(x)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        // PROBLEM: Trying to interoperate with String!
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }

    // Also problematic: hashCode should only consider CaseInsensitiveString comparisons
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
}
