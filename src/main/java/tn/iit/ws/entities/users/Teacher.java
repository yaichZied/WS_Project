package tn.iit.ws.entities.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.entities.all.Group;

@Entity
@Getter @Setter
public class Teacher extends User {
	private static final long serialVersionUID = -2733994786562901142L;
	@ManyToMany
	private List<Group> groupes;
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_TEACHER"));
	}
	
}
