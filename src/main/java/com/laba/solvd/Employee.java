package com.laba.solvd;

class Employee {
    private int id;
    private String name;
    private int yearOfBirth;
    private String department;
    private double salary;

    // Constructor, getters and setters
    public Employee(int id, String name, int yearOfBirth, String department, double salary) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge(int currentYear) {
        return currentYear - yearOfBirth;
    }

    @Override
    public String toString() {
        return id + ". " + name + " (" + department + ")";
    }
}