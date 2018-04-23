package rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.UserManagementEJBLocal;
import entities.Person;

@Produces({MediaType.APPLICATION_JSON})
@Path("user_service")
public class UsersService {
	
	@EJB
	private UserManagementEJBLocal userEJB;
	
	private Person person=new Person();
	
	/**
	 * huidige user opvragen
	 * @param requestContext
	 * @return
	 */
	@JWTTokenNeeded
	@Produces({MediaType.APPLICATION_JSON})
    @POST @Consumes({MediaType.APPLICATION_JSON})
	@Path("/getUser")
	public Response getCurrentUser(ContainerRequestContext requestContext)
	{
        try {
        	Person p=new Person();
        	
            p = userEJB.findPerson(requestContext.getSecurityContext().getUserPrincipal().toString());
        	
            // Return the person on the response
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            		  .add("id", p.getId())
            		  .add("login", p.getLogin())
            		  .add("name", p.getName())
            		  .add("address", p.getAddress())
            		  .add("winkel",p.getWinkel())
            		  .add("token", p.getToken())
            		  .add("werkRegio", p.getRegioWerk())
            		  .add("group", p.getGroup());
            			
            
            JsonObject jsonObject= objectBuilder.build();
            
            return Response.ok(jsonObject,MediaType.APPLICATION_JSON ).header(AUTHORIZATION, "Bearer " + p.getToken()).build();
           
        } catch (Exception e) {
        
            return Response.status(UNAUTHORIZED).build();
        }
		
		
	 
		
		
	}
	
	/**
	 * user toevoegen aan databank
	 * @param login
	 * @param password
	 */
	@POST
	public void addUser(@QueryParam("login") String login, @QueryParam("password") String password)
	{
		
		person.sethPassword(password);
		person.setLogin(login);
		person.setGroup("Admin");
		userEJB.addUser(person);
		System.out.println("nieuwe gebruiker toegevoegd");
	
	}

}
