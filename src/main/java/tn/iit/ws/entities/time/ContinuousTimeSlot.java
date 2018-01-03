package tn.iit.ws.entities.time;

import java.util.Calendar;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.utils.annotations.If;
import tn.iit.ws.utils.annotations.Type;

@Entity
@Getter
@Setter
public class ContinuousTimeSlot extends TimeSlot {
	private Integer day;
	@Type("time")
	private Integer begin;
	@Type("time")
	private Integer end;
	private Boolean weekly;
	@If(value = "weekly",not = true)
	private Boolean weekA;
	@Override
	public String getDisplayName() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,day+1);
		return String.format("%tA %2d:%2d => %2d:%2d ", c, begin / 60, begin % 60, end / 60, end % 60);
	}
}
