package class12;

/**
 * Concrete Decorator - Milk
 */
public class Milk extends AddOnDecorator {

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 20.0;
    }
}
