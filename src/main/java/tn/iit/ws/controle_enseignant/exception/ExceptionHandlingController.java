package tn.iit.ws.controle_enseignant.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(IllegalArgumentException.class)
	public void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Error err = null;
		String[] s = e.getMessage().trim().split(",");
		if(s.length>=3)
		{
			if (ExceptionCodes.ID_NOT_EXISTANT.equals(s[0])) {
				err = new Error(101, "ID number: " + s[2] + " does not exist in the entity " + s[1]);
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				mapper.writer().writeValue(response.getOutputStream(), err);
				return;
			} else {
				if (ExceptionCodes.EXISTANT_ID.equals(s[0])) {
					err = new Error(101, "ID number: " + s[2] + " already exist in the entity " + s[1]);
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					mapper.writer().writeValue(response.getOutputStream(), err);
					return;
				} else {
					throw new Exception(e);
				}
			}
		}
		

	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public void methodNotAllowedException(HttpRequestMethodNotSupportedException e, HttpServletResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Error err = null;
				err = new Error(103, "The method that you called is not allowed within this API. Please check your request and try again ! ");
				response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
				mapper.writer().writeValue(response.getOutputStream(), err);
				return;

	}
	
	@ExceptionHandler(Exception.class)
	public void exception(IllegalArgumentException e, HttpServletResponse response) {
		e.printStackTrace();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Error err = null;
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			err = new Error(100, "Erreur Inconnue dans le serveur , r�essayez plus tard");
			mapper.writer().writeValue(response.getOutputStream(), err);
		} catch (Exception e1) {
			try {
				response.getWriter().println("error");
			} catch (IOException e2) {
			}

		}
	}

	public class Error {
		private int code;
		private String message;

		public Error(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public Error() {
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}