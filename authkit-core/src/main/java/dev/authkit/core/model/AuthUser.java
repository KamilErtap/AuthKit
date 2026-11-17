package dev.authkit.core.model;

import java.util.Objects;
import java.util.Set;

// Kullanıcı verilerini taşıyan, alanları sonradan yeniden atanamayan bir model oluşturduk.
public record AuthUser(
  String userId,
  String loginIdentifier,
  String passwordHash,
  Set<String> authorities, // Yetkileri tekrarsız saklıyoruz.
  boolean enabled
) {
    public AuthUser {
      // Eksik kullanıcı kimliği veya parola hash'i gibi geçersiz durumları reddediyoruz.
      if (userId == null || userId.isBlank()) {
        throw new IllegalArgumentException("User ID is required");
      }

      if (loginIdentifier == null || loginIdentifier.isBlank()) {
        throw new IllegalArgumentException("Login identifier is required");
      }

      if (passwordHash == null || passwordHash.isBlank()) {
        throw new IllegalArgumentException("Password hash is required");
      }

      // Dışarıdan değiştirilebilir bir yetki kümesinin modele doğrudan taşınmasını engelliyoruz.
      authorities = Set.copyOf( 
        Objects.requireNonNull(
          authorities, 
          "Authorities must not be null") 
      ); 
    }

    @Override 
    public String toString() {
      return "AuthUser{" +
              "userId='" + userId + '\'' +
              ", enabled=" + enabled +
              '}';
    }
}