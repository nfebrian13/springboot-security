package com.bfwg.service;

import java.util.Optional;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;

/**
 * @author NANA 16/08/2020
 **/
public interface AuthorityService {
	Optional<Authority> findByName(UserRoleName name);
}
