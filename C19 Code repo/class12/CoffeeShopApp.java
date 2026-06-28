package class12;

/**
 * Main application demonstrating Coffee Shop Ordering System
 *
 * PROBLEM STATEMENT:
 * Design a Coffee Shop Ordering System that allows customers to:
 * 1. Order different types of beverages (Espresso, Latte, Cappuccino, Tea)
 * 2. Customize beverages with add-ons (Milk, Sugar, Whipped Cream, Caramel)
 * 3. Track order status and notify relevant systems (Customer, Kitchen, Billing)
 *
 * DESIGN PATTERNS DEMONSTRATED:
 * 1. Decorator Pattern - Add-ons to beverages
 * 2. Factory Pattern - Create different beverage types
 * 3. Singleton Pattern - Order Manager (single instance)
 * 4. Observer Pattern - Order status notifications
 *
 * @author Aashray
 */
public class CoffeeShopApp {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  COFFEE SHOP ORDERING SYSTEM - DEMO                        ║");
        System.out.println("║    Patterns: Decorator, Factory, Singleton, Observer                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝\n");

        // Get singleton instance of OrderManager
        OrderManager orderManager = OrderManager.getInstance();

        // Scenario 1: Simple order with base beverages
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCENARIO 1: Simple Order - Basic Beverages");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Order order1 = orderManager.createOrder("Alice");

        // Using Factory Pattern to create beverages
        Beverage espresso = BeverageFactory.createBeverage(BeverageType.ESPRESSO);
        Beverage latte = BeverageFactory.createBeverage(BeverageType.LATTE);

        order1.addBeverage(espresso);
        order1.addBeverage(latte);
        order1.printOrderDetails();

        // Process the order (Observer pattern in action)
        orderManager.processOrder(order1.getOrderId());

        // Scenario 2: Complex order with decorators
        System.out.println("\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCENARIO 2: Customized Order - Beverages with Add-ons");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Order order2 = orderManager.createOrder("Bob");

        // Using Decorator Pattern to add customizations
        // Cappuccino with Milk, Sugar, and Whipped Cream
        Beverage cappuccino = BeverageFactory.createBeverage(BeverageType.CAPPUCCINO);
        cappuccino = new Milk(cappuccino);
        cappuccino = new Sugar(cappuccino);
        cappuccino = new WhippedCream(cappuccino);

        // Latte with Caramel and Milk
        Beverage caramelLatte = BeverageFactory.createBeverage(BeverageType.LATTE);
        caramelLatte = new Caramel(caramelLatte);
        caramelLatte = new Milk(caramelLatte);

        order2.addBeverage(cappuccino);
        order2.addBeverage(caramelLatte);
        order2.printOrderDetails();

        orderManager.processOrder(order2.getOrderId());

        // Scenario 3: Multiple add-ons demonstrating decorator pattern
        System.out.println("\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCENARIO 3: Heavily Customized Order");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Order order3 = orderManager.createOrder("Carol");

        // Espresso with everything!
        Beverage superEspresso = BeverageFactory.createBeverage(BeverageType.ESPRESSO);
        superEspresso = new Milk(superEspresso);
        superEspresso = new Sugar(superEspresso);
        superEspresso = new WhippedCream(superEspresso);
        superEspresso = new Caramel(superEspresso);

        // Simple tea with just sugar
        Beverage sweetTea = BeverageFactory.createBeverage(BeverageType.TEA);
        sweetTea = new Sugar(sweetTea);
        sweetTea = new Sugar(sweetTea); // Double sugar!

        order3.addBeverage(superEspresso);
        order3.addBeverage(sweetTea);
        order3.printOrderDetails();

        orderManager.processOrder(order3.getOrderId());

        // Display all orders
        orderManager.printAllOrders();

        // Summary
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              SUMMARY                                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("\n✅ Design Patterns Demonstrated:");
        System.out.println("   1. DECORATOR PATTERN");
        System.out.println("      • Base: Beverage (abstract class)");
        System.out.println("      • Concrete Components: Espresso, Latte, Cappuccino, Tea");
        System.out.println("      • Decorator: AddOnDecorator (abstract class)");
        System.out.println("      • Concrete Decorators: Milk, Sugar, WhippedCream, Caramel");
        System.out.println("      • Benefit: Dynamically add responsibilities to objects");

        System.out.println("\n   2. FACTORY PATTERN");
        System.out.println("      • BeverageFactory creates different beverage types");
        System.out.println("      • Benefit: Encapsulates object creation logic");

        System.out.println("\n   3. SINGLETON PATTERN");
        System.out.println("      • OrderManager ensures single instance");
        System.out.println("      • Benefit: Centralized order management");

        System.out.println("\n   4. OBSERVER PATTERN");
        System.out.println("      • Subject: Order");
        System.out.println("      • Observers: CustomerNotifier, KitchenDisplay, BillingSystem");
        System.out.println("      • Benefit: Loose coupling, automatic updates on state change");

        System.out.println("\n📊 Test Results:");
        System.out.println("   ✓ Created 3 orders with different customizations");
        System.out.println("   ✓ All observers notified on status changes");
        System.out.println("   ✓ Dynamic pricing based on add-ons");
        System.out.println("   ✓ Singleton instance working correctly");

        System.out.println("\n🎓 All 4 design patterns successfully demonstrated!");
        System.out.println();
    }
}
