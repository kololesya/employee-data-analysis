package com.laba.solvd;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeManager {

    // Метод для чтения сотрудников из Excel файла (с использованием параллельных потоков)
    public static List<Employee> readEmployeesFromFile(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Пропускаем заголовок
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                // Извлекаем данные из строки
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

    // Функция для получения имени и отдела сотрудника
    public static Function<Employee, String> nameAndDepartment = employee ->
            employee.getName() + " - " + employee.getDepartment();

    // Получение информации о сотрудниках в виде списка строк (с использованием параллельных потоков)
    public static List<String> getEmployeeInfo(List<Employee> employees) {
        return employees.parallelStream() // Параллельный поток
                .map(nameAndDepartment)
                .collect(Collectors.toList());
    }

    // Фильтрация сотрудников, чей возраст больше заданного (с учетом текущего года)
    public static List<Employee> filterEmployeesByAge(List<Employee> employees, int minAge) {
        // Получаем текущий год
        int currentYear = LocalDate.now().getYear();

        return employees.parallelStream() // Параллельный поток
                .filter(e -> (currentYear - e.getYearOfBirth()) >= minAge) // Фильтрация по возрасту
                .collect(Collectors.toList());
    }

    // Вычисление средней зарплаты (с использованием параллельных потоков)
    public static double calculateAverageSalary(List<Employee> employees) {
        return employees.parallelStream() // Параллельный поток
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }
}
