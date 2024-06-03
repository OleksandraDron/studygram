package org.sashkadron.studygram.controller.subject.facade.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.controller.subject.dto.SubjectDto;
import org.sashkadron.studygram.controller.subject.facade.SubjectFacade;
import org.sashkadron.studygram.controller.subject.facade.mapper.SubjectMapper;
import org.sashkadron.studygram.service.subject.SubjectService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SubjectFacadeImpl implements SubjectFacade {

	private final SubjectService subjectService;
	private final SubjectMapper subjectMapper;

	@Override
	public List<SubjectDto> findSubjects(String name) {
		return subjectService.findSubjects(name).stream().map(subjectMapper::toSubjectDto).toList();
	}
}
