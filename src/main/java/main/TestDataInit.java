package main;

import main.entity.User;
import main.repository.ClientRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.save(new User("bublik", passwordEncoder.encode("1234"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("user", passwordEncoder.encode("pass"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), Collections.singletonList("ROLE_ADMIN")));
        //clientsRepository.save(new Clients("Oleg", "Petrov", "Ivanovich", "1234", "567890"));
        //clientsRepository.save(new Clients("Nikita", "Artemchuk", "Artemovich", "7113", "123456"));

    }
}
