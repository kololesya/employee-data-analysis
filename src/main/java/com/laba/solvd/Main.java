package com.laba.solvd;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Путь к файлу (можно настроить в зависимости от среды)
        String filePath = "src/main/resources/Employees.xlsx";

        // Чтение сотрудников из файла (многопоточность не требуется на стадии чтения)
        List<Employee> employees = EmployeeManager.readEmployeesFromFile(filePath);

        // Получение информации о сотрудниках (параллельная обработка)
        List<String> employeeInfo = EmployeeManager.getEmployeeInfo(employees);

        // Вывод информации о сотрудниках
        System.out.println("Employee Info:");
        employeeInfo.forEach(System.out::println);

        // Получаем список сотрудников, чей возраст больше или равен 30
        List<Employee> filteredEmployees = EmployeeManager.filterEmployeesByAge(employees, 30);

        // Выводим отфильтрованных сотрудников
        System.out.println("\nEmployees older than 30:");
        filteredEmployees.forEach(System.out::println);

        // Вычисление средней зарплаты (параллельный расчет)
        double averageSalary = EmployeeManager.calculateAverageSalary(employees);
        System.out.println("\nAverage Salary: " + averageSalary);
    }
}
