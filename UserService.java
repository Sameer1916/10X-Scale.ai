import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private final Map<String, User> usersByEmail;
    private final Map<Integer, User> usersById;
    private final Map<Role, Set<User>> usersByRole;
    private int totalLoginsRecorded = 0;

    public UserService() {
        usersByEmail = new HashMap<>();
        usersById = new HashMap<>();
        usersByRole = new HashMap<>();
        for (Role role : Role.values()) usersByRole.put(role, new HashSet<>());
    }

    public void addUser(User user) throws DuplicateUserException {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (usersByEmail.containsKey(user.getEmail().toLowerCase()))
            throw new DuplicateUserException("User with email '" + user.getEmail() + "' already exists");

        usersByEmail.put(user.getEmail().toLowerCase(), user);
        usersById.put(user.getId(), user);
        usersByRole.get(user.getRole()).add(user);
        System.out.println("✓ User added successfully: " + user.getName() + " (ID: " + user.getId() + ")");
    }

    public User findUserById(int id) throws UserNotFoundException {
        User user = usersById.get(id);
        if (user == null) throw new UserNotFoundException("User with ID " + id + " not found");
        return user;
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = usersByEmail.get(email.toLowerCase());
        if (user == null) throw new UserNotFoundException("User with email '" + email + "' not found");
        return user;
    }

    public Collection<User> getAllUsers() { return Collections.unmodifiableCollection(usersByEmail.values()); }
    public Set<User> getUsersByRole(Role role) { return Collections.unmodifiableSet(usersByRole.get(role)); }
    public List<User> getActiveUsers() {
        return usersByEmail.values().stream().filter(User::isActive).toList();
    }

    public List<User> getUsersSorted(Comparator<User> comparator) {
        List<User> list = new ArrayList<>(usersByEmail.values());
        list.sort(comparator);
        return Collections.unmodifiableList(list);
    }

    public List<User> getTopUsersByLoginCount(int n) {
        return getUsersSorted(UserComparators.BY_LOGIN_COUNT).stream().limit(n).toList();
    }

    public void updateUser(int id, String name, String email, Role role)
            throws InvalidUserDataException, DuplicateUserException, UserNotFoundException {
        User user = findUserById(id);
        if (!user.getEmail().equalsIgnoreCase(email) && usersByEmail.containsKey(email.toLowerCase()))
            throw new DuplicateUserException("Email '" + email + "' is already in use");

        if (!user.getEmail().equalsIgnoreCase(email)) {
            usersByEmail.remove(user.getEmail().toLowerCase());
            usersByEmail.put(email.toLowerCase(), user);
        }

        if (user.getRole() != role) {
            usersByRole.get(user.getRole()).remove(user);
            usersByRole.get(role).add(user);
        }

        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        System.out.println("✓ User updated successfully: " + user.getName());
    }

    public void recordLogin(String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        if (!user.isActive()) throw new IllegalStateException("Cannot record login for inactive user: " + email);
        user.recordLogin();
        totalLoginsRecorded++;
    }

    public void setUserActive(int id, boolean active) throws UserNotFoundException {
        User user = findUserById(id);
        user.setActive(active);
        System.out.println("✓ User " + (active ? "activated" : "deactivated") + ": " + user.getName());
    }

    public void deleteUser(int id) throws UserNotFoundException {
        User user = findUserById(id);
        usersByEmail.remove(user.getEmail().toLowerCase());
        usersById.remove(user.getId());
        usersByRole.get(user.getRole()).remove(user);
        System.out.println("✓ User deleted: " + user.getName());
    }

    public void deactivateInactiveUsers(int minLogins) {
        int deactivated = 0;
        for (User user : usersByEmail.values()) {
            if (user.isActive() && user.getLoginCount() < minLogins) {
                user.setActive(false);
                deactivated++;
            }
        }
        System.out.println("✓ Deactivated " + deactivated + " users with fewer than " + minLogins + " logins");
    }

    public int getTotalUsers() { return usersByEmail.size(); }
    public int getActiveUsersCount() { return (int) usersByEmail.values().stream().filter(User::isActive).count(); }
    public int getTotalLoginsRecorded() { return totalLoginsRecorded; }

    public Map<Role, Integer> getUserCountByRole() {
        Map<Role, Integer> counts = new HashMap<>();
        for (Role role : Role.values()) counts.put(role, usersByRole.get(role).size());
        return Collections.unmodifiableMap(counts);
    }

    public Map<String, Integer> getLoginStatistics() {
        return usersByEmail.values().stream().collect(Collectors.toMap(User::getEmail, User::getLoginCount));
    }

    public void displayAllUsers() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("ALL USERS (" + getTotalUsers() + " total, " + getActiveUsersCount() + " active)");
        System.out.println("=".repeat(100));
        getUsersSorted(UserComparators.BY_ID).forEach(System.out::println);
        System.out.println("=".repeat(100));
    }

    public void displayStatistics() {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("SYSTEM STATISTICS");
        System.out.println("=".repeat(100));
        System.out.println("Total Users: " + getTotalUsers());
        System.out.println("Active Users: " + getActiveUsersCount());
        System.out.println("Inactive Users: " + (getTotalUsers() - getActiveUsersCount()));
        System.out.println("Total Logins Recorded: " + getTotalLoginsRecorded());
        System.out.println("\nUsers by Role:");
        getUserCountByRole().forEach((role, count) -> System.out.println("  " + role + " (" + role.getDescription() + "): " + count));
        System.out.println("=".repeat(100));
    }
}
