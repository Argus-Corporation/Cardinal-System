package net.argus.system;

import java.lang.annotation.Annotation;

public class AnnotationManager {
	
	public static <A extends Annotation> A getAnnotaion(String className, Class<A> cl) {
		try {return AnnotationManager.class.getClassLoader().loadClass(className).getAnnotation(cl);}
		catch(ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}
	
	public static <A extends Annotation> A getAnnotaion(Class<?> cl, Class<A> clA) {
		return getAnnotaion(cl.getName(), clA);
	}

}
