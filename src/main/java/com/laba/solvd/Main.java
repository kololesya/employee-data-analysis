package com.laba.solvd;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Path to the file (can be adjusted depending on the environment)
        String filePath = "src/main/resources/Employees.xlsx";

        // Reading employees from the file (multithreading is not required at the reading stage)
        List<Employee> employees = EmployeeManager.readEmployeesFromFile(filePath);

        // Retrieving employee information (parallel processing)
        List<String> employeeInfo = EmployeeManager.getEmployeeInfo(employees);

        // Displaying employee information
        System.out.println("Employee Info:");
        employeeInfo.forEach(System.out::println);

        // Get a list of employees whose age is greater than or equal to 30
        List<Employee> filteredEmployees = EmployeeManager.filterEmployeesByAge(employees, 30);

        // Display filtered employees
        System.out.println("\nEmployees older than 30:");
        filteredEmployees.forEach(System.out::println);

        // Calculate average salary (parallel computation)
        double averageSalary = EmployeeManager.calculateAverageSalary(employees);
        System.out.println("\nAverage Salary: " + averageSalary);
    }
}
