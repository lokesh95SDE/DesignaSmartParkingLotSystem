package class12;

import java.util.ArrayList;
import java.util.List;

/**
 * Order class representing a customer order
 * Part of Observer Pattern - Subject
 */
public class Order {
    private String orderId;
    private String customerName;
    private List<Beverage> beverages;
    private OrderStatus status;
    private List<OrderObserver> observers;

    public Order(String orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.beverages = new ArrayList<>();
        this.status = OrderStatus.PLACED;
        this.observers = new ArrayList<>();
    }

    public void addBeverage(Beverage beverage) {
        beverages.add(beverage);
    }

    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        System.out.println("\n[ORDER " + orderId + "] Status changed to: " + status);
        notifyObservers();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalCost() {
        return beverages.stream().mapToDouble(Beverage::cost).sum();
    }

    public void printOrderDetails() {
        System.out.println("\n========================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Status: " + status);
        System.out.println("----------------------------------------");
        System.out.println("Items:");
        for (int i = 0; i < beverages.size(); i++) {
            Beverage bev = beverages.get(i);
            System.out.println((i + 1) + ". " + bev.getDescription() + " - Rs." + bev.cost());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total: Rs." + getTotalCost());
        System.out.println("========================================");
    }
}
