package org.sashkadron.studygram.controller.subject.facade.mapper;

import org.mapstruct.Mapper;
import org.sashkadron.studygram.controller.subject.dto.SubjectDto;
import org.sashkadron.studygram.repository.subject.entity.SubjectEntity;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

	SubjectDto toSubjectDto(SubjectEntity entity);

}
