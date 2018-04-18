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
	public void testSaveUserWithTwoEmails() {
		Email email1 = new Email("user11@example.com");
		Email email2 = new Email("user12@example.com");
		User user = new User("user1", "pass1");
		userRepository.save(user);
		
		user.addEmail(email1);
		user.addEmail(email2);
		email1.addUser(user);
		email2.addUser(user);
		Email savedEmail1 = emailRepository.save(email1);
		Email savedEmail2 = emailRepository.save(email2);		
		userRepository.save(user);

		assertThat(email1).isEqualTo(savedEmail1);
		assertThat(savedEmail1.getId()).isNotNull();
		assertThat(savedEmail1.getEmail()).isEqualTo("user11@example.com");
		assertThat(savedEmail1.getUser()).isNotNull();
		assertThat(savedEmail1.getUser()).isEqualTo(user);
		assertThat(savedEmail1.getUser().getUsername()).isEqualTo("user1");
		assertThat(savedEmail1.getUser().getPassword()).isEqualTo("pass1");		
		
		assertThat(email2).isEqualTo(savedEmail2);
		assertThat(savedEmail2.getId()).isNotNull();
		assertThat(savedEmail2.getEmail()).isEqualTo("user12@example.com");
		assertThat(savedEmail2.getUser()).isNotNull();
		assertThat(savedEmail2.getUser()).isEqualTo(user);
		assertThat(savedEmail2.getUser().getUsername()).isEqualTo("user1");
		assertThat(savedEmail2.getUser().getPassword()).isEqualTo("pass1");				
	}	
}