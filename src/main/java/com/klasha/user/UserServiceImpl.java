package com.klasha.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    List<UserDTO> users = new ArrayList<UserDTO>();

    @Override
    public Mono<UserDTO> saveUser(UserDTO user) {
        users.add(user);
        return Mono.just(user);
    }

    @Override
    public Mono<UserDTO> getUserByEmail(String email) {
        Optional<UserDTO> existingUser = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (existingUser.isPresent()) {
            return Mono.just(existingUser.get());
        }
        return Mono.just(null);
    }
}
