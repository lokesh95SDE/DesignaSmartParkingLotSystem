package class12;

/**
 * Concrete Beverage - Tea
 */
public class Tea extends Beverage {

    public Tea() {
        description = "Tea";
    }

    @Override
    public double cost() {
        return 50.0;
    }
}
