package hw.user;

import hw.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(String username) {
        String sql = "INSERT INTO users (username) VALUES (?) RETURNING id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    long generatedId = rs.getLong("id");
                    return new User(generatedId, username);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        throw new RecordNotFoundException();
    }

    public User getUserById(Long id) {
        String sql = "SELECT username FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    return new User(id, username);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting user by id", e);
        }

        throw new RecordNotFoundException();
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username FROM users ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting users", e);
        }

        return users;
    }

    public User update(Long id, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newUsername);
            statement.setLong(2, id);
            statement.executeUpdate();
            return new User(id, newUsername);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM users";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
