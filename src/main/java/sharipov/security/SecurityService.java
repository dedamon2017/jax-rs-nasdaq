package sharipov.security;

import java.io.IOException;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@ApplicationScoped
public class SecurityService {
	private static final String URL_FORMAT = "http://www.nasdaq.com/symbol/%s/real-time";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
	private static final String PRICE_CLASS = "qwidget-dollar";
	private static final String COMPANY_ID = "qwidget_pageheader";
	private static final String DATE_ID = "qwidget_markettime";
	private static final String GET_ELEMENT_BY_ID = "getElementById";
	private static final String GET_ELEMENT_BY_CLASS = "getElementByClass";
	
	private Document document;
	private Element element;
	private Security security;

	protected Document getDocument(String searchString) throws IOException {
		String url = String.format(URL_FORMAT, searchString);
		return Jsoup.connect(url).userAgent(USER_AGENT).get();
	}

	public String getInfo(String elementName, String methodName) {
		element = (Element) new InfoReflect.Command(new InfoReflect(document), methodName, new String[]{elementName}).execute();
		return Objects.requireNonNull(element.text());
	}

	public Security getSecurity(String ticker) {
		try {
			document = getDocument(ticker);
		} catch (IOException e) {
			throw new AppException("Could not parse documnet.");
		}
		security = new Security();
		security.setCompany(getInfo(COMPANY_ID, GET_ELEMENT_BY_ID));
		security.setPrice(getInfo(PRICE_CLASS, GET_ELEMENT_BY_CLASS));
		security.setDate(getInfo(DATE_ID, GET_ELEMENT_BY_ID));
		security.setTicker(ticker);
		return security;
	}
}
