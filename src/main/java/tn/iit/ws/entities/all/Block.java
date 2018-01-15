package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;
import tn.iit.ws.security.config.authorization.annotations.CanEdit;
import tn.iit.ws.security.config.authorization.annotations.CanSee;

@Entity
@Data
@EqualsAndHashCode(of="id")
@CanSee(value="ROLE_ADMIN")
@CanAdd(value="ROLE_ADMIN")
@CanEdit(value="ROLE_ADMIN")
@CanDelete(value="ROLE_ADMIN")
public class Block {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	public String getDisplayName()
	{
		return name;
	}
}
