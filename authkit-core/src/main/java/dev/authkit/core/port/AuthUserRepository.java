package dev.authkit.core.port;

import dev.authkit.core.model.AuthUser;
import dev.authkit.core.model.NewAuthUser;

import java.util.Optional;

public interface AuthUserRepository {

    Optional<AuthUser> findByLoginIdentifier(
      String loginIdentifier
    );

    AuthUser save(NewAuthUser newUser);
}