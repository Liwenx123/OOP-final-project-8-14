import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskManagementSystem {
    private final ProjectManager projectManager = new ProjectManager();
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public void start() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageProjects();
                    break;
                case 2:
                    manageTasks();
                    break;
                case 3:
                    searchSystem();
                    break;
                case 4:
                    filterAndSortProjects();
                    break;
                case 5:
                    viewUrgentItems();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Thank you for using the Task Management System!");
    }
    
    private void displayMainMenu() {
        System.out.println("\n===== PROJECT AND TASK MANAGEMENT SYSTEM =====");
        System.out.println("1. Manage Projects");
        System.out.println("2. Manage Tasks");
        System.out.println("3. Search");
        System.out.println("4. Filter and Sort Projects");
        System.out.println("5. View Urgent Items");
        System.out.println("0. Exit");
    }
    
    private void manageProjects() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n===== PROJECT MANAGEMENT =====");
            System.out.println("1. Create New Project");
            System.out.println("2. View All Projects");
            System.out.println("3. View Project Details");
            System.out.println("4. Edit Project");
            System.out.println("5. Delete Project");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    viewAllProjects();
                    break;
                case 3:
                    viewProjectDetails();
                    break;
                case 4:
                    editProject();
                    break;
                case 5:
                    deleteProject();
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private void manageTasks() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n===== TASK MANAGEMENT =====");
            System.out.println("1. Add Task to Project");
            System.out.println("2. View Tasks for Project");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Update Task Status");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addTaskToProject();
                    break;
                case 2:
                    viewTasksForProject();
                    break;
                case 3:
                    editTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    updateTaskStatus();
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    // Project management methods
    private void createProject() {
        System.out.println("\n===== CREATE NEW PROJECT =====");
        String name = getStringInput("Enter project name: ");
        String description = getStringInput("Enter project description: ");
        LocalDateTime dueAt = getDateTimeInput("Enter due date (yyyy-MM-dd HH:mm) or leave blank: ");
        Set<String> tags = getTagsInput("Enter tags (comma-separated): ");
        
        Project project = new Project(name, description, dueAt, tags);
        projectManager.addProject(project);
        System.out.println("Project created successfully with ID: " + project.getId());
    }
    
    private void viewAllProjects() {
        System.out.println("\n===== ALL PROJECTS =====");
        List<Project> projects = projectManager.getAllProjects();
        
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }
        
        for (Project project : projects) {
            displayProjectSummary(project);
        }
    }
    
    private void viewProjectDetails() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        displayProjectDetails(project);
    }
    
    private void editProject() {
        int projectId = getIntInput("Enter project ID to edit: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        System.out.println("\n===== EDIT PROJECT =====");
        System.out.println("Current name: " + project.getName());
        String name = getStringInput("Enter new name (or leave blank to keep current): ");
        if (!name.isEmpty()) project.setName(name);
        
        System.out.println("Current description: " + project.getDescription());
        String description = getStringInput("Enter new description (or leave blank to keep current): ");
        if (!description.isEmpty()) project.setDescription(description);
        
        System.out.println("Current due date: " + 
                (project.getDueAt() != null ? project.getDueAt().format(dateFormatter) : "None"));
        LocalDateTime dueAt = getDateTimeInput("Enter new due date (yyyy-MM-dd HH:mm) or leave blank: ");
        if (dueAt != null) project.setDueAt(dueAt);
        
        System.out.println("Current tags: " + String.join(", ", project.getTags()));
        Set<String> tags = getTagsInput("Enter new tags (comma-separated) or leave blank: ");
        if (!tags.isEmpty()) project.setTags(tags);
        
        System.out.println("Project updated successfully.");
    }
    
    private void deleteProject() {
        int projectId = getIntInput("Enter project ID to delete: ");
        
        if (projectManager.removeProject(projectId)) {
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Project not found.");
        }
    }
    
    // Task management methods
    private void addTaskToProject() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        System.out.println("\n===== ADD TASK TO PROJECT: " + project.getName() + " =====");
        String name = getStringInput("Enter task name: ");
        String notes = getStringInput("Enter task notes: ");
        LocalDateTime dueAt = getDateTimeInput("Enter due date (yyyy-MM-dd HH:mm) or leave blank: ");
        Set<String> tags = getTagsInput("Enter tags (comma-separated): ");
        
        Task task = new Task(name, notes, dueAt, tags);
        project.addTask(task);
        System.out.println("Task added successfully with ID: " + task.getId());
    }
    
    private void viewTasksForProject() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        System.out.println("\n===== TASKS FOR PROJECT: " + project.getName() + " =====");
        List<Task> tasks = project.getTasks();
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for this project.");
            return;
        }
        
        for (Task task : tasks) {
            displayTaskSummary(task);
        }
    }
    
    private void editTask() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        int taskId = getIntInput("Enter task ID to edit: ");
        Task taskToEdit = null;
        
        for (Task task : project.getTasks()) {
            if (task.getId() == taskId) {
                taskToEdit = task;
                break;
            }
        }
        
        if (taskToEdit == null) {
            System.out.println("Task not found in this project.");
            return;
        }
        
        System.out.println("\n===== EDIT TASK =====");
        System.out.println("Current name: " + taskToEdit.getName());
        String name = getStringInput("Enter new name (or leave blank to keep current): ");
        if (!name.isEmpty()) taskToEdit.setName(name);
        
        System.out.println("Current notes: " + taskToEdit.getNotes());
        String notes = getStringInput("Enter new notes (or leave blank to keep current): ");
        if (!notes.isEmpty()) taskToEdit.setNotes(notes);
        
        System.out.println("Current due date: " + 
                (taskToEdit.getDueAt() != null ? taskToEdit.getDueAt().format(dateFormatter) : "None"));
        LocalDateTime dueAt = getDateTimeInput("Enter new due date (yyyy-MM-dd HH:mm) or leave blank: ");
        if (dueAt != null) taskToEdit.setDueAt(dueAt);
        
        System.out.println("Current tags: " + String.join(", ", taskToEdit.getTags()));
        Set<String> tags = getTagsInput("Enter new tags (comma-separated) or leave blank: ");
        if (!tags.isEmpty()) taskToEdit.setTags(tags);
        
        System.out.println("Task updated successfully.");
    }
    
    private void deleteTask() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        int taskId = getIntInput("Enter task ID to delete: ");
        
        if (project.removeTask(taskId)) {
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found in this project.");
        }
    }
    
    private void updateTaskStatus() {
        int projectId = getIntInput("Enter project ID: ");
        Project project = projectManager.getProjectById(projectId);
        
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        
        int taskId = getIntInput("Enter task ID: ");
        Task taskToUpdate = null;
        
        for (Task task : project.getTasks()) {
            if (task.getId() == taskId) {
                taskToUpdate = task;
                break;
            }
        }
        
        if (taskToUpdate == null) {
            System.out.println("Task not found in this project.");
            return;
        }
        
        System.out.println("\n===== UPDATE TASK STATUS =====");
        System.out.println("Current status: " + taskToUpdate.getStatus());
        System.out.println("1. NOT_STARTED");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");
        
        int statusChoice = getIntInput("Select new status: ");
        
        switch (statusChoice) {
            case 1:
                taskToUpdate.setStatus(Task.Status.NOT_STARTED);
                break;
            case 2:
                taskToUpdate.setStatus(Task.Status.IN_PROGRESS);
                break;
            case 3:
                taskToUpdate.setStatus(Task.Status.COMPLETED);
                break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }
        
        System.out.println("Task status updated successfully.");
    }
    
    // Search functionality
    private void searchSystem() {
        System.out.println("\n===== SEARCH =====");
        System.out.println("1. Search Projects");
        System.out.println("2. Search Tasks");
        System.out.println("0. Back");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                searchProjects();
                break;
            case 2:
                searchTasks();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private void searchProjects() {
        String query = getStringInput("Enter search query: ");
        List<Project> results = projectManager.searchProjects(query);
        
        System.out.println("\n===== SEARCH RESULTS: PROJECTS =====");
        if (results.isEmpty()) {
            System.out.println("No projects found matching your query.");
            return;
        }
        
        for (Project project : results) {
            displayProjectSummary(project);
        }
    }
    
    private void searchTasks() {
        String query = getStringInput("Enter search query: ");
        List<Task> results = projectManager.searchTasks(query);
        
        System.out.println("\n===== SEARCH RESULTS: TASKS =====");
        if (results.isEmpty()) {
            System.out.println("No tasks found matching your query.");
            return;
        }
        
        for (Task task : results) {
            displayTaskSummary(task);
        }
    }
    
    // Filter and sort functionality
    private void filterAndSortProjects() {
        System.out.println("\n===== FILTER AND SORT PROJECTS =====");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by Due Date");
        System.out.println("3. Sort by Creation Date");
        System.out.println("4. Filter by Tag");
        System.out.println("0. Back");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                sortProjectsByName();
                break;
            case 2:
                sortProjectsByDueDate();
                break;
            case 3:
                sortProjectsByCreationDate();
                break;
            case 4:
                filterProjectsByTag();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private void sortProjectsByName() {
        System.out.println("1. Ascending (A-Z)");
        System.out.println("2. Descending (Z-A)");
        int order = getIntInput("Select order: ");
        
        boolean ascending = order == 1;
        List<Project> sortedProjects = projectManager.sortProjectsByName(ascending);
        
        System.out.println("\n===== PROJECTS SORTED BY NAME =====");
        for (Project project : sortedProjects) {
            displayProjectSummary(project);
        }
    }
    
    private void sortProjectsByDueDate() {
        System.out.println("1. Earliest first");
        System.out.println("2. Latest first");
        int order = getIntInput("Select order: ");
        
        boolean ascending = order == 1;
        List<Project> sortedProjects = projectManager.sortProjectsByDueDate(ascending);
        
        System.out.println("\n===== PROJECTS SORTED BY DUE DATE =====");
        for (Project project : sortedProjects) {
            displayProjectSummary(project);
        }
    }
    
    private void sortProjectsByCreationDate() {
        System.out.println("1. Oldest first");
        System.out.println("2. Newest first");
        int order = getIntInput("Select order: ");
        
        boolean ascending = order == 1;
        List<Project> sortedProjects = projectManager.sortProjectsByCreationDate(ascending);
        
        System.out.println("\n===== PROJECTS SORTED BY CREATION DATE =====");
        for (Project project : sortedProjects) {
            displayProjectSummary(project);
        }
    }
    
    private void filterProjectsByTag() {
        String tag = getStringInput("Enter tag to filter by: ");
        List<Project> filteredProjects = projectManager.getProjectsByTag(tag);
        
        System.out.println("\n===== PROJECTS WITH TAG: " + tag + " =====");
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects found with this tag.");
            return;
        }
        
        for (Project project : filteredProjects) {
            displayProjectSummary(project);
        }
    }
    
    // Urgency indicators
    private void viewUrgentItems() {
        System.out.println("\n===== URGENT ITEMS =====");
        System.out.println("1. View Urgent Projects (due within 48 hours)");
        System.out.println("2. View Urgent Tasks (due within 48 hours)");
        System.out.println("0. Back");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                viewUrgentProjects();
                break;
            case 2:
                viewUrgentTasks();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid option.");
        }
    }
    
    private void viewUrgentProjects() {
        List<Project> urgentProjects = projectManager.getUrgentProjects(48);
        
        System.out.println("\n===== URGENT PROJECTS (DUE WITHIN 48 HOURS) =====");
        if (urgentProjects.isEmpty()) {
            System.out.println("No urgent projects found.");
            return;
        }
        
        for (Project project : urgentProjects) {
            displayProjectSummary(project);
        }
    }
    
    private void viewUrgentTasks() {
        List<Task> urgentTasks = projectManager.getUrgentTasks(48);
        
        System.out.println("\n===== URGENT TASKS (DUE WITHIN 48 HOURS) =====");
        if (urgentTasks.isEmpty()) {
            System.out.println("No urgent tasks found.");
            return;
        }
        
        for (Task task : urgentTasks) {
            displayTaskSummary(task);
        }
    }
    
    // Helper methods for displaying information
    private void displayProjectSummary(Project project) {
        System.out.println("ID: " + project.getId() + 
                " | Name: " + project.getName() + 
                " | Due: " + (project.getDueAt() != null ? project.getDueAt().format(dateFormatter) : "None") +
                " | Progress: " + String.format("%.1f%%", project.getProgressPercentage()) +
                " | Tasks: " + project.getTasks().size() +
                (project.isOverdue() ? " | OVERDUE" : "") +
                (project.isDueWithinHours(48) ? " | URGENT" : ""));
    }
    
    private void displayProjectDetails(Project project) {
        System.out.println("\n===== PROJECT DETAILS =====");
        System.out.println("ID: " + project.getId());
        System.out.println("Name: " + project.getName());
        System.out.println("Description: " + project.getDescription());
        System.out.println("Due Date: " + 
                (project.getDueAt() != null ? project.getDueAt().format(dateFormatter) : "None"));
        System.out.println("Created At: " + project.getCreatedAt().format(dateFormatter));
        System.out.println("Tags: " + String.join(", ", project.getTags()));
        System.out.println("Progress: " + String.format("%.1f%%", project.getProgressPercentage()));
        System.out.println("Status: " + 
                (project.isOverdue() ? "OVERDUE" : 
                (project.isDueWithinHours(48) ? "URGENT" : "On Track")));
        
        System.out.println("\nTasks:");
        List<Task> tasks = project.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks for this project.");
        } else {
            for (Task task : tasks) {
                displayTaskSummary(task);
            }
        }
    }
    
    private void displayTaskSummary(Task task) {
        System.out.println("ID: " + task.getId() + 
                " | Name: " + task.getName() + 
                " | Status: " + task.getStatus() +
                " | Due: " + (task.getDueAt() != null ? task.getDueAt().format(dateFormatter) : "None") +
                (task.isOverdue() ? " | OVERDUE" : "") +
                (task.isDueWithinHours(48) ? " | URGENT" : ""));
    }
    
    // Input helper methods
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private LocalDateTime getDateTimeInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            return null;
        }
        
        try {
            return LocalDateTime.parse(input, dateFormatter);
        } catch (Exception e) {
            System.out.println("Invalid date format. Using no deadline.");
            return null;
        }
    }
    
    private Set<String> getTagsInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            return new HashSet<>();
        }
        
        Set<String> tags = new HashSet<>();
        String[] tagArray = input.split(",");
        for (String tag : tagArray) {
            tags.add(tag.trim());
        }
        
        return tags;
    }
    
    public static void main(String[] args) {
        TaskManagementSystem system = new TaskManagementSystem();
        system.start();
    }
}
