package hw.service;

import hw.entity.User;
import hw.exception.RecordNotFoundException;
import hw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(String username) {
        User user = new User();
        user.setUsername(username);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(Long id, String newUserName) {
        User user = userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        user.setUsername(newUserName);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        userRepository.delete(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();;
    }
}
