package dev.authkit.core.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthUserTest {

    @Test
    void shouldCreateValidUser() {
        AuthUser user = new AuthUser(
                "42",
                "kamil@example.com",
                "hashed-password",
                Set.of("ROLE_USER"),
                true
        );

        assertEquals("42", user.userId());
        assertEquals("kamil@example.com", user.loginIdentifier());
        assertTrue(user.enabled());
    }

    @Test
    void shouldRejectBlankUserId() {
        assertThrows(IllegalArgumentException.class, () ->
                new AuthUser(
                        "",
                        "kamil@example.com",
                        "hashed-password",
                        Set.of("ROLE_USER"),
                        true
                )
        );
    }

    @Test
    void shouldRejectNullAuthorities() {
        assertThrows(NullPointerException.class, () ->
                new AuthUser(
                        "42",
                        "kamil@example.com",
                        "hashed-password",
                        null,
                        true
                )
        );
    }

    @Test
    void shouldAcceptEmptyAuthorities() {
        AuthUser user = new AuthUser(
                "42",
                "kamil@example.com",
                "hashed-password",
                Set.of(),
                true
        );

        assertTrue(user.authorities().isEmpty());
    }

    @Test
    void shouldProtectAuthoritiesFromModification() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        AuthUser user = new AuthUser(
                "42",
                "kamil@example.com",
                "hashed-password",
                roles,
                true
        );

        roles.add("ROLE_ADMIN");

        assertEquals(Set.of("ROLE_USER"), user.authorities());

        assertThrows(UnsupportedOperationException.class, () ->
                user.authorities().add("ROLE_ADMIN")
        );
    }

    @Test
    void shouldRejectBlankLoginIdentifier() {
        assertThrows(IllegalArgumentException.class, () -> 
                new AuthUser(
                        "42",
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
                new AuthUser(
                        "42",
                        "kamil@example.com",
                        "",
                        Set.of("ROLE_USER"),
                        true
                )
        );
    }

    @Test
    void shouldRejectNullAuthorityElement() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add(null);

        assertThrows(NullPointerException.class, () ->
                new AuthUser(
                        "42",
                        "kamil@example.com",
                        "hashed-password",
                        roles,
                        true
                )
        );
    }

    @Test
    void shouldNotExposeSensitiveDataInToString() {
        AuthUser user = new AuthUser(
                "42",
                "kamil@example.com",
                "hashed-password",
                Set.of("ROLE_USER"),
                true
        );

        String result = user.toString();

        assertFalse(result.contains("hashed-password"));
        assertFalse(result.contains("kamil@example.com"));
        assertFalse(result.contains("ROLE_USER"));
    }
}
