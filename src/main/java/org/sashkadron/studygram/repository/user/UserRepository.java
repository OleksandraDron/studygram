package org.sashkadron.studygram.repository.user;

import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<SystemUser, UUID> {

	Optional<SystemUser> findByEmail(String email);
}
