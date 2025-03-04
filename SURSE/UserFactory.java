package org.example;

public class UserFactory {
    public static <T extends Comparable<T>> User<T> createUser(AccountType accountType, String name, String userExperience, String username, String email, String password) {
        return switch (accountType) {
            case REGULAR -> new Regular<>(name, accountType, userExperience, username, email, password);
            case CONTRIBUTOR -> new Contributor<>(name, accountType, userExperience, username, email, password);
            case ADMIN -> new Admin<>(name, accountType, userExperience, username, email, password);
            default -> throw new IllegalArgumentException("Tip de cont nevalid: " + accountType);
        };
    }
}
