package tn.iit.ws.entities.time;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContinuousTimeSlot extends TimeSlot{
	private Integer day;
	private Integer begin;
	private Integer end;
	private Boolean weekly;
	private Boolean weekA;
}
