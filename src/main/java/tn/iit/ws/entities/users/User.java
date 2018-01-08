package tn.iit.ws.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;
import tn.iit.ws.security.config.authorization.annotations.CanEdit;
import tn.iit.ws.security.config.authorization.annotations.CanSee;

@Entity
@Data
@EqualsAndHashCode(of="id")
@CanAdd(value="ROLE_ADMIN")
@CanEdit(value="ROLE_ADMIN")
@CanDelete(value="ROLE_ADMIN")
public abstract class User implements UserDetails{
	private static final long serialVersionUID = 861474528215620204L;
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String email;
	@Column(unique = true)
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
	@CanSee
	private boolean seeAuth(User user) {
		if(user instanceof Administrator) {
			return true;
		}
		return user.equals(this);
	}
}
