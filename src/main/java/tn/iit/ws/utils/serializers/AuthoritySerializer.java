package tn.iit.ws.utils.serializers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class AuthoritySerializer extends StdSerializer<Collection<? extends GrantedAuthority>> {
	private static final long serialVersionUID = -7409356447075915597L;

	public AuthoritySerializer() {
		super(Collection.class, false);
	}

	@Override
	public void serialize(Collection<? extends GrantedAuthority> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartArray();
		value.forEach(auth ->{
			try {
				jgen.writeString(auth.getAuthority());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		jgen.writeEndArray();
	}

}