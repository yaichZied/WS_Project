package tn.iit.ws.utils.search.criteria;

import javax.persistence.Query;

public abstract class Criteria {
	public abstract String process(Class<?> clazz);
	public abstract void setValues(Query query);
}
