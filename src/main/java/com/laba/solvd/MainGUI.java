package com.laba.solvd;

import javax.swing.*;
import java.util.List;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Retrieve information about employees via GUI
            List<Employee> employees = EmployeeGUI.chooseFileAndReadData();

            // If the data exists, show the menu
            if (employees != null) {
                EmployeeGUI.showActionMenu(employees);
            }
        });
    }
}
