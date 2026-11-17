package dev.authkit.core.registration;

public record RegistrationCommand(
  String loginIdentifier,
  String rawPassword
){
  public RegistrationCommand {
    
    if (loginIdentifier == null || loginIdentifier.isBlank()) {
      throw new IllegalArgumentException("Login identifier must not be blank");
    }

    if (rawPassword == null || rawPassword.isBlank()) {
      throw new IllegalArgumentException("Password must not be blank");
    }

  }

  @Override 
  public String toString() {
    return "RegistrationCommand{redacted}";
  }
}