package tn.iit.ws.security.config.authorization;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tn.iit.ws.entities.users.User;
import tn.iit.ws.repositories.UserRepository;
@Service
public class BaseAuthorizationProvider implements PermissionEvaluator{

	@Autowired
	private AddPermissionProvider addPermissionProvider;
	@Autowired
	private ReadPermissionProvider readPermissionProvider;
	@Autowired
	private ClassReadPermissionProvider classReadPermissionProvider;
	@Autowired
	private DeletePermissionProvider deletePermissionProvider;
	@Autowired
	private EditPermissionProvider editPermissionProvider;
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if(!authentication.isAuthenticated())
			return false;
		User user = userRepository.findByUsername(authentication.getName());
		if(user==null)
			return false;
		if(targetDomainObject.getClass().equals(Class.class)) {
			if(!classReadPermissionProvider.hasPermission(user, targetDomainObject))
				return false;
		}
		else {
			if(!classReadPermissionProvider.hasPermission(user, targetDomainObject.getClass()))
				return false;
		}
		
		switch (permission.toString()) {
		case "CLASS_READ":
			return true;
		case "READ":
			return readPermissionProvider.hasPermission(user, targetDomainObject);
		case "EDIT":
			return editPermissionProvider.hasPermission(user, targetDomainObject);
		case "ADD":
			return addPermissionProvider.hasPermission(user, targetDomainObject.getClass());
		case "DELETE":
			return deletePermissionProvider.hasPermission(user, targetDomainObject);

		default:
			return false;
		}
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		
		return false;
	}

}
