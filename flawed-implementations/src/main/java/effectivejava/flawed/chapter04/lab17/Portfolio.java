package effectivejava.flawed.chapter04.lab17;

import java.util.ArrayList;
import java.util.List;

/**
 * FLAWED IMPLEMENTATION - Mutable portfolio with exposed internals
 * 
 * This portfolio exposes its internal list, allowing external code to
 * modify the positions directly, bypassing any validation or notification.
 */
public class Portfolio {

    private final String name;
    private final List<Position> positions;  // Mutable list!

    public Portfolio(String name, List<Position> initialPositions) {
        this.name = name;
        // PROBLEM: Storing reference directly - caller can modify!
        this.positions = initialPositions;
    }

    public String getName() {
        return name;
    }

    // PROBLEM: Returns internal mutable list!
    public List<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public int getPositionCount() {
        return positions.size();
    }

    public Money getTotalValue() {
        // Simplified - assumes all same currency
        return positions.stream()
                .map(Position::getValue)
                .reduce((a, b) -> {
                    a.add(b);
                    return a;
                })
                .orElse(new Money(0, "USD"));
    }

    /**
     * A position in the portfolio.
     */
    public static class Position {
        private String symbol;       // Mutable!
        private int quantity;        // Mutable!
        private Money purchasePrice; // Mutable!

        public Position(String symbol, int quantity, Money purchasePrice) {
            this.symbol = symbol;
            this.quantity = quantity;
            this.purchasePrice = purchasePrice;
        }

        // All mutable!
        public String getSymbol() { return symbol; }
        public void setSymbol(String symbol) { this.symbol = symbol; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public Money getPurchasePrice() { return purchasePrice; }
        public void setPurchasePrice(Money price) { this.purchasePrice = price; }

        public Money getValue() {
            Money value = new Money(purchasePrice.getAmount(), purchasePrice.getCurrency().getCurrencyCode());
            value.multiply(java.math.BigDecimal.valueOf(quantity));
            return value;
        }

        @Override
        public String toString() {
            return String.format("%s: %d @ %s", symbol, quantity, purchasePrice);
        }
    }
}
