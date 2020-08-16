package com.bfwg.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;
import com.bfwg.repository.AuthorityRepository;
import com.bfwg.service.AuthorityService;

/**
 * @author NANA 16/08/2020
 **/

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository userRepository;

	@Override
	public Optional<Authority> findByName(UserRoleName roleName) {
		return userRepository.findByName(roleName);
	}

}
