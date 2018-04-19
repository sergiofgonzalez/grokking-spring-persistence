package com.github.sergiofgonzalez.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "email")
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String email;
		
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(uniqueConstraints = { @UniqueConstraint(columnNames = { "email_id", "users_id" })})
	private List<User> users = new ArrayList<>();
	
	protected Email() {		
	}
	
	public Email(String email) {
		this.email = email;
	}
	
	public Email(Long id, String email) {
		this.id = id;
		this.email = email;
	}
		
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
				
	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
					.append("id", id)
					.append("email", email)
					.append("users", users)
					.toString();
	}
}
