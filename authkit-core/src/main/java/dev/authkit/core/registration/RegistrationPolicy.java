package dev.authkit.core.registration;

import java.util.Set;

public interface RegistrationPolicy {
  
  Set<String> initialAuthorities();

  boolean initiallyEnabled();
}
