package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class ClassRoom {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Floor floor;
	private String name;
}
