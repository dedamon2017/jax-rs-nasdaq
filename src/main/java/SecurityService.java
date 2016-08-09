import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@ApplicationScoped
public class SecurityService {
	private static final String URL_FORMAT = "http://www.nasdaq.com/symbol/%s/real-time";
	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";

	protected Document getDocument(String searchString) throws IOException {
		String url = String.format(URL_FORMAT, searchString);
		return Jsoup.connect(url).userAgent(userAgent).get();
	}

	public String getPrice(String ticker) {
		String price = "";
		try {
			Document nasdaqDocument = getDocument(ticker);
			Element element = nasdaqDocument.getElementsByClass("qwidget-dollar").first();
			price = element.text();
		} catch (Exception e) {
			throw new NotFoundException();
		}
		return price;
	}
}
