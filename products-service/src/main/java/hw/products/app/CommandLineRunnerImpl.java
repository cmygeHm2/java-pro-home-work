package hw.products.app;

import hw.products.entity.User;
import hw.products.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        User alex = userService.create("Александр");
        userService.getAll().forEach(u -> log.info(u.toString()));

        User updatedUser = userService.update(alex.getId(), "Не Александр");
        log.info(userService.getById(updatedUser.getId()).toString());

        userService.delete(alex.getId());
        userService.getAll().forEach(u -> log.info(u.toString()));

        try {
            userService.getById(alex.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}


