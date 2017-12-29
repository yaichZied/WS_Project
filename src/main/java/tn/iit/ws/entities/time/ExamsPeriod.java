package tn.iit.ws.entities.time;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ExamsPeriod extends InterruptionPeriod{
	@Override
	public String getDisplayName()
	{
		return String.format("Exams : %s", super.getDisplayName());
	}
}
