import javax.inject.Inject;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("securities")
public class SecurityResource {
	@Inject
	private SecurityService securityService;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Security getSecurity(@PathParam("id") String ticker) {
		Security security = new Security();
		security.setPrice(securityService.getPrice(ticker));
		security.setTicker(ticker);
		return security;

	}
}
