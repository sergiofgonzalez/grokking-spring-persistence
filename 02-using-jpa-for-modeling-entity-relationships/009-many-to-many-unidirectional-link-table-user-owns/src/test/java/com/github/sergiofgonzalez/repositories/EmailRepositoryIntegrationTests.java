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
	@Sql({ "/sql/create-several-emails-with-users.sql" })
	public void testFindAllWithFiveRowsNoCardinalityCheck() {
		List<Email> emails = (List<Email>) emailRepository.findAll();
		assertThat(emails.size()).isEqualTo(7L);
		assertThat(emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user21@example.com", "user22@example.com", "user31@example.com", "user32@example.com", "user33@example.com", "common@example.com");
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testSaveOneEmail() {
		Email email = new Email("jason@wittertainment.co.uk");
		User user = new User("jason.isaacs", "secret");
		userRepository.save(user);
		Email savedEmail = emailRepository.save(email);
		
		
		assertThat(email).isEqualTo(savedEmail);
		assertThat(savedEmail.getId()).isNotNull();
		assertThat(savedEmail.getEmail()).isEqualTo("jason@wittertainment.co.uk");
	}		
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testSaveUserWithTwoEmails() {
		Email email1 = new Email("user11@example.com");
		Email email2 = new Email("user12@example.com");
		User user = new User("user1", "pass1");
		userRepository.save(user);
		
		user.addEmail(email1);
		user.addEmail(email2);
		Email savedEmail1 = emailRepository.save(email1);
		Email savedEmail2 = emailRepository.save(email2);		
		userRepository.save(user);

		assertThat(email1).isEqualTo(savedEmail1);
		assertThat(savedEmail1.getId()).isNotNull();
		assertThat(savedEmail1.getEmail()).isEqualTo("user11@example.com");
		
		assertThat(email2).isEqualTo(savedEmail2);
		assertThat(savedEmail2.getId()).isNotNull();
		assertThat(savedEmail2.getEmail()).isEqualTo("user12@example.com");
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testReusingEmailForTwoUser() {
		Email email = new Email("common@example.com");
		Email savedEmail = emailRepository.save(email);

		User user1 = new User("user1", "pass1");
		user1.addEmail(savedEmail);
		userRepository.save(user1);
		
		User user2 = new User("user2", "pass2");
		user2.addEmail(savedEmail);
		userRepository.save(user2);
		
		User savedUser1 = userRepository.findByUsername("user1");
		assertThat(savedUser1.getEmails()).hasSize(1);
		assertThat(savedUser1.getEmails().iterator().next()).isEqualTo(savedEmail);
		
		User savedUser2 = userRepository.findByUsername("user2");
		assertThat(savedUser2.getEmails()).hasSize(1);
		assertThat(savedUser2.getEmails().iterator().next()).isEqualTo(savedEmail);		

		assertThat(emailRepository.findAll()).hasSize(1);
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	public void testReusingSameEmailForUserIsNotAllowed() {
		Email email = new Email("common@example.com");
		Email savedEmail = emailRepository.save(email);

		User user1 = new User("user1", "pass1");
		user1.addEmail(savedEmail);
		user1.addEmail(savedEmail);
		userRepository.save(user1);
		
		User savedUser1 = userRepository.findByUsername("user1");
		assertThat(savedUser1.getEmails()).hasSize(1);
		assertThat(savedUser1.getEmails().iterator().next()).isEqualTo(savedEmail);
	}		
	
}