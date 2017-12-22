package tn.iit.ws.entities.time;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class ExamsPeriod {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Date begin;
	private Date end;
	@ManyToOne
	private Semester semester;
}
