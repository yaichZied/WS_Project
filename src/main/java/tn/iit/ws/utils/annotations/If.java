package tn.iit.ws.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface If {
	public String value();
	public boolean not() default false;
	public String operator() default "";
	public String operand() default "";
}
