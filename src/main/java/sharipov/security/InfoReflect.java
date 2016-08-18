package sharipov.security;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class InfoReflect {

	private Document document;

	public InfoReflect(Document document) {
		this.document = document;
	}

	public Element getElementsByClass(String string) {
		return document.getElementsByClass(string).first();
	}

	public Element getElementById(String string) {
		return document.getElementById(string);
	}

	public static class Command {
		private Object receiver;
		private Method action;
		private Object[] args;

		public Command(Object object, String methodName, Object[] arguments) throws NoSuchMethodException {
			receiver = object;
			args = arguments;
			Class<? extends Object> cls = object.getClass();
			Class[] argTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argTypes[i] = args[i].getClass();
			}
			action = cls.getMethod(methodName, argTypes);
			
		}

		public Object execute() throws IllegalAccessException, InvocationTargetException {
			return action.invoke(receiver, args);
		}
	}
}
