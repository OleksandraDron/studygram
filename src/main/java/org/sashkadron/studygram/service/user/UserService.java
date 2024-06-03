package org.sashkadron.studygram.service.user;

import org.sashkadron.studygram.repository.user.entity.Student;
import org.sashkadron.studygram.repository.user.entity.SystemUser;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

	Optional<SystemUser> findOptByEmail(String email);

	SystemUser findByEmail(String email);

	SystemUser findById(UUID userId);

	Set<Student> findAllStudentsByFlowCodes(Set<String> flowCodes);

}
