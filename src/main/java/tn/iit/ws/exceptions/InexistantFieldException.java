package tn.iit.ws.exceptions;

public class InexistantFieldException extends RuntimeException{
	private static final long serialVersionUID = -3918650179321088471L;
	private String field;
	private Class<?> type;
	public InexistantFieldException(String field,Class<?> type) {
		this.field=field;
		this.type=type;
	}
	@Override
	public String getMessage() {	
		return String.format("%s is inexistant in %s type",field,type.getSimpleName());
	}
}
