package com.bfwg.service.impl;

import com.bfwg.model.User;
import com.bfwg.repository.UserRepository;
import com.bfwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findOne(id);
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

}
