package com.laba.solvd;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EmployeeGUI {
    private static final Logger logger = Logger.getLogger(EmployeeGUI.class.getName());
    private static final int DIALOG_WIDTH = 250;
    private static final int DIALOG_HEIGHT = 200;

    public static List<Employee> chooseFileAndReadData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Excel File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logger.log(Level.INFO, "Selected file: {0}", file.getAbsolutePath());
            return EmployeeManager.readEmployeesFromFile(file.getAbsolutePath());
        } else {
            showCustomDialog("No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, "No file was selected by the user.");
            return null;
        }
    }

    public static void showActionMenu(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            showCustomDialog("No employee data found.", "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, "No employee data available to display.");
            return;
        }

        String[] options = {"Average Salary", "Employees Above a Certain Age", "Full Employee List", "Exit"};
        boolean exit = false;

        while (!exit) {
            int choice = JOptionPane.showOptionDialog(null, "Select an action", "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0: // Average Salary
                    logger.log(Level.INFO, "User selected 'Average Salary'.");
                    double averageSalary = EmployeeManager.calculateAverageSalary(employees);
                    showCustomDialog("Average Salary: " + averageSalary, "Result", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 1: // Employees Above a Certain Age
                    logger.log(Level.INFO, "User selected 'Filter by Age'.");
                    String ageInput = JOptionPane.showInputDialog(null, "Enter minimum age (non-negative):", "Filter by Age", JOptionPane.QUESTION_MESSAGE);
                    if (ageInput != null) {
                        try {
                            int minAge = Integer.parseInt(ageInput);
                            if (minAge < 0) {
                                showCustomDialog("Age cannot be negative. Please enter a valid age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.WARNING, "User entered a negative age: {0}", minAge);
                            } else {
                                logger.log(Level.INFO, "Filtering employees older than {0} years.", minAge);
                                List<Employee> filteredEmployees = EmployeeManager.filterEmployeesByAge(employees, minAge);
                                String result = filteredEmployees.stream()
                                        .map(e -> e.getName() + " - " + e.getDepartment())
                                        .collect(Collectors.joining("\n"));
                                showCustomDialog(result.isEmpty() ? "No employees above age " + minAge : result, "Result", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            showCustomDialog("Invalid age input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            logger.log(Level.SEVERE, "Invalid age input by user.", e);
                        }
                    } else {
                        logger.log(Level.INFO, "User canceled the age input.");
                    }
                    break;

                case 2: // Full Employee List
                    logger.log(Level.INFO, "User selected 'Full Employee List'.");
                    List<String> employeeInfo = EmployeeManager.getEmployeeInfo(employees);
                    String info = String.join("\n", employeeInfo);
                    showCustomDialog(info, "Full Employee List", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 3: // Exit
                case JOptionPane.CLOSED_OPTION: // The user closes the window
                    logger.log(Level.INFO, "User exited the program.");
                    exit = true;
                    break;

                default:
                    break;
            }
        }
    }

    private static void showCustomDialog(String message, String title, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT); // Set fixed size for all dialogs
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);
    }
}
