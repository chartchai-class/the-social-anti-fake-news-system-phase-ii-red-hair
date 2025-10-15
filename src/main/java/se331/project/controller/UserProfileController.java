package se331.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.project.dto.UserProfileDto;
import se331.project.entity.UserProfile;
import se331.project.repository.UserProfileRepository;
import se331.project.security.user.Role;
import se331.project.security.user.User;
import se331.project.security.user.UserRepository;
import se331.project.service.UserProfileService;
import se331.project.util.AMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
    final UserProfileService userProfileService;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    // for Admin access
    //get all profiles
    @GetMapping("/users/profiles")
    public ResponseEntity<?> getUsers(){
        List<UserProfile> userProfiles = userProfileService.findAllUserProfiles();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(userProfiles.size()));
        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfiles), responseHeaders, HttpStatus.OK);
    }

    //get single profile by id
    @GetMapping("/users/profiles/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id){
        User user = userRepository.findById(Math.toIntExact(id)).orElse(null);
        if(user == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        UserProfile userProfile=user.getUserProfile();

        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfile), HttpStatus.OK);
    }

    // (currently) update roles only
    @PutMapping("/users/profiles/{id}")
        public ResponseEntity<?> updateUserRole(@PathVariable Long id,
                                            @RequestBody List<Role> newRole){
        User user = userRepository.findById(Math.toIntExact(id)).orElse(null);
        if(user == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        UserProfile userProfile=user.getUserProfile();

        user.setRoles(newRole);
        userProfileRepository.save(userProfile);
        userRepository.save(user);

        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfile), HttpStatus.OK);
    }


    // For General access
    // get own profile
    @GetMapping("/profiles/me")
    public ResponseEntity<?> getUser(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        UserProfile userProfile =  user.getUserProfile();
        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfile), HttpStatus.OK);
    }

    // edit own profile
    @PutMapping("/profiles/me")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserProfile updatedUserProfile){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        UserProfile userProfile =  user.getUserProfile();
        userProfile.setFirstName(updatedUserProfile.getFirstName() == null ? userProfile.getFirstName() : updatedUserProfile.getFirstName());
        userProfile.setLastName(updatedUserProfile.getLastName()  == null ? userProfile.getLastName() : updatedUserProfile.getLastName());
        userProfile.setDisplayName(updatedUserProfile.getDisplayName()  == null ? userProfile.getDisplayName() : updatedUserProfile.getDisplayName());
        userProfile.setEmail(updatedUserProfile.getEmail()  == null ? userProfile.getEmail() : updatedUserProfile.getEmail());
        userProfile.setProfileImage(updatedUserProfile.getProfileImage() == null ? userProfile.getProfileImage() : updatedUserProfile.getProfileImage());
        userProfile.setPhoneNumber(updatedUserProfile.getPhoneNumber() == null ? userProfile.getPhoneNumber() : updatedUserProfile.getPhoneNumber());
        userProfileRepository.save(userProfile);
        userRepository.save(user);

        return new ResponseEntity<>(AMapper.INSTANCE.getUserProfileDto(userProfile), HttpStatus.OK);
    }


}
