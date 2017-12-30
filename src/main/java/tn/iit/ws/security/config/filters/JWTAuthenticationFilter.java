package tn.iit.ws.security.config.filters;

import static tn.iit.ws.security.config.SecurityConstants.EXPIRATION_TIME;
import static tn.iit.ws.security.config.SecurityConstants.SECRET;
import static tn.iit.ws.security.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.iit.ws.entities.users.User;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private ObjectMapper mapper;
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(Authentication.class, new UserTokenAdder());
		mapper.registerModule(module);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (request.getHeader("Origin") != null) {
			String origin = request.getHeader("Origin");
			response.addHeader("Access-Control-Allow-Origin", origin);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		}
		return authenticationManager.authenticate(
				
				new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password")));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		mapper.writeValue(res.getOutputStream(), auth);
	}
	private static class UserTokenAdder extends StdSerializer<Authentication>
	{
		private static final long serialVersionUID = -4540931552054033264L;

		public UserTokenAdder() {
	        this(null);
	    }
	   
	    public UserTokenAdder(Class<Authentication> t) {
	        super(t);
	    }

		@Override
		public void serialize(Authentication auth, JsonGenerator jgen, SerializerProvider arg2) throws IOException {
			
			
			String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
			
			jgen.writeStartObject();
	        jgen.writeStringField("token", TOKEN_PREFIX + token);
	        jgen.writeObjectField("user", auth.getPrincipal());
	        jgen.writeEndObject();
		}
	}
}