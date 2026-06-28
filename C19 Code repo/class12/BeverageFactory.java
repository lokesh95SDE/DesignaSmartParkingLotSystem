package class12;

/**
 * Factory Pattern - Creates different types of beverages
 */
public class BeverageFactory {

    public static Beverage createBeverage(BeverageType type) {
        switch (type) {
            case ESPRESSO:
                return new Espresso();
            case LATTE:
                return new Latte();
            case CAPPUCCINO:
                return new Cappuccino();
            case TEA:
                return new Tea();
            default:
                throw new IllegalArgumentException("Unknown beverage type: " + type);
        }
    }
}
