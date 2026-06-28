package class12;

/**
 * Concrete Beverage - Latte
 */
public class Latte extends Beverage {

    public Latte() {
        description = "Latte";
    }

    @Override
    public double cost() {
        return 150.0;
    }
}
