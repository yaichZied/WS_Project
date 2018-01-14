package tn.iit.ws.entities.users;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.entities.all.Group;
import tn.iit.ws.utils.serializers.AuthoritySerializer;

@Entity
@Getter @Setter
public class PointingAgent extends User{
	private static final long serialVersionUID = 7671129186706784480L;
	@ManyToOne
	private Group groupe;
	@JsonSerialize(using=AuthoritySerializer.class)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_POINTING"));
	}
	
}
