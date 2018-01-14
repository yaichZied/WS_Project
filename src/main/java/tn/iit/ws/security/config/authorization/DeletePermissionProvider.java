package tn.iit.ws.security.config.authorization;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanDelete;

@Service
public class DeletePermissionProvider {
	public boolean hasPermission(User user, Object object) {
		Class<?> cl = object.getClass();
		while (!cl.equals(Object.class)) {
			CanDelete canDelete = cl.getAnnotation(CanDelete.class);
			if (canDelete != null) {
				if (canDelete.value().length > 0) {
					String[] value = canDelete.value();
					Iterator<? extends GrantedAuthority> i = user.getAuthorities().iterator();
					while (i.hasNext()) {
						GrantedAuthority grantedAuthority = i.next();
						for (int j = 0; j < value.length; j++) {
							if (grantedAuthority.getAuthority().equalsIgnoreCase(value[j])) {
								return true;
							}
						}
					}
					return false;
				} else {
					return true;
				}
			}
			Method[] methods = cl.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].isAnnotationPresent(CanDelete.class) && (methods[i].getReturnType().equals(boolean.class)
						|| methods[i].getReturnType().equals(Boolean.class))) {
					Method method = methods[i];
					boolean accessible = method.isAccessible();
					method.setAccessible(true);
					Class<?>[] types = method.getParameterTypes();
					Object[] values = new Object[types.length];
					for (int j = 0; j < values.length; j++) {
						if (types[j].equals(User.class)) {
							values[j] = user;
						}
					}
					boolean result = false;
					try {
						result = (boolean) method.invoke(object, values);
					} catch (Exception e) {
						e.printStackTrace();
					}
					method.setAccessible(accessible);
					return result;
				}
			}
			cl = cl.getSuperclass();
		}
		return true;
	}
}