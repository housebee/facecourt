package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(final String username);

}
