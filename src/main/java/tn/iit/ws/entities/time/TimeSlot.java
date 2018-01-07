package tn.iit.ws.entities.time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tn.iit.ws.utils.serializers.AbstractDeserializer;

@Entity
@Data
@EqualsAndHashCode(of="id")
@JsonDeserialize(using=TimeSlotDeserializer.class,contentAs=TimeSlot.class)
public abstract class TimeSlot {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Semester semester;

	public String getDisplayName() {
		return "";

	}
	
}
@SuppressWarnings("serial")
class TimeSlotDeserializer<T> extends AbstractDeserializer<T> {
	
	public TimeSlotDeserializer() {
		super(TimeSlot.class);
	}

}
