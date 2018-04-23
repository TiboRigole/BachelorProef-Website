package rest;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.DatatypeConverter;

import entities.Person;
import ejb.UserManagementEJBLocal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Produces({MediaType.APPLICATION_JSON})
@Path("/authentication")
public class AuthenticationService {
	
	@EJB
	private UserManagementEJBLocal userEJB;

	/**
	 * authenticeren van user en teruggeven user indien succesvol
	 * @param login
	 * @param password
	 * @return eigenschappen van user in User object
	 */
	@Produces({MediaType.APPLICATION_JSON})
    @POST @Consumes({MediaType.APPLICATION_JSON})
    @Path("/login")
    public Response authenticateUser(@QueryParam("login") String login, @QueryParam("password") String password) {
		
        try {
            // Authenticate the user using the credentials provided
            Person p = authenticate(login, password);
            // Issue a token for the user
            String token = issueToken(p);
            // Return the token on the response
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            		  .add("id", p.getId())
            		  .add("login", p.getLogin())
            		  .add("token", token)
            		  .add("group", p.getGroup());
            JsonObject jsonObject= objectBuilder.build();
            return Response.ok(jsonObject,MediaType.APPLICATION_JSON ).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }

    }
    
	/**
	 * token naar databank
	 * @param login
	 * @param token
	 */
	@POST
	@Path("/token")
    public void setToken(@QueryParam("login") String login, @QueryParam("token") String token) {
		
			System.out.println("login: "+login);
            System.out.println("tokentje: " +token);
            Person p = userEJB.findPerson(login);
            p.setToken(token);
            userEJB.updateUser(p);


    }
	
	
	/**
	 * token van user opvragen
	 * @param login
	 * @return
	 */
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/token")
    public Response getToken(@QueryParam("login") String login) {

		try{
        Person p = userEJB.findPerson(login);
		
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
      		  .add("login", p.getLogin())
      		  .add("token", p.getToken());
      		  
			JsonObject jsonObject= objectBuilder.build();
			
			return Response.ok(jsonObject,MediaType.APPLICATION_JSON ).build();
		}
		catch(Exception e){
			return Response.status(UNAUTHORIZED).build();
		}


    }
	
	/**
	 * effectieve authenticatie van user
	 * @param login
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Produces({MediaType.APPLICATION_JSON})
    private Person authenticate(String login, String password) throws Exception {
    	
    	Person person= new Person();
    	try{
    	person = userEJB.findPerson(login);
    	}
    	catch(Exception e){
    	e.printStackTrace();
    	}
    	MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		byte[] passwordDigest = md.digest();
		String pHash = DatatypeConverter.printBase64Binary(passwordDigest);
		
		if(person.gethPassword().equals(pHash)){
			return person;
		}else{
			throw new SecurityException("Invalid user/password");
		}
		          
    }

	@Produces({MediaType.APPLICATION_JSON})
    private String issueToken(Person person) {
    	Date curDate = new Date();
    	Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DATE, 2);
        
        Key key = ApplicationConfig.JWT_KEY;
        String jwtToken = Jwts.builder()
                .setId(person.getLogin())
                .setSubject(person.getGroup())
                .setIssuer("http://localhost:8080/ProtoREST/rest_example/")
                .setIssuedAt(curDate)
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return jwtToken;

    }
}
