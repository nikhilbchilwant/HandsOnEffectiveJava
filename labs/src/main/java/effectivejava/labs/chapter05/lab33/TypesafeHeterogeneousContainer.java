package effectivejava.labs.chapter05.lab33;

import java.util.*;

/**
 * ============================================================================
 * LAB 33: Consider Typesafe Heterogeneous Containers (Item 33)
 * ============================================================================
 * Chapter 5, pp. 151-156
 * 
 * SCENARIO:
 * Need a container that can store items of different types safely.
 * Normal generics limit you to one type parameter. THC pattern allows many!
 * 
 * YOUR TASK:
 * TODO: Implement a typesafe heterogeneous container
 * ============================================================================
 */
public class TypesafeHeterogeneousContainer {

    // =========================================================================
    // Typesafe Heterogeneous Container pattern
    // =========================================================================

    // Key is Class<T>, value is the instance of type T
    private final Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        // Use type.cast to ensure instance is actually of type T
        favorites.put(Objects.requireNonNull(type), type.cast(instance));
    }

    public <T> T getFavorite(Class<T> type) {
        // Safe cast - we only store T with Class<T> key
        return type.cast(favorites.get(type));
    }

    // =========================================================================
    // Why this works
    // =========================================================================

    // The relationship between key and value is:
    // Class<T> → T
    // 
    // But we can't express that directly in Java generics,
    // so we use Class<?> → Object and cast safely.

    // =========================================================================
    // Limitation: Can't use with generic types
    // =========================================================================

    public void limitation() {
        // This works:
        putFavorite(String.class, "Hello");
        putFavorite(Integer.class, 42);

        // This DOESN'T work:
        // putFavorite(List<String>.class, List.of("a"));  // NO! 
        // There's no List<String>.class - erasure!
        // Can only use List.class, losing the String type
    }

    // =========================================================================
    // Real-world example: Annotation retrieval
    // =========================================================================

    // java.lang.reflect uses this pattern:
    // <T extends Annotation> T getAnnotation(Class<T> annotationClass);

    public static void main(String[] args) {
        System.out.println("=== Typesafe Heterogeneous Container ===\n");

        TypesafeHeterogeneousContainer favorites = new TypesafeHeterogeneousContainer();

        favorites.putFavorite(String.class, "Java");
        favorites.putFavorite(Integer.class, 42);
        favorites.putFavorite(Class.class, TypesafeHeterogeneousContainer.class);

        // Type-safe retrieval!
        String favString = favorites.getFavorite(String.class);
        Integer favInt = favorites.getFavorite(Integer.class);
        Class<?> favClass = favorites.getFavorite(Class.class);

        System.out.printf("Favorite String: %s%n", favString);
        System.out.printf("Favorite Integer: %d%n", favInt);
        System.out.printf("Favorite Class: %s%n", favClass.getSimpleName());

        System.out.println("\n--- Pattern ---");
        System.out.println("Map<Class<?>, Object> where:");
        System.out.println("  Key Class<T> corresponds to value of type T");
        System.out.println("\nUsed in: getAnnotation, ServiceLoader, etc.");
    }
}
