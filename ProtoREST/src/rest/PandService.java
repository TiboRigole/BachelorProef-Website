package rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;

import ejb.PandManagementEJBLocal;
import ejb.UserManagementEJBLocal;
import entities.Pand;
import entities.Panden;
import entities.Person;

//import org.apache.commons.io.IOUtils;
import org.apache.taglibs.standard.tag.common.*;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("pand_service")
public class PandService {
	
	@EJB
	private PandManagementEJBLocal pandEJB;
	
	List<Pand> panden= new ArrayList<Pand>();
	List<Pand> incompletePanden= new ArrayList<Pand>();
	ArrayList<Pand> pandjes= new ArrayList<Pand>();
	

	/**
	 * parameters horend bij pand opvragen
	 * @param pandId
	 * @return
	 */
	@GET
	@Path("parametersPand")
	@Produces({MediaType.APPLICATION_JSON})
	public JsonObject getParametersPandById(@QueryParam("pandId") long pandId)
	{
		ArrayList<String> noodzakelijkeParams=new ArrayList<>( pandEJB.getAlleNoodzakelijkeParametersById(pandId));
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		
		for(int i =0; i<noodzakelijkeParams.size(); i++){
					StringBuilder sb= new StringBuilder();
					sb.append("parameter").append(i);
					System.out.println(noodzakelijkeParams.get(i));
	       		  objectBuilder.add(sb.toString(), noodzakelijkeParams.get(i));
		}
	       		  
			 JsonObject jsonObject= objectBuilder.build();
	         
		
		return jsonObject;
		
		
		
		
	}
	
	/**
	 * pand updaten naar databank
	 * @param p
	 */
	@PUT
	@Path("updatePand")
	@Consumes({MediaType.APPLICATION_JSON})
	public void updatePand(Pand p){
		Pand bestaandPand= pandEJB.findPand(p.getId());
		byte[] afbeelding= bestaandPand.getAfbeelding();
		p.setAfbeelding(afbeelding);
		p.setLand(bestaandPand.getLand());
		pandEJB.updatePand(p);
	}
	
	/**
	 * alle panden horend bij een winkel opvragen
	 * @param winkel
	 * @return
	 */
	@GET
	@Path("winkelPanden")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pand> getPandenOpWinkel(@QueryParam("winkel") String winkel)
	{
		
		panden=new ArrayList<Pand>(pandEJB.findWinkelPanden(winkel));
		for(Pand p: panden){
			
			p.setAfbeelding(null);
		}
		return panden;
		
	}
	
	/**
	 * alle panden horend bij een winkel en regio opvragen
	 * @param regio
	 * @param winkel
	 * @return
	 */
	@GET
	@Path("regioPanden")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pand> getPandenOpRegio(@QueryParam("regio") String regio, @QueryParam("winkel") String winkel)
	{
		
		panden=new ArrayList<Pand>(pandEJB.findRegioPanden(regio, winkel));
		for(Pand p: panden){
			
			p.setAfbeelding(null);
		}
		return panden;
		
	}
	
	/**
	 * afbeelding van een pand updaten
	 * @param obj
	 * @param pandId
	 */
	@POST
	@Path("setAfbeelding")
	@Consumes({MediaType.APPLICATION_JSON})
	public void setAfbeeldingPand(JsonObject obj, @QueryParam("pandId") long pandId ){
		System.out.println("we persisten de afbeelding van pand met id nummer: "+pandId);
		Pand pand= pandEJB.findPand(pandId);
		
		byte[] afbeelding=DatatypeConverter.parseBase64Binary(obj.getString("afbeelding"));
		System.out.println(obj.get("afbeelding").toString());
		
		pand.setAfbeelding(afbeelding);
		pandEJB.updatePand(pand);
		
	}
	
	/**
	 * pand opvragen adhv id
	 * @param id
	 * @return
	 */
	@GET
	@Path("getPand")
	@Produces({MediaType.APPLICATION_JSON})
	public Pand getPandOpId(@QueryParam("id") long id)
	{
		
		
		Pand huidig= pandEJB.findPand(id);
		huidig.setAfbeelding(null);
		
		return huidig;
	}
	
	/**
	 * alle incomplete panden van een winkel opvragen
	 * @param winkel
	 * @return
	 */
	@GET
	@Path("incompleteWinkelPanden")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pand> getIncompletePandenOpWinkel(@QueryParam("winkel") String winkel)
	{
		System.out.println(winkel);
		incompletePanden=new ArrayList<Pand>(pandEJB.findIncompleteWinkelPanden(winkel));
		
		for(Pand x: incompletePanden){
			x.setAfbeelding(null);
		}
		
		
		return incompletePanden;
		
	}
	
	/**
	 * alle incomplete panden van een winkel in een bepaalde regio opvragen
	 * @param regio
	 * @param winkel
	 * @return
	 */
	@GET
	@Path("incompleteRegioPanden")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pand> getIncompletePandenOpRegio(@QueryParam("regio") String regio, @QueryParam("winkel") String winkel)
	{
		
		incompletePanden=new ArrayList<Pand>(pandEJB.findIncompleteRegioPanden(regio,winkel));
		
		for(Pand x: incompletePanden){
			x.setAfbeelding(null);
		}
		
		
		return incompletePanden;
		
	}
	
	/**
	 * afbeelding van een pand opvragen
	 * @param id
	 * @return in response zit Byte64 string van afbeelding
	 */
	@GET
	@Path("afbeeldingPand")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPandenOpWinkel(@QueryParam("id") long id)
	{
		Pand p= pandEJB.findPand(id);
		
		if(p.getAfbeelding()!=null){
		 JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
       		  .add("afbeelding", DatatypeConverter.printBase64Binary(p.getAfbeelding()));
		 JsonObject jsonObject= objectBuilder.build();
         
         return Response.ok(jsonObject,MediaType.APPLICATION_JSON ).build();
		}
		else{
			
				 
		         return null;
		        
			
		}
	}
	
	/**
	 * aantal incomplete panden (nummer)
	 * @param winkel
	 * @param regio
	 * @return
	 */
	@GET
	@Path("incompleted")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAantalIncompleetVanWinkelRegio(@QueryParam("winkel") String winkel, @QueryParam("regio") String regio){
		List<Pand> regioPanden=pandEJB.findRegioPanden(regio,winkel);
		int aantal=0;
		for(Pand p:regioPanden){
			if (p.getCompleted()==0){
				aantal++;
			}
		}
		
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
	       		  .add("aantal",aantal);
			 JsonObject jsonObject= objectBuilder.build();
	         
	         return Response.ok(jsonObject,MediaType.APPLICATION_JSON ).build();
			
		
	}
}
