import java.time.*;
import java.util.*;

public class Task {
    public enum Status { NOT_STARTED, IN_PROGRESS, COMPLETED }

    private static int COUNT = 0;

    private final int id;
    private String name;
    private String notes;
    private LocalDateTime dueAt;      // nullable
    private Status status;
    private final LocalDateTime createdAt;
    private final Set<String> tags = new LinkedHashSet<>();

    public Task(String name, String notes, LocalDateTime dueAt, Set<String> tags) {
        this.id = ++COUNT;
        this.name = name;
        this.notes = notes;
        this.dueAt = dueAt;
        this.status = Status.NOT_STARTED;
        this.createdAt = LocalDateTime.now();
        if (tags != null) this.tags.addAll(normalize(tags));
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getNotes() { return notes; }
    public LocalDateTime getDueAt() { return dueAt; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<String> getTags() { return Collections.unmodifiableSet(tags); }

    public void setName(String name) { this.name = name; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    public void setStatus(Status status) { this.status = status; }
    public void setTags(Set<String> newTags) {
        this.tags.clear();
        if (newTags != null) this.tags.addAll(normalize(newTags));
    }

    public boolean isOverdue() {
        return dueAt != null && status != Status.COMPLETED && dueAt.isBefore(LocalDateTime.now());
    }

    public boolean isDueWithinHours(long hours) {
        if (dueAt == null || status == Status.COMPLETED) return false;
        LocalDateTime now = LocalDateTime.now();
        return !dueAt.isBefore(now) && Duration.between(now, dueAt).toHours() <= hours;
    }

    private static Set<String> normalize(Set<String> in) {
        Set<String> out = new LinkedHashSet<>();
        for (String t : in) if (t != null) out.add(t.trim().toLowerCase());
        return out;
    }
