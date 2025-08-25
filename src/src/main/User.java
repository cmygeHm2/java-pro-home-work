package main;

public class User {
    private final Long id;
    private final String username;

    public Long getId() {
        return id;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
