package tn.iit.ws.entities.users;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Teacher extends User {
	private static final long serialVersionUID = -2733994786562901142L;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_TEACHER"));
	}
	
}
