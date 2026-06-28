package class10.e_practice_problem.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION: E-Commerce Notification System
 *
 * Combines all four behavioral patterns:
 *   Observer   → OrderService notifies listeners
 *   Strategy   → NotificationChannel (Email/SMS/Push)
 *   Chain      → FraudChecker pipeline
 *   Decorator  → Message formatting layers
 */
public class Main {

    // ═══════════════════════════════════════════════════════════════════════
    // ORDER (domain object)
    // ═══════════════════════════════════════════════════════════════════════
    static class Order {
        final String id, item, country, userId;
        final double amount;

        Order(String id, String item, double amount, String country, String userId) {
            this.id = id; this.item = item; this.amount = amount;
            this.country = country; this.userId = userId;
        }

        public String toString() { return "Order#" + id + ": " + item + " ($" + String.format("%.2f", amount) + ")"; }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // OBSERVER — OrderService is the Subject
    // ═══════════════════════════════════════════════════════════════════════
    interface OrderListener {
        void onOrderPlaced(Order order);
    }

    static class OrderService {
        private final List<OrderListener> listeners = new ArrayList<>();

        void addListener(OrderListener l) { listeners.add(l); }

        void placeOrder(Order order) {
            System.out.println("  [OrderService] New order placed: " + order);
            for (OrderListener l : listeners) {
                l.onOrderPlaced(order);
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // STRATEGY — Notification channels
    // ═══════════════════════════════════════════════════════════════════════
    interface NotificationChannel {
        void send(String message);
    }

    static class EmailChannel implements NotificationChannel {
        public void send(String msg) { System.out.println("  [EMAIL] " + msg); }
    }

    static class SMSChannel implements NotificationChannel {
        public void send(String msg) { System.out.println("  [SMS] " + msg); }
    }

    static class PushChannel implements NotificationChannel {
        public void send(String msg) { System.out.println("  [PUSH] " + msg); }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // CHAIN OF RESPONSIBILITY — Fraud checks
    // ═══════════════════════════════════════════════════════════════════════
    static abstract class FraudChecker {
        private FraudChecker next;

        FraudChecker setNext(FraudChecker next) { this.next = next; return next; }

        boolean check(Order order) {
            if (!passes(order)) return false;
            if (next != null) return next.check(order);
            return true;
        }

        abstract boolean passes(Order order);
    }

    static class AmountChecker extends FraudChecker {
        boolean passes(Order order) {
            if (order.amount > 10000) {
                System.out.println("  [Fraud] AmountChecker: BLOCKED — $"
                        + String.format("%.2f", order.amount) + " exceeds limit");
                return false;
            }
            System.out.println("  [Fraud] AmountChecker: PASSED");
            return true;
        }
    }

    static class LocationChecker extends FraudChecker {
        private final List<String> blacklist = List.of("NK", "SY");

        boolean passes(Order order) {
            if (blacklist.contains(order.country)) {
                System.out.println("  [Fraud] LocationChecker: BLOCKED — country " + order.country);
                return false;
            }
            System.out.println("  [Fraud] LocationChecker: PASSED");
            return true;
        }
    }

    static class FrequencyChecker extends FraudChecker {
        boolean passes(Order order) {
            // Simplified: always passes in demo
            System.out.println("  [Fraud] FrequencyChecker: PASSED");
            return true;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // DECORATOR — Message formatting
    // ═══════════════════════════════════════════════════════════════════════
    interface Message {
        String getContent();
    }

    static class BaseMessage implements Message {
        private final Order order;
        BaseMessage(Order order) { this.order = order; }
        public String getContent() { return "New order: " + order.item + " ($" + String.format("%.2f", order.amount) + ")"; }
    }

    static abstract class MessageDecorator implements Message {
        protected final Message wrapped;
        MessageDecorator(Message wrapped) { this.wrapped = wrapped; }
    }

    static class UrgencyDecorator extends MessageDecorator {
        private final double threshold;
        UrgencyDecorator(Message msg, double threshold) { super(msg); this.threshold = threshold; }
        public String getContent() {
            return "[URGENT] " + wrapped.getContent();
        }
    }

    static class TimestampDecorator extends MessageDecorator {
        TimestampDecorator(Message msg) { super(msg); }
        public String getContent() { return "2024-01-15 10:30 | " + wrapped.getContent(); }
    }

    static class SignatureDecorator extends MessageDecorator {
        SignatureDecorator(Message msg) { super(msg); }
        public String getContent() { return wrapped.getContent() + " — MyStore Inc."; }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // OBSERVER IMPLEMENTATIONS — wire everything together
    // ═══════════════════════════════════════════════════════════════════════
    static class InventoryService implements OrderListener {
        public void onOrderPlaced(Order order) {
            System.out.println("  [Inventory] Reserving stock for " + order);
        }
    }

    static class AnalyticsService implements OrderListener {
        public void onOrderPlaced(Order order) {
            System.out.println("  [Analytics] Tracking " + order);
        }
    }

    // NotificationService: Observer + uses Strategy + Chain + Decorator
    static class NotificationService implements OrderListener {
        private NotificationChannel channel;
        private final FraudChecker fraudChain;

        NotificationService(NotificationChannel channel, FraudChecker fraudChain) {
            this.channel = channel;
            this.fraudChain = fraudChain;
        }

        void setChannel(NotificationChannel channel) { this.channel = channel; }

        public void onOrderPlaced(Order order) {
            // Chain: fraud check
            if (!fraudChain.check(order)) return;

            // Decorator: build formatted message
            Message msg = new BaseMessage(order);
            msg = new TimestampDecorator(msg);
            if (order.amount > 5000) msg = new UrgencyDecorator(msg, 5000);
            msg = new SignatureDecorator(msg);

            // Strategy: send via chosen channel
            channel.send(msg.getContent());
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // DEMO
    // ═══════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        System.out.println("=== E-Commerce Notification System ===\n");

        // Build fraud check chain
        FraudChecker amountChk = new AmountChecker();
        amountChk.setNext(new LocationChecker()).setNext(new FrequencyChecker());

        // Create services (observers)
        InventoryService inventory = new InventoryService();
        AnalyticsService analytics = new AnalyticsService();
        NotificationService notifications = new NotificationService(new EmailChannel(), amountChk);

        // Register observers with OrderService
        OrderService orderService = new OrderService();
        orderService.addListener(inventory);
        orderService.addListener(analytics);
        orderService.addListener(notifications);

        // --- Order 1: Normal order, email ---
        System.out.println("--- Order 1: Normal order (Email) ---");
        orderService.placeOrder(new Order("1001", "Laptop", 999.00, "US", "user1"));

        // --- Order 2: High-value → blocked by fraud ---
        System.out.println("\n--- Order 2: High-value order (blocked) ---");
        orderService.placeOrder(new Order("1002", "Server Rack", 15000.00, "US", "user2"));

        // --- Order 3: Switch to SMS ---
        System.out.println("\n--- Order 3: Switch to SMS ---");
        notifications.setChannel(new SMSChannel());
        orderService.placeOrder(new Order("1003", "Keyboard", 79.00, "US", "user3"));

        // --- Order 4: Blacklisted country ---
        System.out.println("\n--- Order 4: Blacklisted country ---");
        orderService.placeOrder(new Order("1004", "Mouse", 25.00, "NK", "user4"));
    }
}
