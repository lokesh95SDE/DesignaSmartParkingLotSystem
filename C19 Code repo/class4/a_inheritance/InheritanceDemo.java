package class4.a_inheritance;

public class InheritanceDemo {

    // ==================== SINGLE INHERITANCE ====================
    // One parent, one child

    static class Animal {
        String name;

        void eat() {
            System.out.println(name + " is eating.");
        }

        void sleep() {
            System.out.println(name + " is sleeping.");
        }
    }

    // Dog "is-a" Animal — inherits name, eat(), sleep()
    static class Dog extends Animal {
        String breed;

        void bark() {
            System.out.println(name + " says: Woof! Woof!");
        }
    }

    // ==================== MULTILEVEL INHERITANCE ====================
    // Chain: Vehicle -> Car -> ElectricCar

    static class Vehicle {
        String brand;

        void start() {
            System.out.println(brand + " vehicle started.");
        }
    }

    static class Car extends Vehicle {
        int numDoors;
        /*
        String brand;

        void start() {
            System.out.println(brand + " vehicle started.");
        }
         */
        void drive() {
            System.out.println(brand + " car is driving with " + numDoors + " doors.");
        }
    }

    // ElectricCar inherits from Car, which inherits from Vehicle
    static class ElectricCar extends Car {
        int batteryLife;
        /*int numDoors;
        /*
        String brand;

        void start() {
            System.out.println(brand + " vehicle started.");
        }

        void drive() {
            System.out.println(brand + " car is driving with " + numDoors + " doors.");
        }*/
        void chargeBattery() {
            System.out.println(brand + " is charging. Battery: " + batteryLife + " hrs.");
        }
    }

    // ==================== HIERARCHICAL INHERITANCE ====================
    // One parent, multiple children

    static class Shape {
        String color;

        void displayColor() {
            System.out.println("Color: " + color);
        }
    }

    static class Circle extends Shape {
        double radius;

        double area() {
            return Math.PI * radius * radius;
        }

        void displayColor() {
            System.out.println("Color: " + color);
        }
    }

    static class Rectangle extends Shape {
        double length, width;

        double area() {
            return length * width;
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Single Inheritance ---
        System.out.println("=== Single Inheritance ===");
        Dog dog = new Dog();
        dog.name = "Buddy";       // inherited from Animal
        dog.breed = "Labrador";   // Dog's own field
        dog.eat();                // inherited method
        dog.sleep();              // inherited method
        dog.bark();               // Dog's own method

        // --- Multilevel Inheritance ---
        System.out.println("\n=== Multilevel Inheritance ===");
        ElectricCar tesla = new ElectricCar();
        tesla.brand = "Tesla";       // from Vehicle (grandparent)
        tesla.numDoors = 4;          // from Car (parent)
        tesla.batteryLife = 12;      // ElectricCar's own field
        tesla.start();               // from Vehicle
        tesla.drive();               // from Car
        tesla.chargeBattery();       // ElectricCar's own method

        // --- Hierarchical Inheritance ---
        System.out.println("\n=== Hierarchical Inheritance ===");
        Circle circle = new Circle();
        circle.color = "Red";        // from Shape
        circle.radius = 5;
        circle.displayColor();       // inherited
        System.out.println("Circle area: " + circle.area());

        Rectangle rect = new Rectangle();
        rect.color = "Blue";         // from Shape
        rect.length = 4;
        rect.width = 6;
        rect.displayColor();         // inherited
        System.out.println("Rectangle area: " + rect.area());
    }
}
