package class12;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Pattern - Manages all orders in the system
 */
public class OrderManager {
    private static OrderManager instance;
    private Map<String, Order> orders;
    private int orderCounter;

    private OrderManager() {
        orders = new HashMap<>();
        orderCounter = 1;
    }

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public Order createOrder(String customerName) {
        String orderId = "ORD" + String.format("%03d", orderCounter++);
        Order order = new Order(orderId, customerName);

        // Register default observers
        order.registerObserver(new CustomerNotifier());
        order.registerObserver(new KitchenDisplay());
        order.registerObserver(new BillingSystem());

        orders.put(orderId, order);
        return order;
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public void processOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.PREPARING);

            // Simulate processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            order.setStatus(OrderStatus.READY);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            order.setStatus(OrderStatus.DELIVERED);
        }
    }

    public void printAllOrders() {
        System.out.println("\n========== ALL ORDERS ==========");
        for (Order order : orders.values()) {
            System.out.println("Order #" + order.getOrderId() +
                             " - " + order.getCustomerName() +
                             " - " + order.getStatus() +
                             " - Rs." + order.getTotalCost());
        }
        System.out.println("================================\n");
    }
}
