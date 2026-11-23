package dev.authkit.core.registration;

import dev.authkit.core.exception.PasswordPolicyViolationException;
import dev.authkit.core.port.AuthUserRepository;
import dev.authkit.core.port.PasswordHasher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {
  
  @Test
  void shouldNotSaveUserWhenPasswordPolicyFails() {
    
    AuthUserRepository repository = mock(AuthUserRepository.class);
    PasswordHasher hasher = mock(PasswordHasher.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);
    RegistrationPolicy registrationPolicy = mock(RegistrationPolicy.class);

    RegistrationService service = new RegistrationService(
      repository,
      hasher,
      passwordPolicy,
      registrationPolicy
    );

    RegistrationCommand command = new RegistrationCommand(
      "test@example.com",
      "123456"
    );

    doThrow(new PasswordPolicyViolationException("Password is too short"))
      .when(passwordPolicy)
      .validate("123456");

    assertThrows(PasswordPolicyViolationException.class, () -> service.register(command));

    verify(repository, never()).save(any());
    verify(hasher, never()).hash(anyString());
  }
}