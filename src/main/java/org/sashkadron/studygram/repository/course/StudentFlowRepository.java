package org.sashkadron.studygram.repository.course;

import org.sashkadron.studygram.repository.user.entity.StudentFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentFlowRepository extends JpaRepository<StudentFlow, UUID> {
}
