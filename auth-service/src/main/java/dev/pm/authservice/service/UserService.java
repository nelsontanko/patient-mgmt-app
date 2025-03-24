package dev.pm.authservice.service;

import dev.pm.authservice.entity.User;
import dev.pm.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Nelson Tanko
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
