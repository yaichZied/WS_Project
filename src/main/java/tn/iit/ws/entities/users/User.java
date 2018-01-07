package tn.iit.ws.entities.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.utils.serializers.AbstractDeserializer;

@Entity
@Data
@EqualsAndHashCode(of="id")
@JsonDeserialize(using=UserDeserializer.class,contentAs=User.class)
public abstract class User implements UserDetails{
	private static final long serialVersionUID = 861474528215620204L;
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	private String username;
	@JsonIgnore
	private String password;

	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		return true;
	}
	public boolean isEnabled() {
		return true;
	}
	
	public String getDisplayName()
	{
		return name;
	}
}
@SuppressWarnings("serial")
class UserDeserializer<T> extends AbstractDeserializer<T> {
	
	public UserDeserializer() {
		super(User.class);
	}

}
