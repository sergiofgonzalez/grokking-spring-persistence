package com.github.sergiofgonzalez.repositories;

import java.util.List;
import static java.util.stream.Collectors.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.sergiofgonzalez.domain.Email;
import com.github.sergiofgonzalez.domain.User;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailRepositoryIntegrationTests {
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testFindAllEmpty() {
		List<Email> emails = (List<Email>) emailRepository.findAll();
		assertThat(emails.size()).isEqualTo(0L);
	}
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/create-6-emails-with-users.sql" })
	public void testFindAllWithFiveRowsNoCardinalityCheck() {
		List<Email> emails = (List<Email>) emailRepository.findAll();
		assertThat(emails.size()).isEqualTo(6L);
		assertThat(emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user21@example.com", "user22@example.com", "user31@example.com", "user32@example.com", "user33@example.com");
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testSaveOneEmail() {
		Email email = new Email("jason@wittertainment.co.uk");
		User user = new User("jason.isaacs", "secret");
		user.addEmail(email);

		Email savedEmail = emailRepository.save(email);
		userRepository.save(user);
		
		assertThat(email).isEqualTo(savedEmail);
		assertThat(savedEmail.getId()).isNotNull();
		assertThat(savedEmail.getEmail()).isEqualTo("jason@wittertainment.co.uk");		
	}			
}