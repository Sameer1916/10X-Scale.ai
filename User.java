import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User implements Comparable<User> {
    private static int idCounter = 1;

    private final int id;
    private String name;
    private String email;
    private Role role;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private final List<LocalDateTime> loginHistory;

    public User(String name, String email, Role role) throws InvalidUserDataException {
        validateName(name);
        validateEmail(email);

        this.id = idCounter++;
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
        this.loginHistory = new ArrayList<>();
    }

    private void validateName(String name) throws InvalidUserDataException {
        if (name == null || name.trim().isEmpty()) throw new InvalidUserDataException("Name cannot be null or empty");
        if (name.length() < 2) throw new InvalidUserDataException("Name must be at least 2 characters long");
    }

    private void validateEmail(String email) throws InvalidUserDataException {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new InvalidUserDataException("Invalid email format");
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastModified() { return lastModified; }
    public List<LocalDateTime> getLoginHistory() { return Collections.unmodifiableList(loginHistory); }
    public int getLoginCount() { return loginHistory.size(); }

    public void setName(String name) throws InvalidUserDataException { validateName(name); this.name = name; this.lastModified = LocalDateTime.now(); }
    public void setEmail(String email) throws InvalidUserDataException { validateEmail(email); this.email = email; this.lastModified = LocalDateTime.now(); }
    public void setRole(Role role) { this.role = role; this.lastModified = LocalDateTime.now(); }
    public void setActive(boolean active) { this.active = active; this.lastModified = LocalDateTime.now(); }

    public void recordLogin() { loginHistory.add(LocalDateTime.now()); this.lastModified = LocalDateTime.now(); }
    public LocalDateTime getLastLogin() { return loginHistory.isEmpty() ? null : loginHistory.get(loginHistory.size() - 1); }

    public static int getIdCounter() { return idCounter; }
    public static void resetIdCounter() { idCounter = 1; }

    @Override
    public int compareTo(User other) { return this.name.compareToIgnoreCase(other.name); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equalsIgnoreCase(user.email);
    }

    @Override
    public int hashCode() { return email.toLowerCase().hashCode(); }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String lastLogin = getLastLogin() != null ? getLastLogin().format(formatter) : "Never";
        return String.format("User[id=%d, name='%s', email='%s', role=%s, active=%s, logins=%d, lastLogin=%s]",
                id, name, email, role, active, loginHistory.size(), lastLogin);
    }
}
