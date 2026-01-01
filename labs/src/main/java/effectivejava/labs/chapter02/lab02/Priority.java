package effectivejava.labs.chapter02.lab02;

public enum Priority {
    LOW(1), NORMAL(2), HIGH(3), URGENT(4), CRITICAL(5);
    
    private final int level;
    
    Priority(int level) { this.level = level; }
    public int getLevel() { return level; }
}
