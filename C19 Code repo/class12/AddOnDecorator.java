package class12;

/**
 * Abstract decorator class for beverage add-ons
 * Part of Decorator Pattern - Decorator
 */
public abstract class AddOnDecorator extends Beverage {
    protected Beverage beverage;

    public abstract String getDescription();
}
