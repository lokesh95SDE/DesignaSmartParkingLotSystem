package class12;

/**
 * Abstract base class for all beverages
 * Part of Decorator Pattern - Component
 */
public abstract class Beverage {
    protected String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
