package com.github.sergiofgonzalez.repositories;

import org.springframework.data.repository.CrudRepository;

import com.github.sergiofgonzalez.domain.Email;

public interface EmailRepository extends CrudRepository<Email, Long> {
	Email findByEmailIgnoringCase(String email);
}

