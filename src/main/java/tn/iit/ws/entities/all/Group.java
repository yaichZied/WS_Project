package tn.iit.ws.entities.all;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.entities.users.Administrator;
import tn.iit.ws.entities.users.Teacher;
import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;
import tn.iit.ws.security.config.authorization.annotations.CanEdit;
import tn.iit.ws.security.config.authorization.annotations.CanSee;

@Entity
@Data
@EqualsAndHashCode(of="id")
@Table(name="group_table")
@CanSee(value= {"ROLE_ADMIN","ROLE_TEACHER"})
@CanAdd(value="ROLE_ADMIN")
@CanEdit(value="ROLE_ADMIN")
@CanDelete(value="ROLE_ADMIN")
public class Group {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private SubLevel subLevel;
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Teacher> teachers= new ArrayList<>();
	private String name;
	public String getDisplayName()
	{
		return name;
	}
	@CanSee
	private boolean addAuth(User user) {
		if(user instanceof Administrator) {
			return true;
		}
		if(user instanceof Teacher) {
			return teachers.contains(user);
		}
		return false;
	}
}
