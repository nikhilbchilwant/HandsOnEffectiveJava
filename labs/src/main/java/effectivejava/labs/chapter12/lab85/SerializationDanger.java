package effectivejava.labs.chapter12.lab85;

import java.io.*;

/**
 * ============================================================================
 * LAB 85: Prefer Alternatives to Java Serialization (Item 85)
 * ============================================================================
 * Chapter 12, pp. 339-343
 * 
 * SCENARIO:
 * Code uses Java serialization for data persistence. This is DANGEROUS:
 * - Deserialization of untrusted data can execute arbitrary code
 * - "Gadget chains" can exploit any Serializable class on classpath
 * 
 * YOUR TASK:
 * TODO #1: NEVER deserialize untrusted data
 * TODO #2: Use JSON or other cross-platform formats instead
 * TODO #3: If you must use serialization, use ObjectInputFilter
 * ============================================================================
 */
public class SerializationDanger implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int value;

    public SerializationDanger(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // =========================================================================
    // DANGEROUS: ObjectInputStream on untrusted data!
    // =========================================================================
    
    // DON'T DO THIS with untrusted data!
    public static Object deserializeUnsafe(byte[] data) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            // DANGEROUS: Arbitrary code can execute here!
            // Gadget chains can trigger file writes, network requests,
            // or even remote code execution.
            return ois.readObject();
        }
    }

    // =========================================================================
    // BETTER: Use JSON (example with hypothetical JSON library)
    // =========================================================================
    
    // public static Object deserializeSafe(String json) {
    //     // JSON parsers don't execute code from the data
    //     return JsonParser.parse(json, MyClass.class);
    // }

    // =========================================================================
    // IF YOU MUST: Use ObjectInputFilter (Java 9+)
    // =========================================================================
    
    // public static Object deserializeFiltered(byte[] data) throws Exception {
    //     try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
    //          ObjectInputStream ois = new ObjectInputStream(bais)) {
    //         
    //         ois.setObjectInputFilter(info -> {
    //             if (info.serialClass() != null) {
    //                 String name = info.serialClass().getName();
    //                 if (!ALLOWED_CLASSES.contains(name)) {
    //                     return ObjectInputFilter.Status.REJECTED;
    //                 }
    //             }
    //             return ObjectInputFilter.Status.ALLOWED;
    //         });
    //         
    //         return ois.readObject();
    //     }
    // }

    @Override
    public String toString() {
        return "SerializationDanger{name='" + name + "', value=" + value + '}';
    }

    public static void main(String[] args) {
        System.out.println("=== Serialization Danger ===\n");

        System.out.println("Java serialization is a SECURITY RISK!");
        System.out.println();
        System.out.println("Deserialization can trigger:");
        System.out.println("- Arbitrary code execution");
        System.out.println("- Denial of service");
        System.out.println("- Data corruption");
        System.out.println();
        System.out.println("Alternatives:");
        System.out.println("1. JSON (Jackson, Gson)");
        System.out.println("2. Protocol Buffers");
        System.out.println("3. XML with safe parser");
        System.out.println();
        System.out.println("If you MUST use Java serialization:");
        System.out.println("- NEVER deserialize untrusted data");
        System.out.println("- Use ObjectInputFilter to whitelist classes");
    }
}
