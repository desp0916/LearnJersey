package learn.jersey.services.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/LogCount")
public class LogCount {

	@GET
	@Path("/TotalsForMinute/{timestamp}")
	@Produces("application/json")
	public String getMinuteTotals(@PathParam("timestamp") String timestamp) {


	}
}
