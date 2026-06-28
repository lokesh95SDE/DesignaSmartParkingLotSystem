package class12;

/**
 * Concrete Observer - Updates billing system
 */
public class BillingSystem implements OrderObserver {

    @Override
    public void update(Order order) {
        if (order.getStatus() == OrderStatus.DELIVERED) {
            System.out.println("  [BILLING SYSTEM] Order #" + order.getOrderId() +
                             " completed. Amount: Rs." + order.getTotalCost() +
                             " - Payment processed");
        }
    }
}
