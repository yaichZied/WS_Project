package tn.iit.ws.entities.time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public abstract class TimeSlot {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Semester semester;

	public String getDisplayName() {
		return "";

	}

}
