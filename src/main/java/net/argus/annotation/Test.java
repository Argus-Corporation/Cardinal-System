package net.argus.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Cardinal
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	
	String info();
	
}
