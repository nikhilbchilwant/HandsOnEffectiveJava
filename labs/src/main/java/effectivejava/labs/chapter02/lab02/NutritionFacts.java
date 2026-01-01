package effectivejava.labs.chapter02.lab02;

import java.util.Objects;

/**
 * ============================================================================
 * REFERENCE: NutritionFacts - Bloch's Classic Builder Example
 * ============================================================================
 * From "Effective Java" 3rd Edition, Page 13
 * 
 * This is the CORRECT implementation of the Builder Pattern.
 * Compare with Notification.java to see the problem and solution.
 * ============================================================================
 */
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // Required parameters
        private final int servingSize;
        private final int servings;

        // Optional parameters - initialized to default values
        private int calories      = 0;
        private int fat           = 0;
        private int sodium        = 0;
        private int carbohydrate  = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings    = servings;
        }

        public Builder calories(int val)
        { calories = val;      return this; }
        public Builder fat(int val)
        { fat = val;           return this; }
        public Builder sodium(int val)
        { sodium = val;        return this; }
        public Builder carbohydrate(int val)
        { carbohydrate = val;  return this; }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        servingSize  = builder.servingSize;
        servings     = builder.servings;
        calories     = builder.calories;
        fat          = builder.fat;
        sodium       = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    @Override
    public String toString() {
        return String.format("NutritionFacts{serving=%dml x%d, cal=%d, fat=%d, sodium=%d, carb=%d}",
                servingSize, servings, calories, fat, sodium, carbohydrate);
    }

    public static void main(String[] args) {
        System.out.println("=== Bloch's Builder Pattern Example ===\n");

        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();

        System.out.println(cocaCola);

        System.out.println("\n--- Key Features ---");
        System.out.println("1. Required params in Builder constructor");
        System.out.println("2. Optional params via fluent setters");
        System.out.println("3. Immutable result object");
        System.out.println("4. Private constructor taking Builder");
    }
}
