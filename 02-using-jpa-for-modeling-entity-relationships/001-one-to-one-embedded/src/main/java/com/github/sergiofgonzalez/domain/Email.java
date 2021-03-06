package com.github.sergiofgonzalez.domain;

import javax.persistence.Column;

import org.springframework.core.style.ToStringCreator;

public class Email {

	protected Email() {		
	}
	
	public Email(String email) {
		this.email = email;
	}
	
	@Column(length = 50, nullable = false)
	private String email;
	
	public String getEmail() {
		return email;
	}

		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
					.append("email", email)
					.toString();
	}
}
