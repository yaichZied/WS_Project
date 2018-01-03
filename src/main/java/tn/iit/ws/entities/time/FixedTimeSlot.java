package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.utils.annotations.Type;

@Entity
@Getter
@Setter
public class FixedTimeSlot extends TimeSlot {
	private Date date;
	@Type("duration")
	private Integer duration;

	@Override
	public String getDisplayName() {
		return String.format("%2$d Hours : %1$ta %1$td %1$tB %1$tY : %s", date, duration);
	}
}
