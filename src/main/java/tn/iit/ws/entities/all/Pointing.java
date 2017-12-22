package tn.iit.ws.entities.all;

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
public class Pointing {
	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	@ManyToOne
	private Course course;
}
