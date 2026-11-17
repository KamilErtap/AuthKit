package dev.authkit.core.port;

public interface PasswordHasher {

  String hash(String rawPassword);

  boolean matches(String rawPassword, String encodedPassword);
}