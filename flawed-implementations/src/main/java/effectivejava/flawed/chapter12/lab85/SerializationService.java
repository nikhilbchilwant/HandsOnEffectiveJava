package effectivejava.flawed.chapter12.lab85;

import java.io.*;

/**
 * FLAWED IMPLEMENTATION - Using Java Serialization for data transfer
 * 
 * Java serialization has fundamental security issues:
 * - Deserialization can trigger arbitrary code execution
 * - The attack surface includes all Serializable classes on classpath
 * - "Gadget chains" combine classes to form exploits
 * 
 * The safest approach: DON'T DESERIALIZE UNTRUSTED DATA
 */
public class SerializationService {

    /**
     * DANGEROUS: Deserializes arbitrary objects from stream.
     * 
     * If the bytes come from an untrusted source, this is a code execution 
     * vulnerability. Even filtering by expected class is insufficient because
     * the attack happens DURING deserialization, not after.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            // VULNERABLE: No filtering of what classes can be instantiated
            return (T) ois.readObject();
        }
    }

    /**
     * Serialize an object to bytes.
     * Serialization itself is not dangerous - deserialization is!
     */
    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            return baos.toByteArray();
        }
    }

    /**
     * STILL DANGEROUS: Type checking happens AFTER deserialization!
     * The malicious code has already executed by the time we cast.
     */
    public static <T> T deserializeTyped(byte[] data, Class<T> expectedType) 
            throws IOException, ClassNotFoundException {
        Object obj = deserialize(data);
        // Too late! Gadget chain has already executed in readObject()!
        return expectedType.cast(obj);
    }

    // THE PROBLEM:
    // 
    // 1. When you call ois.readObject(), the ObjectInputStream:
    //    - Reads class descriptors
    //    - Instantiates objects (calling constructors, readObject methods, etc.)
    //    - Resolves references
    //
    // 2. Attackers craft byte streams that:
    //    - Chain together existing classes on your classpath
    //    - Trigger dangerous operations in readObject(), finalize(), etc.
    //
    // 3. Common "gadget chains" exist for:
    //    - Spring, Commons Collections, Hibernate, etc.
    //    - These are NOT vulnerabilities in those libraries
    //    - They're normal classes used in unexpected contexts
    //
    // SOLUTIONS:
    // - Use JSON, XML, Protocol Buffers instead
    // - Use ObjectInputFilter if you MUST use serialization
    // - Never deserialize untrusted data
}
