package dev.authkit.core.registration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import dev.authkit.core.exception.PasswordPolicyViolationException;

class DefaultPasswordPolicyTest {

  private final PasswordPolicy policy = new DefaultPasswordPolicy();

  @Test 
  void shouldAcceptValidPassword() {
    assertDoesNotThrow(() -> 
      policy.validate("correct horse battery staple")
    );
  }

  @Test 
  void shouldRejectShortPassword() {
    assertThrows(PasswordPolicyViolationException.class, () ->
      policy.validate("123456")
    );
  }

  @Test
  void shouldRejectNullPassword() {
    assertThrows(PasswordPolicyViolationException.class, () ->
      policy.validate(null) 
    );
  }

  @Test
  void shouldAcceptExactlyFifteenCharacters() {
    assertDoesNotThrow(() ->
      policy.validate("a".repeat(15))
    );
  }

  @Test 
  void shouldAcceptExactly128Characters() {
    assertDoesNotThrow(() ->
      policy.validate("a".repeat(128))
    );
  }

  @Test
  void shouldRejectMoreThan128Characters() {
    assertThrows(PasswordPolicyViolationException.class, () ->
      policy.validate("a".repeat(129))
    );
  }
}