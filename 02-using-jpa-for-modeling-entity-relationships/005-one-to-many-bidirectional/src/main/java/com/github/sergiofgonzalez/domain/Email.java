package com.github.sergiofgonzalez.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "email")
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String email;
	
	@ManyToOne
	private User user;
	
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
			
	public User getUser() {
		return user;
	}

	public void addUser(User user) {
		this.user = user;
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
					.append("user", user)
					.toString();
	}
}
