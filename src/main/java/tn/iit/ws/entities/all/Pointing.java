package tn.iit.ws.entities.all;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.entities.users.PointingAgent;
import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;
import tn.iit.ws.security.config.authorization.annotations.CanEdit;
import tn.iit.ws.security.config.authorization.annotations.CanSee;

@Entity
@Data
@EqualsAndHashCode(of="id")
@CanSee(value={"ROLE_ADMIN","ROLE_POINTING","ROLE_TEACHER"})
@CanAdd(value={"ROLE_ADMIN","ROLE_POINTING"})
@CanEdit(value={"ROLE_ADMIN","ROLE_POINTING"})
@CanDelete(value={"ROLE_ADMIN","ROLE_POINTING"})
public class Pointing {
	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	@ManyToOne
	private Course course;
	public Pointing(){
		date = new Date();
	}
	public String getDisplayName()
	{
		try {
			DateFormat df = DateFormat.getDateInstance();
			if(course!=null)
				return String.format("Pointing for : %s at %s ",course.
						getDisplayName(), df.format(date));
			return String.format("Pointing at %s ", df.format(date));
		} catch (Exception e) {
			return "";
		}
	}
	@CanSee
	private boolean addAuth(User user) {
		if(user instanceof Administrator) {
			return true;
		}
		if(user instanceof PointingAgent) {
			return true;
		}
		if(user instanceof Teacher) {
			return course.getTeacher().equals(user);
		}
		return false;
	}
}
