public enum Role {
    ADMIN("Administrator", 4),
    MANAGER("Manager", 3),
    USER("Regular User", 2),
    GUEST("Guest User", 1);

    private final String description;
    private final int priority;

    Role(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
