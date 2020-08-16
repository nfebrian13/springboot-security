package com.bfwg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Optional<Authority> findByName(UserRoleName name);
}
