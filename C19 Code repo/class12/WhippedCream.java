package class12;

/**
 * Concrete Decorator - Whipped Cream
 */
public class WhippedCream extends AddOnDecorator {

    public WhippedCream(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    @Override
    public double cost() {
        return beverage.cost() + 30.0;
    }
}
