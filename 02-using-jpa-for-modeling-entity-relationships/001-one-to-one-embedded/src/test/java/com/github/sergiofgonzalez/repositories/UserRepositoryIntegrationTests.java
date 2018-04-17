package com.github.sergiofgonzalez.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.sergiofgonzalez.domain.Email;
import com.github.sergiofgonzalez.domain.User;
import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTests {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql" })
	public void testFindAllEmpty() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users.size()).isEqualTo(0L);
	}
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-5-users.sql" })
	public void testFindAllWithFiveRows() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users.size()).isEqualTo(5L);
		assertThat(users.stream().map(user -> user.getUsername()).collect(toList())).containsExactlyInAnyOrder("user1", "user2", "user3", "user4", "user5");
		assertThat(users.stream().map(user -> user.getEmail().getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com");
	}	
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql" })
	public void testSaveOneUser() {
		User user = new User("jason", "isaacs");
		user.addEmail(new Email("jason@wittertainment.co.uk"));
		User savedUser = userRepository.save(user);
		
		assertThat(user).isEqualTo(savedUser);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("jason");
		assertThat(savedUser.getEmail().getEmail()).isEqualTo("jason@wittertainment.co.uk");
	}	
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql" })
	public void testSaveOneUserAndFindShouldBeEqual() {
		User user = new User("jason", "isaacs");
		user.addEmail(new Email("jason@wittertainment.co.uk"));
		userRepository.save(user); // ignoring return value
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		
		assertThat(user).isEqualTo(savedUser);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("jason");
		assertThat(savedUser.getEmail().getEmail()).isEqualTo("jason@wittertainment.co.uk");
	}
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-1-user.sql" })
	public void testDeleteUserShouldFailWithoutId() {
		User user = new User("user1", "password1");
		user.addEmail(new Email("user1@example.com"));

		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		userRepository.delete(user);
		Optional<User> savedUserAfterDeletion = userRepository.findById(savedUser.getId()); 

		assertThat(savedUser.getUsername()).isEqualTo("user1");
		assertThat(savedUser.getEmail().getEmail()).isEqualTo("user1@example.com");
		assertThat(savedUser.getId()).isNotNull();

		assertThat(savedUserAfterDeletion.isPresent()).isTrue();
		assertThat(savedUserAfterDeletion.get().getUsername()).isEqualTo("user1");
		assertThat(savedUserAfterDeletion.get().getEmail().getEmail()).isEqualTo("user1@example.com");				
	}
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-1-user.sql" })
	public void testDeleteUserShouldWorkWithId() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		userRepository.delete(savedUser);
		Optional<User> savedUserAfterDeletion = userRepository.findById(savedUser.getId()); 
		List<User> usersAfterDeletion = (List<User>) userRepository.findAll();

		assertThat(savedUserAfterDeletion.isPresent()).isFalse();
		assertThat(usersAfterDeletion).isEmpty();
	}	
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-1-user.sql" })
	public void testSaveAlsoUpdateExistingUser() {
		User savedUser = ((List<User>) userRepository.findAll()).get(0);
		User newUser = new User(savedUser.getId(), savedUser.getUsername() + "-copy", savedUser.getPassword() + "-copy");
		newUser.addEmail(new Email(savedUser.getEmail().getEmail() + ".copy"));
		
		userRepository.save(newUser);
		List<User> users = (List<User>) userRepository.findAll();

		assertThat(users.size()).isEqualTo(1L);
		assertThat(users.get(0).getUsername()).isEqualTo("user1-copy");
		assertThat(users.get(0).getPassword()).isEqualTo("pass1-copy");
		assertThat(users.get(0).getEmail().getEmail()).isEqualTo("user1@example.com.copy");
	}
	
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-5-users.sql" })
	public void testFindAllEmails() {
		List<Email> emails = StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.map(user -> user.getEmail())
					.collect(toList());
		
		assertThat(emails.size()).isEqualTo(5L);
		assertThat(emails).containsExactlyInAnyOrder(new Email("user1@example.com"), new Email("user2@example.com"), new Email("user3@example.com"), new Email("user4@example.com"), new Email("user5@example.com"));		
	}
	
	@Test
	@Sql({ "/sql/schema/create-schema.sql", "/sql/truncate-tables.sql", "/sql/insert-5-users.sql" })
	public void testFindByEmail() {
		List<User> users = (List<User>) userRepository.findByEmailEmailLikeIgnoringCase("%");
		
		assertThat(users.size()).isEqualTo(5L);
		assertThat(users.stream().map(user -> user.getUsername()).collect(toList())).containsExactlyInAnyOrder("user1", "user2", "user3", "user4", "user5");
		assertThat(users.stream().map(user -> user.getEmail().getEmail()).collect(toList())).containsExactlyInAnyOrder("user1@example.com", "user2@example.com", "user3@example.com", "user4@example.com", "user5@example.com");	
	}	
}
