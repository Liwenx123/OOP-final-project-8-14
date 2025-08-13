import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectManager {
    private final List<Project> projects = new ArrayList<>();
    
    public void addProject(Project project) {
        projects.add(project);
    }
    
    public boolean removeProject(Project project) {
        return projects.remove(project);
    }
    
    public boolean removeProject(int projectId) {
        return projects.removeIf(project -> project.getId() == projectId);
    }
    
    public List<Project> getAllProjects() {
        return Collections.unmodifiableList(projects);
    }
    
    public Project getProjectById(int id) {
        return projects.stream()
                .filter(project -> project.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    // Search functionality
    public List<Project> searchProjects(String query) {
        String lowercaseQuery = query.toLowerCase();
        return projects.stream()
                .filter(project -> 
                    project.getName().toLowerCase().contains(lowercaseQuery) || 
                    project.getDescription().toLowerCase().contains(lowercaseQuery) ||
                    project.getTags().stream().anyMatch(tag -> tag.contains(lowercaseQuery)))
                .collect(Collectors.toList());
    }
    
    public List<Task> searchTasks(String query) {
        String lowercaseQuery = query.toLowerCase();
        return projects.stream()
                .flatMap(project -> project.getTasks().stream())
                .filter(task -> 
                    task.getName().toLowerCase().contains(lowercaseQuery) || 
                    task.getNotes().toLowerCase().contains(lowercaseQuery) ||
                    task.getTags().stream().anyMatch(tag -> tag.contains(lowercaseQuery)))
                .collect(Collectors.toList());
    }
    
    // Filtering options
    public List<Project> filterProjects(Predicate<Project> filter) {
        return projects.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
    
    // Sorting options
    public List<Project> sortProjectsByName(boolean ascending) {
        return projects.stream()
                .sorted((p1, p2) -> ascending ? 
                        p1.getName().compareToIgnoreCase(p2.getName()) : 
                        p2.getName().compareToIgnoreCase(p1.getName()))
                .collect(Collectors.toList());
    }
    
    public List<Project> sortProjectsByDueDate(boolean ascending) {
        return projects.stream()
                .sorted((p1, p2) -> {
                    if (p1.getDueAt() == null && p2.getDueAt() == null) return 0;
                    if (p1.getDueAt() == null) return ascending ? 1 : -1;
                    if (p2.getDueAt() == null) return ascending ? -1 : 1;
                    return ascending ? 
                            p1.getDueAt().compareTo(p2.getDueAt()) : 
                            p2.getDueAt().compareTo(p1.getDueAt());
                })
                .collect(Collectors.toList());
    }
    
    public List<Project> sortProjectsByCreationDate(boolean ascending) {
        return projects.stream()
                .sorted((p1, p2) -> ascending ? 
                        p1.getCreatedAt().compareTo(p2.getCreatedAt()) : 
                        p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    // Urgency indicators
    public List<Project> getUrgentProjects(long hoursThreshold) {
        return projects.stream()
                .filter(project -> project.isDueWithinHours(hoursThreshold))
                .collect(Collectors.toList());
    }
    
    public List<Task> getUrgentTasks(long hoursThreshold) {
        return projects.stream()
                .flatMap(project -> project.getTasks().stream())
                .filter(task -> task.isDueWithinHours(hoursThreshold))
                .collect(Collectors.toList());
    }
    
    // Tag-based filtering
    public List<Project> getProjectsByTag(String tag) {
        return projects.stream()
                .filter(project -> project.getTags().contains(tag.toLowerCase().trim()))
                .collect(Collectors.toList());
    }
}
