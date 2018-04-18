package com.github.sergiofgonzalez.repositories;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
	@Sql({ "/sql/insert-5-users.sql" })
	public void testFindAllWithFiveRows() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users.size()).isEqualTo(5L);
		assertThat(users.stream().map(user -> user.getUsername()).collect(toList())).containsExactlyInAnyOrder("user1", "user2", "user3", "user4", "user5");
		assertThat(users.stream().map(user -> user.getEmail().getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com");
	}	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	public void testSaveOneUser() {
		User user = new User("jason", "isaacs");
		Email email = new Email("jason@wittertainment.co.uk");
		user.addEmail(email);
		Email savedEmail = emailRepository.save(email);
		User savedUser = userRepository.save(user);
		
		assertThat(user).isEqualTo(savedUser);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("jason");
		assertThat(savedUser.getEmail().getEmail()).isEqualTo("jason@wittertainment.co.uk");
		assertThat(user.getEmail()).isEqualTo(savedEmail);
	}	
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-1-user.sql" })
	public void testDeleteUserShouldNotCascadeByDefault() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		userRepository.delete(savedUser);
		Optional<User> savedUserAfterDeletion = userRepository.findById(savedUser.getId()); 
		List<User> usersAfterDeletion = (List<User>) userRepository.findAll();
		List<Email> emailsAfterDeletion = (List<Email>) emailRepository.findAll();
		
		assertThat(savedUserAfterDeletion.isPresent()).isFalse();
		assertThat(usersAfterDeletion).isEmpty();
		assertThat(emailsAfterDeletion).isNotEmpty();
	}	
	
	
	@Test(expected = DataIntegrityViolationException.class)
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-1-user.sql" })
	public void testDeleteEmailWithoutDeletingUserShouldNotBeAllowed() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		emailRepository.delete(savedUser.getEmail());
	}	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-1-user.sql" })
	public void testUpdateNonEmailFieldsOnExistingUser() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		User newUser = new User(savedUser.getId(), savedUser.getUsername() + "-copy", savedUser.getPassword() + "-copy");
		newUser.addEmail(savedUser.getEmail());
		
		userRepository.save(newUser);
		List<User> users = (List<User>) userRepository.findAll();

		assertThat(users.size()).isEqualTo(1L);
		assertThat(users.get(0).getUsername()).isEqualTo("user1-copy");
		assertThat(users.get(0).getPassword()).isEqualTo("pass1-copy");
		assertThat(users.get(0).getEmail().getEmail()).isEqualTo("user1@example.com");
	}
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-1-user.sql" })
	public void testUpdateEmailOnExistingUser() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		Email newEmail = emailRepository.save(new Email("new.email@example.com"));
		User newUser = new User(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword());
		newUser.addEmail(newEmail);
		
		userRepository.save(newUser);
		List<User> users = (List<User>) userRepository.findAll();

		assertThat(users.size()).isEqualTo(1L);
		assertThat(users.get(0).getUsername()).isEqualTo("user1");
		assertThat(users.get(0).getPassword()).isEqualTo("pass1");
		assertThat(users.get(0).getEmail().getEmail()).isEqualTo("new.email@example.com");
	}
	
	
	@Test
	@Sql(scripts = { "/sql/schema/create-schema.sql", "/sql/schema/create-supporting-stored-procs.sql" }, config = @SqlConfig(separator = "//"))	
	@Sql({ "/sql/insert-3-users-different-email-pattern.sql" })
	public void testFindUserByEmails() {
		List<User> users = (List<User>) userRepository.findByEmailEmailLikeIgnoringCase("%github%");
		
		assertThat(users.size()).isEqualTo(1L);
		assertThat(users.get(0).getEmail().getEmail()).isEqualTo("user2@github.com");		
	}
	
}
