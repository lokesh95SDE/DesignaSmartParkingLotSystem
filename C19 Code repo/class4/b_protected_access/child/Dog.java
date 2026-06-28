package class4.b_protected_access.child;

import class4.b_protected_access.parent.Animal;

// Dog is in a DIFFERENT package than Animal
// But it EXTENDS Animal, so it can access 'protected' members
public class Dog extends Animal {

    private String breed;

    public Dog(int id, String name, int age, String breed) {
        super(id, name, age, "Dog");
        this.breed = breed;
    }

    public void showDetails() {
        // protected fields — accessible because Dog is a SUBCLASS of Animal
        System.out.println("Dog name: " + name);     // protected — OK via inheritance
        System.out.println("Dog age: " + age);        // protected — OK via inheritance
        System.out.println("Dog breed: " + breed);    // private to Dog — OK

        // public field — always accessible
        System.out.println("Species: " + species);    // public — OK

        // private field from parent — NOT accessible even in subclass
         //System.out.println("ID: " + id);           // COMPILE ERROR!

        // protected method from parent — accessible in subclass
        displayInfo();                                 // protected — OK via inheritance
    }

    public static void main(String[] args) {
        System.out.println("=== Protected Access from Different Package Subclass ===");
        Dog dog = new Dog(101, "Buddy", 3, "Labrador");
        dog.showDetails();

        System.out.println("\n=== What a non-subclass in this package CANNOT do ===");
        // If we create an Animal object here (not a subclass relationship),
        // we CANNOT access protected members:
        Animal animal = new Animal(2, "Kitty", 2, "Cat");
        // animal.name = "Test";       // COMPILE ERROR! Different package, not a subclass reference
        // animal.displayInfo();       // COMPILE ERROR!
        animal.eat();                  // public — OK
        System.out.println(animal.species);  // public — OK
    }
}
