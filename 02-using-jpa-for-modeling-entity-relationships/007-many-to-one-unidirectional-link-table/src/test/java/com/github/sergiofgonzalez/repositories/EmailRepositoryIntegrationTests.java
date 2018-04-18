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
		assertThat(emails.stream().map(email -> email.getUser().getUsername()).collect(toSet())).containsExactlyInAnyOrder("user1", "user2", "user3");
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/create-6-emails-with-users.sql" })
	public void testByUsernameLike() {
		List<Email> user1Emails = (List<Email>) emailRepository.findByUserUsernameLikeIgnoringCase("%user1%");
		List<Email> user2Emails = (List<Email>) emailRepository.findByUserUsernameLikeIgnoringCase("%user2%");
		List<Email> user3Emails = (List<Email>) emailRepository.findByUserUsernameLikeIgnoringCase("%user3%");
		
		assertThat(user1Emails.size()).isEqualTo(1L);
		assertThat(user2Emails.size()).isEqualTo(2L);
		assertThat(user3Emails.size()).isEqualTo(3L);

		assertThat(user1Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com");
		assertThat(user2Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user21@example.com", "user22@example.com");
		assertThat(user3Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user31@example.com", "user32@example.com", "user33@example.com");		
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
	@Sql({ "/sql/create-6-emails-with-users.sql" })
	public void testUpdateEmailUser() {
		Email email = emailRepository.findByEmailIgnoringCase("user21@example.com");
		User newUser = new User("new-user", "new-pass");
		email.addUser(newUser);
		
		userRepository.save(newUser);
		emailRepository.save(email);
		
		List<Email> newUserEmails = (List<Email>) emailRepository.findByUserUsernameLikeIgnoringCase("%new-user%");
		
		assertThat(newUserEmails).hasSize(1);
		assertThat(newUserEmails.get(0).getUser().getUsername()).isEqualTo("new-user");
		assertThat(emailRepository.findAll()).hasSize(6);
	}		

	
}