package com.github.sergiofgonzalez.repositories;

import java.util.List;
import java.util.Set;

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
public class UserRepositoryIntegrationTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailRepository emailRepository;	
		
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	public void testFindAllEmpty() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users.size()).isEqualTo(0L);
	}
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))
	@Sql({ "/sql/create-5-users.sql" })
	public void testFindAllWithFiveRows() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users.size()).isEqualTo(5L);
		assertThat(users.stream().map(user -> user.getUsername()).collect(toList())).containsExactlyInAnyOrder("user1", "user2", "user3", "user4", "user5");
	}	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	public void testSaveOneUser() {
		User user = new User("jason", "isaacs");
		User savedUser = userRepository.save(user);
		
		assertThat(user).isEqualTo(savedUser);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("jason");
	}	
	
			

	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/create-1-user.sql" })
	public void testUpdateEmailOnExistingUser() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		User newUser = new User(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword());
		
		userRepository.save(newUser);
		List<User> users = (List<User>) userRepository.findAll();

		assertThat(users.size()).isEqualTo(1L);
		assertThat(users.get(0).getUsername()).isEqualTo("user1");
		assertThat(users.get(0).getPassword()).isEqualTo("pass1");
	}	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/create-several-emails-with-users.sql" })
	public void testDeleteUserWithAssociatedEmail() {
		
		Email email1 = emailRepository.findByEmailIgnoringCase("user1@example.com");
		Email modifiedEmail1 = new Email(email1.getId(), email1.getEmail());
		
		emailRepository.save(modifiedEmail1);
		
		User user1 = userRepository.findByUsername("user1");		
		userRepository.delete(user1);
		
		User user1AfterDeletion = userRepository.findByUsername("user1"); 
		
		assertThat(user1AfterDeletion).isNull();	
		assertThat(emailRepository.findByEmailIgnoringCase("user1@example.com")).isNotNull();
	}		
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/create-several-emails-with-users.sql" })
	public void testCheckUserEmails() {		
		Set<Email> user1Emails = userRepository.findByUsername("user1").getEmails();
		Set<Email> user2Emails = userRepository.findByUsername("user2").getEmails();
		Set<Email> user3Emails = userRepository.findByUsername("user3").getEmails();
		
				
		assertThat(user1Emails).hasSize(1);
		assertThat(user1Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com");
		
		assertThat(user2Emails).hasSize(3);
		assertThat(user2Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user21@example.com", "user22@example.com", "common@example.com");		

		assertThat(user3Emails).hasSize(4);
		assertThat(user3Emails.stream().map(email -> email.getEmail()).collect(toList())).containsExactlyInAnyOrder("user31@example.com", "user32@example.com", "user33@example.com", "common@example.com");		
	}		
	
}
