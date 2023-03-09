package com.klasha.user;

import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDTO> saveUser(UserDTO user);
    Mono<UserDTO> getUserByEmail(String email);
}
