package org.sashkadron.studygram.service.user.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.config.exception.NotFoundException;
import org.sashkadron.studygram.repository.user.StudentRepository;
import org.sashkadron.studygram.repository.user.UserRepository;
import org.sashkadron.studygram.repository.user.entity.Student;
import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.sashkadron.studygram.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;


	@Override
	public Optional<SystemUser> findOptByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public SystemUser findByEmail(String email) {
		return findOptByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with specified email - not found"));
	}

	@Override
	public SystemUser findById(UUID userId) {
		return userRepository.findById(userId).orElseThrow();
	}

	@Override
	public Set<Student> findAllStudentsByFlowCodes(Set<String> flowCodes) {
		return studentRepository.findAllByStudentFlow_FlowCodeIn(flowCodes);
	}

}
