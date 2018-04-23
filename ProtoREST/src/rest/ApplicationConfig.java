package rest;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;



import java.security.Key;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@ApplicationPath("rest_example")
public class ApplicationConfig extends Application {

	private final Set<Class<?>> classes;
	private static final String KEY_DATA = "dkz45KZAH@#!!EF684pm";
	public static Key JWT_KEY = new SecretKeySpec(KEY_DATA.getBytes(), 0, 16, "AES");
	
	public ApplicationConfig() {
		HashSet<Class<?>> c = new HashSet<>();
		c.add(AuthenticationService.class);
		c.add(JWTTokenNeededFilter.class);
		c.add(UsersService.class);
		c.add(PandService.class);
		c.add(ParameterService.class);
		classes = Collections.unmodifiableSet(c);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}