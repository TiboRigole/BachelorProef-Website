package rest;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ejb.ParameterManagementEJBLocal;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("parameter_service")
public class ParameterService {
	@EJB
	private ParameterManagementEJBLocal parameterEJB;
	
	/**
	 * alle eigenschappen van een bepaalde parameter opvragen
	 * @param parameter
	 * @return
	 */
	@Produces({MediaType.APPLICATION_JSON})
	@Path("eigenschappen")
	@GET
	public JsonObject getEigenschappen(@QueryParam("parameter") String parameter){
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("type", parameterEJB.getType(parameter))
					.add("eenheid", parameterEJB.getType(parameter) )
					.add("aantal", parameterEJB.getAantal(parameter))
					.add("beschrijving", parameterEJB.getBeschrijving(parameter))
					.add("naam", parameterEJB.getNaam(parameter));
		
		JsonObject eigenschappen=objectBuilder.build();
		
		return eigenschappen;
	}
}
