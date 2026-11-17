package dev.authkit.core.model;

import java.util.Objects;
import java.util.Set;

public record NewAuthUser(
  String loginIdentifier,
  String passwordHash,
  Set<String> authorities,
  boolean enabled
){
  public NewAuthUser {
    if (loginIdentifier == null || loginIdentifier.isBlank()) {
      throw new IllegalArgumentException("Login identifier must not be blank");
    }

    if (passwordHash == null || passwordHash.isBlank()) {
      throw new IllegalArgumentException("Password hash must not be blank");
    }

    authorities = Set.copyOf(
      Objects.requireNonNull(
        authorities,
        "Authorities must not be null"
      )
    );
  }

  @Override
  public String toString() {
    return "NewAuthUser{enabled" + enabled + "}";
  }
}
