package class12;

/**
 * Concrete Observer - Updates kitchen display system
 */
public class KitchenDisplay implements OrderObserver {

    @Override
    public void update(Order order) {
        if (order.getStatus() == OrderStatus.PLACED ||
            order.getStatus() == OrderStatus.PREPARING) {
            System.out.println("  [KITCHEN DISPLAY] Order #" + order.getOrderId() +
                             " - " + order.getBeverages().size() +
                             " item(s) - Status: " + order.getStatus());
        }
    }
}
