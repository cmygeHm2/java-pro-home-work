package main;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(String username) {
        return userDao.create(username);
    }

    public User getById(Long id) {
        return userDao.getUserById(id);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User update(Long id, String newUserName) {
        return userDao.update(id, newUserName);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }
}
