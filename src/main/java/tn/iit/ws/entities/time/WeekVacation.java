package tn.iit.ws.entities.time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class WeekVacation {
	@Id
	@GeneratedValue
	private Long id;
	private Integer day;
}
