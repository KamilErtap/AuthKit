package dev.authkit.core.registration;
import dev.authkit.core.exception.PasswordPolicyViolationException;

public final class DefaultPasswordPolicy implements PasswordPolicy {

  private static final int MIN_LENGTH = 15;
  private static final int MAX_LENGTH = 128;

  @Override
  public void validate(String rawPassword) {
    
    if (rawPassword == null ) {
      throw new PasswordPolicyViolationException("Password must not be null");
    }

    int length = rawPassword.codePointCount(0, rawPassword.length());

    if (length < MIN_LENGTH) {
      throw new PasswordPolicyViolationException("Password is too short");
    }

    if (length > MAX_LENGTH) {
      throw new PasswordPolicyViolationException("Password is too long");
    }
  }
}