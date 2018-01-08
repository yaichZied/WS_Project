package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;
import tn.iit.ws.security.config.authorization.annotations.CanEdit;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@CanAdd(value="ROLE_ADMIN")
@CanEdit(value="ROLE_ADMIN")
@CanDelete(value="ROLE_ADMIN")
public class Semester {
	@Id
	@GeneratedValue
	private Long id;
	private Date begin;
	private Date end;

	public String getDisplayName() {
		return String.format(" From %1$ta %1$td %1$tB %1$tY To  %2$ta %2$td %2$tB %2$tY", begin, end);

	}

}
