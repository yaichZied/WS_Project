package tn.iit.ws.entities.users;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.utils.serializers.AuthoritySerializer;

@Entity
@Getter @Setter
public class Teacher extends User {
	private static final long serialVersionUID = -2733994786562901142L;

//	@JsonIgnore
//	@ManyToMany(mappedBy="teachers",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	private Set<Group> groups= new HashSet<>();
	@JsonSerialize(using=AuthoritySerializer.class)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_TEACHER"));
	}
	
}
