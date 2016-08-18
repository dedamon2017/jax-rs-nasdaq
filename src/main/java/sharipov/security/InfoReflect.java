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
		private String arg;

		public Command(Object object, String methodName, String argument) {
			receiver = object;
			arg = argument;
			Class<? extends Object> cls = object.getClass();
			Class<? extends Object> argType = arg.getClass(); 
			try {
				action = cls.getMethod(methodName, argType);
			} catch (NoSuchMethodException exception) {
				throw new AppException(exception.getMessage());
			}
		}

		public Object execute() {
			try {
				return action.invoke(receiver, arg);
			} catch (IllegalAccessException exception) {
				throw new AppException(exception.getMessage());
			} catch (InvocationTargetException exception) {
				throw new AppException(exception.getMessage());
			}
		}
	}
}
