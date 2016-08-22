package next.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import core.test.IntegrationTest;
import next.model.User;

public class UserRepositoryTest extends IntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void crud() {
        User user = new User("userId", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
    }

}
