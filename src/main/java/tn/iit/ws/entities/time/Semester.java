package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
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
