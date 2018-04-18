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
	@Sql({ "/sql/insert-5-users.sql" })
	public void testFindAllWithFiveRows() {
		List<Email> emails = (List<Email>) emailRepository.findAll();
		assertThat(emails.size()).isEqualTo(5L);
		assertThat(emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com");
		assertThat(emails.stream().map(email -> email.getUser().getUsername())).containsExactlyInAnyOrder("user1", "user2", "user3", "user4", "user5");
	}	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testSaveOneEmail() {
		Email email = new Email("jason@wittertainment.co.uk");
		User user = new User("jason.isaacs", "secret");
		email.addUser(user);
		userRepository.save(user);
		Email savedEmail = emailRepository.save(email);
		
		
		assertThat(email).isEqualTo(savedEmail);
		assertThat(savedEmail.getId()).isNotNull();
		assertThat(savedEmail.getEmail()).isEqualTo("jason@wittertainment.co.uk");
		
		assertThat(savedEmail.getUser()).isNotNull();
		assertThat(savedEmail.getUser()).isEqualTo(user);
		assertThat(savedEmail.getUser().getUsername()).isEqualTo("jason.isaacs");
		assertThat(savedEmail.getUser().getPassword()).isEqualTo("secret");		
	}		
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-3-users-different-email-pattern.sql" })
	public void testFindEmailByUsername() {
		List<Email> emails = (List<Email>) emailRepository.findByUserUsernameLikeIgnoringCase("%git%");
		
		assertThat(emails.size()).isEqualTo(1L);
		assertThat(emails.get(0).getUser().getUsername()).isEqualTo("githubuser");		
	}
	
}