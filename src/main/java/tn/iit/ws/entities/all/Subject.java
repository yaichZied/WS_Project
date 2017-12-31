package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Subject {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	public String getDisplayName()
	{
		return name;
	}
}
