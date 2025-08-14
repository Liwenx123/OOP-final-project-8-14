import java.time.*;
import java.util.*;

/**
 * Project class - represents a project in the task management system
 * This is an initial implementation that will be enhanced later
 */
public class Project {
    private static int COUNT = 0;
    
    private final int id;
    private String name;
    private String description;
    private LocalDateTime dueAt;
    private final LocalDateTime createdAt;
    private final List<Task> tasks = new ArrayList<>();
    
    /**
     * Constructor for creating a new project
     */
    public Project(String name, String description) {
        this.id = ++COUNT;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
    
    // Basic getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getDueAt() { return dueAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Task> getTasks() { return Collections.unmodifiableList(tasks); }
    
    // Basic setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    
    // Task management
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }
    
    // TODO: Add methods for project progress tracking
    // TODO: Add methods for deadline management
    // TODO: Add support for project tags
}
