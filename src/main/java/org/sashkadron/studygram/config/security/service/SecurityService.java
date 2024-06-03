package org.sashkadron.studygram.config.security.service;

import org.sashkadron.studygram.config.security.SystemUserDetails;
import org.sashkadron.studygram.repository.user.entity.SystemUser;

import java.util.UUID;

public interface SecurityService {

	SystemUser getUser();

	UUID getUserId();

	SystemUserDetails getUserDetails();

}
