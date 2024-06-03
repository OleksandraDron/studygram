package org.sashkadron.studygram.repository.user;

import org.sashkadron.studygram.repository.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

	Set<Student> findAllByStudentFlow_FlowCodeIn(Set<String> flowCodes);

}
