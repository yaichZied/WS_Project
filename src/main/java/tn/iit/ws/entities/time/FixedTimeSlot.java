package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FixedTimeSlot extends TimeSlot{
	private Date date;
	private Integer duration;
}
