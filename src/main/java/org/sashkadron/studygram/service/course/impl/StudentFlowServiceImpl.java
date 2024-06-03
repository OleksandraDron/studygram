package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.StudentFlowRepository;
import org.sashkadron.studygram.repository.user.entity.StudentFlow;
import org.sashkadron.studygram.service.course.StudentFlowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentFlowServiceImpl implements StudentFlowService {

	private final StudentFlowRepository studentFlowRepository;

	@Override
	public List<StudentFlow> findAll() {
		return studentFlowRepository.findAll();
	}
}
