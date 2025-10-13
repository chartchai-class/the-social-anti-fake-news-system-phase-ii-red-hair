package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se331.project.dto.UserDto;
import se331.project.entity.User;
import se331.project.service.UserService;
import se331.project.util.AMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        List<User> users = userService.findAllUsers();
        //maybe add pagination later
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(users.size()));

        return new ResponseEntity<>(users, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        User user = userService.findById(id).orElse(null);

        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new ResponseEntity<>(AMapper.INSTANCE.getUserDto(user), HttpStatus.OK);
    }
}
