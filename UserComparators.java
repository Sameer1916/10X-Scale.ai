import java.util.Comparator;

public class UserComparators {
    public static final Comparator<User> BY_ROLE =
            Comparator.comparingInt((User u) -> u.getRole().getPriority()).reversed();

    public static final Comparator<User> BY_LOGIN_COUNT =
            Comparator.comparingInt(User::getLoginCount).reversed();

    public static final Comparator<User> BY_EMAIL =
            Comparator.comparing(User::getEmail, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<User> BY_ID =
            Comparator.comparingInt(User::getId);

    public static final Comparator<User> BY_CREATED_DATE =
            Comparator.comparing(User::getCreatedAt).reversed();

    public static final Comparator<User> BY_ACTIVE_ROLE_NAME =
            Comparator.comparing(User::isActive).reversed()
                      .thenComparing((User u) -> u.getRole().getPriority()).reversed()
                      .thenComparing(User::getName, String.CASE_INSENSITIVE_ORDER);
}

