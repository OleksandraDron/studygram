package org.sashkadron.studygram.controller.user.facade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sashkadron.studygram.controller.user.dto.LecturerDto;
import org.sashkadron.studygram.controller.user.dto.StudentDto;
import org.sashkadron.studygram.controller.user.dto.UserDto;
import org.sashkadron.studygram.controller.user.dto.UserLogInDto;
import org.sashkadron.studygram.repository.user.entity.Lecturer;
import org.sashkadron.studygram.repository.user.entity.Student;
import org.sashkadron.studygram.repository.user.entity.SystemUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toUserDto(SystemUser user);

	StudentDto toStudentDto(Student user);

	LecturerDto toLecturerDto(Lecturer user);

	UserLogInDto toUserLogInDto(SystemUser user, String accessToken);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "password", ignore = true)
	SystemUser toSystemUser(UserDto userDto);

}
