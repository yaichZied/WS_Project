package tn.iit.ws.entities.time;

import java.util.Calendar;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContinuousTimeSlot extends TimeSlot {
	private Integer day;
	private Integer begin;
	private Integer end;
	private Boolean weekly;
	private Boolean weekA;

	@Override
	public String getDisplayName() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,day+1);
		return String.format("%tA %2d:%2d => %2d:%2d ", c, begin / 60, begin % 60, end / 60, end % 60);
	}
}
