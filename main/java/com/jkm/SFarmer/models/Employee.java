package com.jkm.SFarmer.models;

public class Employee {    // Fields to hold the employee's data
    private String name;
    private String role;
    private String phoneNumber; // Example of other fields you might have
    private double salary;      // Example of other fields you might have

    // Constructor 1: Default constructor (takes no arguments)
    // The compiler found this one.
    public Employee() {
        // Default constructor
    }

    // THIS IS THE NEW CONSTRUCTOR YOU NEED TO ADD
    // It matches the call: new Employee("John Doe", "Tractor Driver")
    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
        // You can leave other fields as null or set default values
        this.phoneNumber = "N/A";
        this.salary = 0.0;
    }

    // Constructor 3: Full constructor (takes all arguments)
    // The compiler also found this one.
    public Employee(String name, String role, String phoneNumber, double salary) {
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

    // --- Getters and Setters for your fields ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
