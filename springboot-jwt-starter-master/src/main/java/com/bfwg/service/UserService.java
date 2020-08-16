package com.bfwg.service;

import com.bfwg.model.User;

import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    User save(User user);
    List<User> findAll ();
    
    boolean existsByUsername (String username);
    boolean existsByEmail (String email);
}
