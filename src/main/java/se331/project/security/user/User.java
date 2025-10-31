package se331.project.security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se331.project.entity.UserProfile;
import se331.project.security.token.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Integer id;
  private String parentId;
  @Column(unique = true)
  private String username; //for login
  // maybe I will add email later here to accept email login (or maybe not haha)
  private String password;
  private Boolean enabled;

  @Enumerated(EnumType.STRING)
  @ElementCollection
  @Builder.Default
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @OneToOne(mappedBy = "user")
  private UserProfile  userProfile;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
