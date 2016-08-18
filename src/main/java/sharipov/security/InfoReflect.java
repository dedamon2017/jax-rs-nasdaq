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

	static public class Command {
		private Object receiver;
		private Method action;
		private Object[] args;

		public Command(Object object, String methodName, Object[] arguments) {
			receiver = object;
			args = arguments;
			Class<? extends Object> cls = object.getClass();
			
			Class[] argTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++)
				argTypes[i] = args[i].getClass();
			try {
				action = cls.getMethod(methodName, argTypes);
			} catch (NoSuchMethodException exception) {
				throw new AppException(exception.getMessage());
			}
		}

		public Object execute() {
			try {
				return action.invoke(receiver, args);
			} catch (IllegalAccessException exception) {
				throw new AppException(exception.getMessage());
			} catch (InvocationTargetException exception) {
				throw new AppException(exception.getMessage());
			}
		}
	}
}
