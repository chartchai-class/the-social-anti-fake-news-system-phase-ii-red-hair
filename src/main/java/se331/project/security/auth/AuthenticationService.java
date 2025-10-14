package se331.project.security.auth;



import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se331.project.entity.UserProfile;
import se331.project.repository.UserProfileRepository;
import se331.project.security.config.JwtService;
import se331.project.security.token.Token;
import se331.project.security.token.TokenRepository;
import se331.project.security.token.TokenType;
import se331.project.security.user.Role;
import se331.project.security.user.User;
import se331.project.security.user.UserRepository;
import se331.project.util.AMapper;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;

  public AuthenticationResponse register(RegisterRequest request) {
    User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .enabled(true)
            .roles(List.of(Role.ROLE_READER))
            .build();

    UserProfile userProfile = UserProfile.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .displayName(request.getUsername())
            .profileImage(request.getProfileImage())
            .phoneNumber(request.getPhoneNumber())
            .build();

    // This should come first before setUserProfile and setUser. Persistence instance can't save transient instance. (lesson learned :")
    // Or else, you will see a message like "persistent instance references an unsaved transient instance (save the transient instance before flushing)"
    userRepository.save(user);
    userProfileRepository.save(userProfile);

    user.setUserProfile(userProfile); // Don't forget to add this. seems like my entity relationship sucks.
    userProfile.setUser(user);

    // Lesson learned: I should have sleep earlier.

    AuthenticationRequest authenticationRequest = new AuthenticationRequest(); // auto login after register
    authenticationRequest.setUsername(request.getUsername());
    authenticationRequest.setPassword(request.getPassword());
    return authenticate(authenticationRequest);

  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    User user = repository.findByUsername(request.getUsername())
            .orElseThrow();

    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
//    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .user(AMapper.INSTANCE.getUserProfileAuthDto(user.getUserProfile()))
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      User user = this.repository.findByUsername(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
