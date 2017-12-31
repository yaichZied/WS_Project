package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public abstract class InterruptionPeriod {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Semester semester;
	private Date begin;
	private Date end;
	private String name;

	public boolean isInPeriod(Date date) {
		return date.after(begin) && date.before(end);
	}

	public String getDisplayName() {
		return String.format(" From %1$ta %1$td %1$tB %1$tY To  %2$ta %2$td %2$tB %2$tY", begin, end);

	}
}
