package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanSee;
import tn.iit.ws.utils.annotations.Type;

@Entity
@Getter
@Setter
@CanSee(value= {"ROLE_ADMIN","ROLE_POINTING","ROLE_TEACHER"})
@CanAdd(value={"ROLE_ADMIN","ROLE_TEACHER"})
public class FixedTimeSlot extends TimeSlot {

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Type("duration")
	private Integer duration;
	
	@Override
	public String getDisplayName() {
		return String.format("%2$d Hours : %1$ta %1$td %1$tB %1$tY ", date, duration);
	}
}
