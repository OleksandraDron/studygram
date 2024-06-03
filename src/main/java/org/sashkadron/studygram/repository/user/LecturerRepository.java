package org.sashkadron.studygram.repository.user;

import org.sashkadron.studygram.repository.user.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {

}
