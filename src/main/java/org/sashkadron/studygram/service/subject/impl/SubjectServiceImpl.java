package org.sashkadron.studygram.service.subject.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sashkadron.studygram.repository.subject.SubjectRepository;
import org.sashkadron.studygram.repository.subject.entity.SubjectEntity;
import org.sashkadron.studygram.service.subject.SubjectService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

	SubjectRepository subjectRepository;

	@Override
	public List<SubjectEntity> findSubjects(String name) {
		String search = StringUtils.isBlank(name) ? "" : name.toLowerCase();
		return subjectRepository.findAll(
				Specification.where((r, rq, cb) -> cb.like(cb.lower(r.get("title")), "%" + search + "%")),
				Sort.by("title").ascending()
		);
	}
}
