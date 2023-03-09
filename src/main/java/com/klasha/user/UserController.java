package com.klasha.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;
    @PostMapping("/signup")
    public Mono<ResponseEntity<UserDTO>> signup(@RequestBody UserDTO dto){
        Mono<UserDTO> userDTOMono = service.saveUser(dto);
        return userDTOMono.map(user->{
            if (user != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        });
    }
    @PostMapping("/signin")
    public Mono<ResponseEntity<UserDTO>> signin(@RequestBody UserDTO dto){
        Mono<UserDTO> userDTOMono = service.getUserByEmail(dto.getEmail());
        return userDTOMono.map(user->{
            if (user.getPassword() == dto.getPassword()) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        });
    }
}
