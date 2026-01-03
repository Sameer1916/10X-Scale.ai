import java.util.Comparator;

public class UserManagementSystem {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              COMPREHENSIVE USER MANAGEMENT SYSTEM - DEMONSTRATION                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝\n");

        UserService userService = new UserService();

        try {
            // 1. CREATE AND ADD USERS
            System.out.println("1. CREATING AND ADDING USERS:");
            System.out.println("-".repeat(100));

            User user1 = new User("Shaik Sameer", "Sameer.Shaik@gmail.com", Role.ADMIN);
            userService.addUser(user1);

            User user2 = new User("Shaik Sharukh", "Sharukh.Shaik@gmail.com", Role.MANAGER);
            userService.addUser(user2);

            User user3 = new User("Shaik Salman", "Salman.Shaik@egmail.com", Role.USER);
            userService.addUser(user3);

            User user4 = new User("Shaik Suhail", "Suhail.Shaik@gmail.com", Role.USER);
            userService.addUser(user4);

            User user5 = new User("Shaik Sunny",  "Sunny.Shaik@gmail.com", Role.GUEST);
            userService.addUser(user5);

            // 2. DISPLAY ALL USERS
            userService.displayAllUsers();

            // 3. TEST DUPLICATE EMAIL EXCEPTION
            System.out.println("\n2. TESTING DUPLICATE EMAIL:");
            System.out.println("-".repeat(100));
            try {
                User duplicate = new User("Sameer Copy", "Sameer.Shaik@gmail.com", Role.USER);
                userService.addUser(duplicate);
            } catch (DuplicateUserException e) {
                System.out.println("✗ Expected error caught: " + e.getMessage());
            }

            // 4. TEST INVALID DATA EXCEPTION
            System.out.println("\n3. TESTING INVALID DATA:");
            System.out.println("-".repeat(100));
            try {
                User invalid = new User("", "test@example.com", Role.USER);
            } catch (InvalidUserDataException e) {
                System.out.println("✗ Expected error caught: " + e.getMessage());
            }

            try {
                User invalidEmail = new User("Test User", "invalid-email", Role.USER);
            } catch (InvalidUserDataException e) {
                System.out.println("✗ Expected error caught: " + e.getMessage());
            }

            // 5. SIMULATE LOGINS
            System.out.println("\n4. SIMULATING USER LOGINS:");
            System.out.println("-".repeat(100));
            userService.recordLogin("Sameer.Shaik@gmail.com");
            userService.recordLogin("Sameer.Shaik@gmail.com");
            userService.recordLogin("Sameer.Shaik@gmail.com");
            userService.recordLogin("Sharukh.Shaik@gmail.com");
            userService.recordLogin("Sharukh.Shaik@gmail.com");
            userService.recordLogin("Salman.Shaik@egmail.com");
            System.out.println("✓ Logins recorded for multiple users");

            // 6. FIND USER BY ID
            System.out.println("\n5. FINDING USER BY ID:");
            System.out.println("-".repeat(100));
            User found = userService.findUserById(2);
            System.out.println("Found user with ID 2: " + found);

            // 7. FIND USER BY EMAIL
            System.out.println("\n6. FINDING USER BY EMAIL:");
            System.out.println("-".repeat(100));
            User foundByEmail = userService.findUserByEmail("Sameer.Shaik@gmail.com");
            System.out.println("Found user: " + foundByEmail);

            // 8. GET USERS BY ROLE
            System.out.println("\n7. FILTERING USERS BY ROLE:");
            System.out.println("-".repeat(100));
            for (Role role : Role.values()) {
                System.out.println(role + " users (" + userService.getUsersByRole(role).size() + "):");
                for (User user : userService.getUsersByRole(role)) {
                    System.out.println("  - " + user);
                }
            }

            // 9. SORTING DEMONSTRATIONS
            System.out.println("\n8. SORTING DEMONSTRATIONS:");
            System.out.println("-".repeat(100));
            System.out.println("Sorted by Name (Natural Order):");
            userService.getUsersSorted(Comparator.naturalOrder())
                       .forEach(u -> System.out.println("  - " + u.getName()));

            System.out.println("\nSorted by Login Count (Descending):");
            userService.getUsersSorted(UserComparators.BY_LOGIN_COUNT)
                       .forEach(u -> System.out.println("  - " + u.getName() + " (" + u.getLoginCount() + " logins)"));

            System.out.println("\nSorted by Role Priority:");
            userService.getUsersSorted(UserComparators.BY_ROLE)
                       .forEach(u -> System.out.println("  - " + u.getName() + " (" + u.getRole() + ")"));

            // 10. TOP USERS BY LOGIN COUNT
            System.out.println("\n9. TOP 3 USERS BY LOGIN COUNT:");
            System.out.println("-".repeat(100));
            int rank = 1;
            for (User user : userService.getTopUsersByLoginCount(3)) {
                System.out.println(rank++ + ". " + user.getName() + " - " + user.getLoginCount() + " logins");
            }

            // 11. UPDATE USER
            System.out.println("\n10. UPDATING USER:");
            System.out.println("-".repeat(100));
            userService.updateUser(2, "Shaik Sharukh Jr.", "Sharukh.Shaik@gmail.com", Role.ADMIN);
            System.out.println("Updated user: " + userService.findUserById(2));

            // 12. DEACTIVATE USER
            System.out.println("\n11. DEACTIVATING USER:");
            System.out.println("-".repeat(100));
            userService.setUserActive(5, false);

            // 13. ACTIVE USERS
            System.out.println("\n12. ACTIVE USERS:");
            System.out.println("-".repeat(100));
            System.out.println("Active users (" + userService.getActiveUsers().size() + "):");
            userService.getActiveUsers().forEach(u -> System.out.println("  - " + u));

            // 14. BULK DEACTIVATION
            System.out.println("\n13. BULK DEACTIVATION (Users with < 2 logins):");
            System.out.println("-".repeat(100));
            userService.deactivateInactiveUsers(2);

            // 15. LOGIN STATISTICS
            System.out.println("\n14. LOGIN STATISTICS:");
            System.out.println("-".repeat(100));
            userService.getLoginStatistics()
                       .forEach((email, count) -> System.out.println("  " + email + ": " + count + " logins"));

            // 16. SYSTEM STATISTICS
            userService.displayStatistics();

            // 17. FINAL STATE
            System.out.println("\n15. FINAL USER STATE:");
            userService.displayAllUsers();

            // 18. DEMONSTRATE equals() AND hashCode()
            System.out.println("\n16. TESTING equals() AND hashCode():");
            System.out.println("-".repeat(100));
            User testUser1 = new User("Test", "test@example.com", Role.USER);
            User testUser2 = new User("Test Different Name", "test@example.com", Role.ADMIN);
            System.out.println("testUser1.equals(testUser2): " + testUser1.equals(testUser2));
            System.out.println("testUser1.hashCode() == testUser2.hashCode(): " +
                               (testUser1.hashCode() == testUser2.hashCode()));
            System.out.println("(Both have same email, so they are considered equal)");

            // 19. DEMONSTRATE IMMUTABILITY PROTECTION
            System.out.println("\n17. TESTING IMMUTABILITY PROTECTION:");
            System.out.println("-".repeat(100));
            try {
                userService.getAllUsers().add(new User("Hacker", "hacker@example.com", Role.GUEST));
            } catch (UnsupportedOperationException e) {
                System.out.println("✓ Collection is protected from external modification");
            }

        } catch (Exception e) {
            System.err.println("\n✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           DEMONSTRATION COMPLETED                                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
