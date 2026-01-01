package effectivejava.labs.chapter03.lab14;

import java.util.*;

/**
 * ============================================================================
 * LAB 14: Consider Implementing Comparable (Item 14)
 * ============================================================================
 * Chapter 3, pp. 66-72
 * 
 * SCENARIO:
 * A Version class needs to be sorted (1.0 < 1.1 < 2.0 etc).
 * Currently it doesn't implement Comparable, so sorting fails.
 * 
 * YOUR TASK:
 * TODO #1: Implement Comparable<Version>
 * TODO #2: compareTo() must be consistent with equals()
 * TODO #3: Use compare methods instead of subtraction (overflow risk!)
 * ============================================================================
 */
public class Version {

    private final int major;
    private final int minor;
    private final int patch;

    public Version(int major, int minor, int patch) {
        if (major < 0 || minor < 0 || patch < 0) {
            throw new IllegalArgumentException("Version numbers must be non-negative");
        }
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int getMajor() { return major; }
    public int getMinor() { return minor; }
    public int getPatch() { return patch; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Version)) return false;
        Version v = (Version) o;
        return major == v.major && minor == v.minor && patch == v.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }

    // =========================================================================
    // FIXME: Implement Comparable!
    // =========================================================================
    
    // TODO: Add "implements Comparable<Version>" to class declaration
    
    // TODO: Implement compareTo():
    //
    // @Override
    // public int compareTo(Version v) {
    //     // Compare major first
    //     int result = Integer.compare(major, v.major);
    //     if (result != 0) return result;
    //     
    //     // Then minor
    //     result = Integer.compare(minor, v.minor);
    //     if (result != 0) return result;
    //     
    //     // Finally patch
    //     return Integer.compare(patch, v.patch);
    // }
    //
    // DON'T DO: return major - v.major; 
    // (integer overflow can give wrong sign!)

    public static void main(String[] args) {
        System.out.println("=== Comparable Demo ===\n");

        List<Version> versions = new ArrayList<>();
        versions.add(new Version(2, 0, 0));
        versions.add(new Version(1, 10, 0));
        versions.add(new Version(1, 9, 1));
        versions.add(new Version(1, 9, 0));

        System.out.println("Before sort: " + versions);

        try {
            Collections.sort(versions);
            System.out.println("After sort: " + versions);
        } catch (ClassCastException e) {
            System.out.println("SORT FAILED!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nCause: Version doesn't implement Comparable.");
            System.out.println("Fix: Add 'implements Comparable<Version>'");
        }

        System.out.println("\n--- Expected after fix ---");
        System.out.println("[1.9.0, 1.9.1, 1.10.0, 2.0.0]");
    }
}
