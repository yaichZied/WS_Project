package tn.iit.ws.security.config.authorization;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.security.config.authorization.annotations.CanAdd;

@Service
public class AddPermissionProvider {
	public boolean hasPermission( User user, Object clazz){
		Class<?> cl = (Class<?>) clazz;
		while (!cl.equals(Object.class)) {
			CanAdd canAdd = cl.getAnnotation(CanAdd.class);
			if(canAdd!=null) {
				if(canAdd.value().length>0) {
					String[] value = canAdd.value();
					Iterator<? extends GrantedAuthority> i = user.getAuthorities().iterator();
					while (i.hasNext()) {
						GrantedAuthority grantedAuthority = i.next();
						for (int j = 0; j < value.length; j++) {
							if(grantedAuthority.getAuthority().equalsIgnoreCase(value[j])) {
								return true;
							}
						}
					}
					return false;
				}
				else {
					return true;
				}
			}
			Method[] methods = cl.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if(methods[i].isAnnotationPresent(CanAdd.class)
						&&(methods[i].getReturnType().equals(boolean.class)||methods[i].getReturnType().equals(Boolean.class))) {
					Method method = methods[i];
					boolean accessible = method.isAccessible();
					method.setAccessible(true);
					Class<?>[] types = method.getParameterTypes();
					Object[] values = new Object[types.length];
					for (int j = 0; j < values.length; j++) {
						if(types[j].equals(User.class)) {
							values[j] = user;
						}
					}
					boolean result = false;
					try {
						result = (boolean)method.invoke(null, values);
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
