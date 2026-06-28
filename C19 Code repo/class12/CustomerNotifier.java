package class12;

/**
 * Concrete Observer - Notifies customer about order status
 */
public class CustomerNotifier implements OrderObserver {

    @Override
    public void update(Order order) {
        System.out.println("  [CUSTOMER NOTIFICATION] Dear " + order.getCustomerName() +
                         ", your order #" + order.getOrderId() +
                         " is now " + order.getStatus());
    }
}
