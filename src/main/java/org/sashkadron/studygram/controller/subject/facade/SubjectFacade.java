package org.sashkadron.studygram.controller.subject.facade;

import org.sashkadron.studygram.controller.subject.dto.SubjectDto;

import java.util.List;

public interface SubjectFacade {

	List<SubjectDto> findSubjects(String name);

}
