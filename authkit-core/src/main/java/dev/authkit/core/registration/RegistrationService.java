
package dev.authkit.core.registration;

import dev.authkit.core.model.AuthUser;
import dev.authkit.core.model.NewAuthUser;
import dev.authkit.core.port.AuthUserRepository;
import dev.authkit.core.port.PasswordHasher;

import java.util.Objects;

public final class RegistrationService {
  
  private final AuthUserRepository userRepository;
  private final PasswordHasher passwordHasher;
  private final PasswordPolicy passwordPolicy;
  private final RegistrationPolicy registrationPolicy;

  public RegistrationService(
    AuthUserRepository userRepository,
    PasswordHasher passwordHasher,
    PasswordPolicy passwordPolicy,
    RegistrationPolicy registrationPolicy
  ){
    this.userRepository = Objects.requireNonNull(userRepository);
    this.passwordHasher = Objects.requireNonNull(passwordHasher);
    this.passwordPolicy = Objects.requireNonNull(passwordPolicy);
    this.registrationPolicy = Objects.requireNonNull(registrationPolicy);
  }
  
  public AuthUser register(RegistrationCommand command) {
    Objects.requireNonNull(command, "Registration command is required");

    passwordPolicy.validate(command.rawPassword());

    String passwordHash = passwordHasher.hash(command.rawPassword());

    NewAuthUser newUser = new NewAuthUser(
      command.loginIdentifier(),
      passwordHash,
      registrationPolicy.initialAuthorities(),
      registrationPolicy.initiallyEnabled()
    );

    return userRepository.save(newUser);
  }
}