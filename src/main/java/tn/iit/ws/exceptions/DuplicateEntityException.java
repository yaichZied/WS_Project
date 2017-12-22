package tn.iit.ws.exceptions;

public class DuplicateEntityException extends RuntimeException{
	private static final long serialVersionUID = -3918650179321088471L;
	private Object id;
	private Class<?> type;
	public DuplicateEntityException(Object id,Class<?> type) {
		this.id=id;
		this.type=type;
	}
	@Override
	public String getMessage() {	
		return String.format("%s with id %s not found",id,type.getSimpleName());
	}
}
