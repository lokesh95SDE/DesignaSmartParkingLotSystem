package class12;

/**
 * Concrete Decorator - Sugar
 */
public class Sugar extends AddOnDecorator {

    public Sugar(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return beverage.cost() + 5.0;
    }
}
