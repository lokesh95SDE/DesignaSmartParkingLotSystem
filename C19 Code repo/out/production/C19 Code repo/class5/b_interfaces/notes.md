# Interfaces

## What is an Interface?
A reference type in Java, similar to a class, that can contain only constants,
method signatures, default methods, static methods, and nested types.
All methods in an interface are **abstract by default** (except default and static methods).

## Characteristics:
- Can only have **abstract methods** (until Java 7).
- From **Java 8**, can have **default** and **static** methods.
- From **Java 9**, can have **private** methods.
- **Cannot** have constructors.
- **Cannot** have instance variables — only `static` and `final` variables.
- A class can implement **multiple interfaces** (solves the diamond problem!).

## Interface vs Concrete Class

| Feature       | Interface                              | Concrete Class                    |
|---------------|----------------------------------------|-----------------------------------|
| Instantiation | Cannot be instantiated                 | Can be instantiated               |
| Methods       | Abstract, default, and static methods  | Concrete methods, static methods  |
| Variables     | Only static and final variables        | Can have instance variables       |

## Abstract Class vs Interface

| Feature              | Abstract Class                        | Interface                                  |
|----------------------|---------------------------------------|--------------------------------------------|
| Methods              | Abstract and concrete methods         | Abstract, default, static (since Java 8)   |
| Variables            | Can have instance variables           | Only static and final variables            |
| Multiple Inheritance | Does NOT support                      | Supports (a class can implement multiple)  |
| Constructors         | Can have constructors                 | Cannot have constructors                   |
| Implementation       | Can provide method implementation     | Cannot (except default and static methods) |

## When to Use?
- **Interface**: When you want to define a **contract** that unrelated classes can implement.
- **Abstract Class**: When you want to share **common code** among closely related classes.
- Avoid State in Interface: Interface should NOT store changing data.
- Interface should not have instance state 
- Only constants are safe to share 
- Supports multiple inheritance cleanly 
- Keeps design simple and predictable

## other Characteristics
- Class as higher priority ```class C extends A implements B { }```
- multiple inheritance Diamond problem ```class D extends B, C { }``` resolved by ```class D implements B, C { } // ✅ Allowed``` Here B and C are Interface
- In Java 8 --> if use default method instead of abstract in interface --> Same Diamond problem comes -->resolved by ```@Override  or B.super.show()```
- Java solves the diamond problem in interfaces by requiring the implementing class to explicitly override conflicting default methods, ensuring no ambiguity.

## Example in this folder
- `InterfaceDemo.java` — Interface basics, multiple interface implementation, default methods.
