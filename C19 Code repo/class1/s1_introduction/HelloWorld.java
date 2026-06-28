package class1.s1_introduction;

import class1.s3_variables_scope.VariableScopeDemo;

/**
 * HelloWorld — The very first Java program.
 *
 * Every Java program needs:
 *  1. A class (same name as the file)
 *  2. A main method — this is where execution starts
 */
/*
class Car {
   color
   engine
   mileage

   stop()
   start()
   break()
}

objects - instance of a class
-----------------
__  __    __



------------------
 */
public class HelloWorld {

    // main()     // main() is the entry point of every Java applicationis the entry point of every Java application
    public static void main(String[] args) {
        VariableScopeDemo s1 = new VariableScopeDemo("Alice", 120);
        // System.out.println() prints text to the console and adds a new line
        System.out.println("Hello, World!");

        // System.out.print() prints without adding a new line at the end
        System.out.print("Java is ");
        System.out.print("fun!");

        System.out.println(); // just prints a blank new line
        System.out.println("Welcome to Backend Engineering Launchpad!");
    }
}
