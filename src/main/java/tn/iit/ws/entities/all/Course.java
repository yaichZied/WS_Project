package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.entities.time.TimeSlot;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.entities.users.PointingAgent;
import tn.iit.ws.entities.users.Student;
import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanSee;

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
		try {
			return String.format("%s : %s ( %s : %s )", subject.getName(),teacher.getName(), group.getName(), classRoom.getName());
		} catch (Exception e) {
			return "Invalide";
		}
	}
	@CanSee
	private boolean seeAuth(User user) {
		if(user instanceof Administrator) {
			return true;
		}
		if(user instanceof PointingAgent) {
			return true;
		}
		if(user instanceof Teacher) {
			return user.getId().equals(teacher.getId());
		}
		if(user instanceof Student) {
			Student st = (Student) user;
			try {
				return st.getGroupe().getId().equals(group.getId());
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	@CanAdd
	private boolean addAuth(User user) {
		if(user instanceof Administrator) {
			return true;
		}
		if(user instanceof Teacher) {
			return user.getId().equals(teacher.getId());
		}
		return false;
	}
}
