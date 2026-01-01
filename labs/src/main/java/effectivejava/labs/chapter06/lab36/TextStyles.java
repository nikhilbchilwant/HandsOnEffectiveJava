package effectivejava.labs.chapter06.lab36;

import java.util.*;

/**
 * ============================================================================
 * LAB 36: Use EnumSet Instead of Bit Fields (Item 36)
 * ============================================================================
 * Chapter 6, pp. 169-171
 * 
 * SCENARIO:
 * Old code uses int bit flags for sets of options. This is type-unsafe
 * and hard to iterate. EnumSet is better in every way!
 * 
 * YOUR TASK:
 * TODO: Replace bit field with EnumSet
 * ============================================================================
 */
public class TextStyles {

    // =========================================================================
    // BAD: Bit field approach
    // =========================================================================
    
    public static final int STYLE_BOLD = 1 << 0;        // 1
    public static final int STYLE_ITALIC = 1 << 1;      // 2
    public static final int STYLE_UNDERLINE = 1 << 2;   // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3;  // 8

    public void applyStylesBitField(String text, int styles) {
        // Hard to print, iterate, or validate
        if ((styles & STYLE_BOLD) != 0) System.out.print("[BOLD]");
        if ((styles & STYLE_ITALIC) != 0) System.out.print("[ITALIC]");
        if ((styles & STYLE_UNDERLINE) != 0) System.out.print("[UNDERLINE]");
        if ((styles & STYLE_STRIKETHROUGH) != 0) System.out.print("[STRIKE]");
        System.out.println(" " + text);
    }

    // =========================================================================
    // GOOD: EnumSet approach
    // =========================================================================
    
    public enum Style {
        BOLD, ITALIC, UNDERLINE, STRIKETHROUGH
    }

    public void applyStylesEnumSet(String text, Set<Style> styles) {
        // Clean iteration, type-safe, efficient internally
        for (Style style : styles) {
            System.out.print("[" + style + "]");
        }
        System.out.println(" " + text);
    }

    public static void main(String[] args) {
        TextStyles demo = new TextStyles();

        System.out.println("=== Bit Fields vs EnumSet ===\n");

        // Old way: bit fields (confusing, error-prone)
        System.out.println("Bit field approach:");
        demo.applyStylesBitField("Hello", STYLE_BOLD | STYLE_ITALIC);

        // Can pass wrong values!
        demo.applyStylesBitField("Oops", 999);  // No type safety!

        // New way: EnumSet (clear, type-safe)
        System.out.println("\nEnumSet approach:");
        demo.applyStylesEnumSet("Hello",
                EnumSet.of(Style.BOLD, Style.ITALIC));

        // Type-safe: can only pass Style values
        // demo.applyStylesEnumSet("Oops", 999);  // Won't compile!

        System.out.println("\n--- Benefits of EnumSet ---");
        System.out.println("1. Type-safe (enum only)");
        System.out.println("2. Iterable");
        System.out.println("3. Efficient (bit vector internally)");
        System.out.println("4. Rich API (addAll, removeAll, etc.)");
    }
}
