package class12;

/**
 * Concrete Beverage - Cappuccino
 */
public class Cappuccino extends Beverage {

    public Cappuccino() {
        description = "Cappuccino";
    }

    @Override
    public double cost() {
        return 140.0;
    }
}
