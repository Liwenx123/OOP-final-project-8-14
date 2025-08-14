import java.time.*;
import java.util.*;

public class Project {
    private static int COUNT = 0;
    
    private final int id;
    private String name;
    private String description;
    private LocalDateTime dueAt;
    private final LocalDateTime createdAt;
    private final List<Task> tasks = new ArrayList<>();
    private final Set<String> tags = new LinkedHashSet<>();
    
    public Project(String name, String description, LocalDateTime dueAt, Set<String> tags) {
        this.id = ++COUNT;
        this.name = name;
        this.description = description;
        this.dueAt = dueAt;
        this.createdAt = LocalDateTime.now();
        if (tags != null) this.tags.addAll(normalize(tags));
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getDueAt() { return dueAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Task> getTasks() { return Collections.unmodifiableList(tasks); }
    public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
    
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    public void setTags(Set<String> newTags) {
        this.tags.clear();
        if (newTags != null) this.tags.addAll(normalize(newTags));
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }
    
    public boolean removeTask(int taskId) {
        return tasks.removeIf(task -> task.getId() == taskId);
    }
    
    public double getProgressPercentage() {
        if (tasks.isEmpty()) return 0.0;
        
        long completedTasks = tasks.stream()
                .filter(task -> task.getStatus() == Task.Status.COMPLETED)
                .count();
        
        return (double) completedTasks / tasks.size() * 100.0;
    }
    
    public boolean isOverdue() {
        return dueAt != null && dueAt.isBefore(LocalDateTime.now());
    }
    
    public boolean isDueWithinHours(long hours) {
    if (dueAt == null) {
        return false;
    }
    
    LocalDateTime now = LocalDateTime.now();
    return !dueAt.isBefore(now) && Duration.between(now, dueAt).toHours() <= hours;
}
    
    private static Set<String> normalize(Set<String> in) {
        Set<String> out = new LinkedHashSet<>();
        for (String t : in) if (t != null) out.add(t.trim().toLowerCase());
        return out;
    }
}
