package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.project.dto.UserDto;
import se331.project.entity.Role;
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

        return new ResponseEntity<>(AMapper.INSTANCE.getUserDto(users), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        User user = userService.findById(id).orElse(null);

        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new ResponseEntity<>(AMapper.INSTANCE.getUserDto(user), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id,
                                            @RequestBody List<Role> newRole){
        User user = userService.findById(id).orElse(null);

        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        user.setRoles(newRole);
        User updatedUser = userService.save(user);
        return new ResponseEntity<>(AMapper.INSTANCE.getUserDto(updatedUser), HttpStatus.OK);
    }
}
