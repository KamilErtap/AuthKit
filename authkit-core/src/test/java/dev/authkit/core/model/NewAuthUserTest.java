package dev.authkit.core.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NewAuthUserTest {
  
  @Test
  void shouldCreateValidNewUser() {
    NewAuthUser user = new NewAuthUser(
      "kamil@example.com",
      "hashed-password",
      Set.of("ROLE_USER"),
      true
    );

    assertEquals("kamil@example.com", user.loginIdentifier());
    assertTrue(user.enabled());
  }

  @Test
  void shouldRejectBlankLoginIdentifier() {
    assertThrows(IllegalArgumentException.class, () ->
      new NewAuthUser(
        " ",
        "hashed-password",
        Set.of("ROLE_USER"),
        true
      )
    );
  }

  @Test 
  void shouldRejectBlankPasswordHash() {
    assertThrows(IllegalArgumentException.class, () ->
      new NewAuthUser(
        "kamil@example.com",
        "",
        Set.of("ROLE_USER"),
        true
      )
    );
  }

  @Test 
  void shouldRejectNullAuthorities() {
    assertThrows(NullPointerException.class, () ->
      new NewAuthUser(
        "kamil@example.com",
        "hashed-password",
        null,
        true
      )
    );
  }

  @Test 
  void shouldDefensivelyCopyAuthorities() {
    Set<String> roles = new HashSet<>();
    roles.add("ROLE_USER");

    NewAuthUser user = new NewAuthUser(
      "kamil@example.com",
      "hashed-password",
      roles,
      true
    );

    roles.add("ROLE_ADMIN");

    assertEquals(Set.of("ROLE_USER"), user.authorities());
  }

  @Test 
  void shouldNotExposeSensitiveDataInToString() {
    NewAuthUser user = new NewAuthUser(
      "kamil@example.com",
      "hashed-password",
      Set.of("ROLE_ADMIN"),
      true
    );

    String output = user.toString();

    assertFalse(output.contains("hashed-password"));
    assertFalse(output.contains("kamil@example.com"));
    assertFalse(output.contains("ROLE_ADMIN"));
  }
}