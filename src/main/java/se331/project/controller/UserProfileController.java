package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.project.entity.UserProfile;
import se331.project.service.UserProfileService;
import se331.project.util.AMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
    final UserProfileService userProfileService;

    @GetMapping("/profiles")
    public ResponseEntity<?> getUsers(){
        List<UserProfile> userProfiles = userProfileService.findAllUserProfiles();
        //maybe add pagination later
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(userProfiles.size()));
        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfiles), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        UserProfile userProfile = userProfileService.findById(id).orElse(null);

        if(userProfile == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfile), HttpStatus.OK);
    }

    // WILL EDIT IN LATER COMMITS
//    @PutMapping("/profiles/{id}/role")
//    public ResponseEntity<?> updateUserRole(@PathVariable Long id,
//                                            @RequestBody List<Role> newRole){
//        UserProfile userProfile = userProfileService.findById(id).orElse(null);
//
//        if(userProfile == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
//        }
//        userProfile.setRoles(newRole);
//        UserProfile updatedUserProfile = userProfileService.save(userProfile);
//        return new ResponseEntity<>(AMapper.INSTANCE.getUserDto(updatedUserProfile), HttpStatus.OK);
//    }
}
