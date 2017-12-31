package tn.iit.ws.entities.all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
@Table(name="group_table")
public class Group {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private SubLevel subLevel;
	private String name;
	public String getDisplayName()
	{
		return name;
	}
}
