package dev.authkit.core.registration;

public interface PasswordPolicy {
  void validate(String rawPassword);
}