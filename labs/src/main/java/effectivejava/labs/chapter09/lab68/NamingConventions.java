package effectivejava.labs.chapter09.lab68;

/**
 * ============================================================================
 * LAB 68: Adhere to Generally Accepted Naming Conventions (Item 68)
 * ============================================================================
 * Chapter 9, pp. 290-292
 * 
 * SCENARIO:
 * Code uses inconsistent or unconventional names. Hard to read!
 * 
 * YOUR TASK:
 * TODO: Apply standard Java naming conventions
 * ============================================================================
 */
public class NamingConventions {

    // =========================================================================
    // Package names: lowercase with hierarchy
    // =========================================================================

    // GOOD: com.google.common.collect
    // GOOD: org.apache.commons.lang
    // BAD:  MyPackage, myPackage, my_package

    // =========================================================================
    // Class/Interface names: UpperCamelCase (nouns/adjectives)
    // =========================================================================

    // GOOD: ArrayList, LinkedHashMap, Comparable, Runnable
    // BAD:  arraylist, ARRAY_LIST, array_list

    class PhoneNumber { }      // GOOD: noun
    interface Comparable { }   // GOOD: adjective
    interface Runnable { }     // GOOD: -able suffix

    // =========================================================================
    // Method names: lowerCamelCase (verbs/verb phrases)
    // =========================================================================

    void getValue() { }        // GOOD
    void isEmpty() { }         // GOOD: boolean getter
    void toArray() { }         // GOOD: conversion
    void asList() { }          // GOOD: view conversion

    // =========================================================================
    // Constant names: UPPER_SNAKE_CASE
    // =========================================================================

    static final int MAX_VALUE = 100;      // GOOD
    static final int MIN_CAPACITY = 16;    // GOOD
    // static final int maxValue = 100;    // BAD

    // =========================================================================
    // Variable names: lowerCamelCase (nouns)
    // =========================================================================

    int itemCount;             // GOOD
    String userName;           // GOOD
    // int item_count;         // BAD: not Java style
    // int ItemCount;          // BAD: looks like class

    // =========================================================================
    // Type parameter names: single uppercase letter
    // =========================================================================

    // T - arbitrary Type
    // E - Element (collections)
    // K - Key
    // V - Value
    // X - Exception
    // R - Return type

    class Box<T> { }
    interface Map<K, V> { }

    // =========================================================================
    // Special conventions
    // =========================================================================

    // Boolean getters: is/has prefix
    boolean isEmpty() { return true; }
    boolean hasNext() { return false; }

    // Conversion methods
    String toString() { return ""; }     // toXxx
    Object[] toArray() { return null; }

    // Static factories
    // valueOf, of, getInstance, newInstance, getType, newType

    public static void main(String[] args) {
        System.out.println("=== Naming Conventions ===\n");

        System.out.println("Type        | Convention      | Example");
        System.out.println("------------|-----------------|------------------");
        System.out.println("Package     | lower.dot.case  | java.util");
        System.out.println("Class       | UpperCamelCase  | ArrayList");
        System.out.println("Interface   | UpperCamelCase  | Comparable");
        System.out.println("Method      | lowerCamelCase  | getValue");
        System.out.println("Variable    | lowerCamelCase  | itemCount");
        System.out.println("Constant    | UPPER_SNAKE     | MAX_VALUE");
        System.out.println("Type param  | Single capital  | T, E, K, V");

        System.out.println("\n--- Special Prefixes ---");
        System.out.println("is/has      - boolean getters");
        System.out.println("get/set     - accessors");
        System.out.println("to          - conversion (toString)");
        System.out.println("as          - view (asList)");
        System.out.println("of/valueOf  - static factories");
    }
}
