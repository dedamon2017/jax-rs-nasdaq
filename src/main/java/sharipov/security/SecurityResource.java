package sharipov.security;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("securities")
public class SecurityResource {

	private SecurityService securityService;

	@Inject
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Security getSecurity(@PathParam("id") String ticker) {
		return securityService.getSecurity(ticker);
	}
}
