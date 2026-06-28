package class4.c_this_and_super;

public class ThisSuperDemo {

    // ==================== 'this' KEYWORD ====================

    // 1. Using 'this' to differentiate instance variable from parameter
    static class Student {
        String name;
        int age;

        // Without 'this', the parameter shadows the instance variable
        Student(String name, int age) {
            this.name = name;   // this.name = instance variable
            this.age = age;     // this.age = instance variable
        }

        // 2. Using 'this()' for constructor chaining
        Student() {
//            this.name = "Unknown";
//            this.age = 0;
            this("Unknown", 0);  // calls the 2-arg constructor above
            System.out.println("Default constructor called via this()");
        }

        void display() {
            System.out.println("Student: " + name + ", Age: " + age);
        }
    }

    // ==================== 'super' KEYWORD ====================

    static class Animal {
        String name;
        String type = "Animal";

        Animal() {

        }
        Animal(String name) {
            this.name = name;
            System.out.println("  Animal constructor called for: " + name);
        }

        void makeSound() {
            System.out.println(name + " makes a generic sound.");
        }
    }

    static class Dog extends Animal {
        String breed;
        String type = "Dog";  // shadows parent's 'type' field
        /*
        Dog
        _________ // breed
        ref-Animal -

        ---some other animal instance

         */
        // 1. super() to call parent constructor
        Dog(String name, String breed) {
            //super();
            //super(name);  // MUST be the first line — calls Animal(String name)
            //super();
            super.type = this.type;
            this.breed = breed;
            //Animal animal = new Animal("");
            System.out.println("  Dog constructor called for: " + name);

        }

        // 2. super.method() to call parent's overridden method
        @Override
        void makeSound() {
            super.makeSound();  // calls Animal's makeSound()
            System.out.println(name + " says: Woof! Woof!");
        }

        // 3. super.variable to access parent's shadowed variable
        void showType() {
            System.out.println("this.type = " + this.type);    // "Dog"
            System.out.println("super.type = " + super.type);  // "Animal"
        }
    }

    static class Dog1 extends Dog {

        Dog1(String name, String breed) {
            super(name, breed);
        }

        @Override
        void makeSound() {
            super.makeSound();
        }

    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- 'this' keyword demos ---
        System.out.println("=== 'this' keyword ===");
        Student s1 = new Student("Rahul", 22);
        s1.display();

        System.out.println();
        Student s2 = new Student();  // uses this() to chain
        s2.display();

        // --- 'super' keyword demos ---
        System.out.println("\n=== 'super' keyword ===");
        System.out.println("Creating a Dog (watch constructor chain):");
        Dog dog = new Dog("Buddy", "Labrador");

        System.out.println("\nCalling overridden method (super.makeSound inside):");
        dog.makeSound();

        System.out.println("\nAccessing shadowed variable:");
        dog.showType();
    }
}
