package class12;

/**
 * Concrete Decorator - Caramel
 */
public class Caramel extends AddOnDecorator {

    public Caramel(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Caramel";
    }

    @Override
    public double cost() {
        return beverage.cost() + 25.0;
    }
}
