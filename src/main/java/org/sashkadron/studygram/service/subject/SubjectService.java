package org.sashkadron.studygram.service.subject;

import org.sashkadron.studygram.repository.subject.entity.SubjectEntity;

import java.util.List;

public interface SubjectService {

	List<SubjectEntity> findSubjects(String name);

}
