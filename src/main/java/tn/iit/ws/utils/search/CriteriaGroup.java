package tn.iit.ws.utils.search;

import javax.persistence.Query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriteriaGroup {
	private String name;
	private CriteriaGroup[] groups;
	private Criteria[] filters;
	public String process(Class<?> clazz) {
		if (verify()) {
			StringBuilder sb = new StringBuilder(" ( ");
			boolean begin = false;
			if (filters != null && filters.length > 0) {
				begin = true;
				for (int i = 0; i < filters.length; i++) {
					sb.append(filters[i].process(clazz));
					if (i < filters.length - 1 || (groups != null && groups.length > 0)) {

						sb.append(" ");
						sb.append(name);
					}
					sb.append(" ");
				}
			}
			if (groups != null && groups.length > 0) {
				begin = true;
				for (int i = 0; i < groups.length; i++) {
					sb.append(groups[i].process(clazz));
					if (i < groups.length - 1) {

						sb.append(" ");
						sb.append(name);
					}
					sb.append(" ");
				}
			}
			sb.append(" ) ");
			if (begin) {
				return sb.toString();
			}

		}
		return " (true) ";
	}
	public void setValues(Query query) {
		if (filters != null && filters.length > 0) {
			for (int i = 0; i < filters.length; i++) {
				filters[i].setValues(query);
			}
		}
		if (groups != null && groups.length > 0) {
			for (int i = 0; i < groups.length; i++) {
				groups[i].setValues(query);
			}
		}
	}
	private boolean verify() {
		return filters != null && filters.length > 0
				&& (name.equalsIgnoreCase("and") || name.equalsIgnoreCase("or"));
	}
}
