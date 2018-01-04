package tn.iit.ws.utils.search.criteria;

import javax.persistence.Query;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MultipleCriteria extends Criteria{
	private Criteria[] list;
	private String operator;
	private Class<?> clazz;
	@Override
	public String process(Class<?> clazz) {
		StringBuilder sb = new StringBuilder(" ( ");
		if (list != null && list.length > 0) {
			for (int i = 0; i < list.length; i++) {
				sb.append(list[i].process(clazz));
				if (i < list.length - 1) {
					sb.append(" ");
					sb.append(operator);
					sb.append(" ");
				}
			}
		}
		else {
			throw new RuntimeException(String.format("Criteria group %s can't be empty",operator));
		}
		sb.append(" ) ");
		return sb.toString();
	}
	@Override
	public void setValues(Query query) {
		for (int i = 0; i < list.length; i++) {
			list[i].setValues(query);
		}
	}
	
}
