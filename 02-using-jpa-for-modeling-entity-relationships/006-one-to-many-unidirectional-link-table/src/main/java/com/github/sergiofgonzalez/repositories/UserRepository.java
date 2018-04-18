package com.github.sergiofgonzalez.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.sergiofgonzalez.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	List<User> findByEmailsEmailLikeIgnoringCase(String email);
}
