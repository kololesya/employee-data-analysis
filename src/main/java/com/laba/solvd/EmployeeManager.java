package com.laba.solvd;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeManager {

    // Method for reading employees from an Excel file (using parallel streams)
    public static List<Employee> readEmployeesFromFile(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Skip the header
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                // Extract data from the row
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                int yearOfBirth = (int) row.getCell(2).getNumericCellValue();
                String department = row.getCell(3).getStringCellValue();
                double salary = row.getCell(4).getNumericCellValue();

                Employee employee = new Employee(id, name, yearOfBirth, department, salary);
                employees.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // Function to get the employee's name and department
    public static Function<Employee, String> nameAndDepartment = employee ->
            employee.getName() + " - " + employee.getDepartment();

    // Retrieve employee information as a list of strings (using parallel streams)
    public static List<String> getEmployeeInfo(List<Employee> employees) {
        return employees.parallelStream() // Parallel stream
                .map(nameAndDepartment)
                .collect(Collectors.toList());
    }

    // Filter employees whose age is greater than the specified value (considering the current year)
    public static List<Employee> filterEmployeesByAge(List<Employee> employees, int minAge) {
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        return employees.parallelStream() // Parallel stream
                .filter(e -> (currentYear - e.getYearOfBirth()) >= minAge) // Filter by age
                .collect(Collectors.toList());
    }

    // Calculate average salary (using parallel streams)
    public static double calculateAverageSalary(List<Employee> employees) {
        return employees.parallelStream() // Parallel stream
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }
}