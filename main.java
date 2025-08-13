import java.util.*;
import java.time.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        ProjectManager system = new ProjectManager();
        
        // Load saved data if available
        DataManager dataManager = new DataManager();
        ProjectManager loadedSystem = dataManager.loadData();
        if (loadedSystem != null) {
            system = loadedSystem;
        }
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("Welcome to Project Management System");
        
        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. View All Projects");
            System.out.println("2. Create New Project");
            System.out.println("3. Search Projects/Tasks");
            System.out.println("4. Filter Projects");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    viewProjects(system, scanner);
                    break;
                case 2:
                    createProject(system, scanner);
                    break;
                case 3:
                    searchProjectsAndTasks(system, scanner);
                    break;
                case 4:
                    filterProjects(system, scanner);
                    break;
                case 5:
                    running = false;
                    dataManager.saveData(system);
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
    
    // Other methods for handling menu options
    private static void viewProjects(ProjectManager system, Scanner scanner) {
        // Implementation for viewing projects
    }
    
    private static void createProject(ProjectManager system, Scanner scanner) {
        // Implementation for creating projects
    }
    
    private static void searchProjectsAndTasks(ProjectManager system, Scanner scanner) {
        // Implementation for searching
    }
    
    private static void filterProjects(ProjectManager system, Scanner scanner) {
        // Implementation for filtering
    }
}
