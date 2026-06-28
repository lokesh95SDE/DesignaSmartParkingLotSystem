package class10.e_practice_problem;

/**
 * PRACTICE PROBLEM: Notification System
 *
 * Build a notification system for an e-commerce platform that uses
 * all four behavioral design patterns from this session.
 *
 * See notes.md for the full problem statement.
 */
public class ProblemStatement {
    public static void main(String[] args) {
        System.out.println("=== PRACTICE PROBLEM: E-Commerce Notification System ===");
        System.out.println();
        System.out.println("Build a notification system that combines:");
        System.out.println("  1. OBSERVER    — OrderService notifies listeners on new orders");
        System.out.println("  2. STRATEGY    — Multiple notification channels (Email, SMS, Push)");
        System.out.println("  3. CHAIN       — Fraud check pipeline before sending notification");
        System.out.println("  4. DECORATOR   — Add formatting (urgency prefix, timestamp, signature)");
        System.out.println();
        System.out.println("See notes.md for requirements and solution/ for the answer.");
    }
}
