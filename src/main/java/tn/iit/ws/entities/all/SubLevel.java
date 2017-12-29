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
public class SubLevel {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Level level;
	private String name;
	public String getDisplayName()
	{
		return name;
	}
}
