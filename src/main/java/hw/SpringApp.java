package hw;

import hw.user.User;
import hw.user.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class SpringApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringApp.class);
        UserService userService = context.getBean(UserService.class);

        userService.deleteAll();

        User alex = userService.create("Александр");
        System.out.println(userService.getAll());

        User egor = userService.create("Егор");
        System.out.println(userService.getAll());

        User updatedUser = userService.update(egor.getId(), "Не Егор");
        System.out.println(userService.getById(updatedUser.getId()));

        userService.delete(alex.getId());
        System.out.println(userService.getAll());

        try {
            userService.getById(alex.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
