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
@EqualsAndHashCode(of="id")
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
		return date.after(begin)&&date.before(end);
	}
}
