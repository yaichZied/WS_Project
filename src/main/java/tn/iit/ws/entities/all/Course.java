package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.entities.time.TimeSlot;
import tn.iit.ws.entities.users.Teacher;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Subject subject;
	@ManyToOne
	private Group group;
	@ManyToOne
	private Teacher teacher;
	@ManyToOne
	private ClassRoom classRoom;
	@ManyToOne
	private TimeSlot timeSlot;
	public String getDisplayName()
	{
		return String.format("%s : %s ( %s : %s )", subject.getName(),teacher.getName(), group.getName(), classRoom.getName());
	}
}
