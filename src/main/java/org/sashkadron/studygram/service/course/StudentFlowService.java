package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.user.entity.StudentFlow;

import java.util.List;

public interface StudentFlowService {

	List<StudentFlow> findAll();

}
