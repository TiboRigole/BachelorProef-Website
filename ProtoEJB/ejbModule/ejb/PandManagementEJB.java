package ejb;

import javax.ejb.Stateless;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;


import entities.Pand;
import entities.Person;

// implementeert interface PandManagementEJBLocal
@Stateless
public class PandManagementEJB implements PandManagementEJBLocal{
	
	//entity manager wordt geimplementeerd met... unitName moet overeenkomen met deze die gedefinieert staat in de persistence.xml
	@PersistenceContext(unitName = "Project2017-G4")
	private EntityManager em;
	
	@EJB
	private PandManagementEJBLocal pandEJB;
	
	@EJB
	private UserManagementEJBLocal userEJB;
	
	@Resource
	private SessionContext ctx;
	
	
	//defaultconstructor
	public PandManagementEJB(){
		
	}

	/**
	 * geeft alle panden weer van een bepaalde winkel, in een bepaalde provincie
	 * geeft deze panden weer in een lijst van panden
	 */
	@Override
	public List<Pand> findRegioPanden(String regio, String winkel){
		String r;
		String w;
		
		if(regio==null || winkel==null){
			String naam=ctx.getCallerPrincipal().getName(); 
			Person p=userEJB.findPerson(naam);
			r=p.getRegioWerk();
			w=p.getWinkel();
		}else{
			r= regio;
			w=winkel;
		}
		
		Query q = em.createQuery("SELECT p FROM Pand p WHERE p.provincie=:regio AND p.winkel=:winkel");
		q.setParameter("regio", r);
		q.setParameter("winkel", w);
		List<Pand> panden=q.getResultList();
		return panden;
	}
	
	/**
	 * geeft alle panden weer van een bepaalde winkel
	 * geeft ze weer in een lijst van panden
	 */
	@Override
	public List<Pand> findWinkelPanden(String winkel){
		String w;
		
		if(winkel==null){
			String naam=ctx.getCallerPrincipal().getName(); 
			Person p=userEJB.findPerson(naam);
			w=p.getWinkel();
		}else{
			w= winkel;
		}
		
		Query q = em.createQuery("SELECT p FROM Pand p WHERE p.winkel=:winkel");
		q.setParameter("winkel", w);
		List<Pand> panden=q.getResultList();
		return panden;
	}
	
	/**
	 * vindt alle panden van een bepaalde winkel weer die incompleet zijn
	 * geeft een lijst van panden weer
	 */
	@Override
	public List<Pand> findIncompleteWinkelPanden(String winkel){
		String w;
		
		if(winkel==null){
			String naam=ctx.getCallerPrincipal().getName(); 
			Person p=userEJB.findPerson(naam);
			w=p.getWinkel();
		}else{
			w= winkel;
		}
		
		Query q = em.createQuery("SELECT p FROM Pand p WHERE p.winkel=:winkel AND p.completed=0");
		q.setParameter("winkel", w);
		List<Pand> panden=q.getResultList();
		return panden;
	}
	
	/**
	 * zelfde als hierboven, maar binnen een bepaalde regio
	 */
	@Override
	public List<Pand> findIncompleteRegioPanden(String regio, String winkel){
		String w;
		String r;
		if(winkel==null){
			String naam=ctx.getCallerPrincipal().getName(); 
			Person p=userEJB.findPerson(naam);
			w=p.getWinkel();
			r=p.getRegioWerk();
		}else{
			w= winkel;
			r=regio;
		}
		
		Query q = em.createQuery("SELECT p FROM Pand p WHERE p.provincie=:regio AND p.winkel=:winkel AND p.completed=0");
		q.setParameter("winkel", w);
		q.setParameter("regio", r);
		List<Pand> panden=q.getResultList();
		return panden;
	}
	
	/**
	 * geeft alle parameters van de pand DB weer in een lijst van strings
	 * dit zijn dus verschillende zaken die ingevuld moeten worden
	 */
	@Override
	public List<String> getAlleParameters(){
		Query q= em.createNativeQuery("SELECT c.COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS c WHERE TABLE_NAME='templates' and NOT c.COLUMN_NAME='idtemplates' and NOT c.COLUMN_NAME='idPand'");
		List<String> params=q.getResultList();
		return params;
	}
	
	/**
	 * geeft alle noodzakelijke parameters weer
	 * een parameters is noodzakelijk als ze bij de templatesDB op 1 staat, voor het overeenkomstig pand
	 */
	@Override
	public ArrayList<String> getAlleNoodzakelijkeParameters(Pand pand){
		
		// id van huidige pand
		Long id= pand.getId();
		List<String> alleParams=getAlleParameters();
		
		ArrayList<String> nodigeParams=new ArrayList<String>();
		StringBuilder query= new StringBuilder();
		
		
		for(String parameter: alleParams){
			//maken van query met huidige parameter
			query.append("SELECT ").append(parameter+" ").append("FROM templates WHERE idPand=").append(id);
			
			Query q= em.createNativeQuery(query.toString());
			
			//als de value 1 is wordt de parameter toegevoegd aan de lijst met nodigeParams
			if(q.getResultList().size()!=0){
				if(q.getResultList().get(0).equals("1")){
					nodigeParams.add(parameter);
				}
			}
			
			// query initializeren op "" 
			query.setLength(0);
		}
		
		return nodigeParams;
	}
	
	/**
	 * geeft alle parameter types weer, ook als deze op 0 staan voor het overeenkomstig pand
	 */
	@Override
	public List<String> getAlleParamTypes(){
		Query q= em.createNativeQuery("SELECT c.DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS c WHERE TABLE_NAME='pand' and NOT c.COLUMN_NAME='straat' and NOT c.COLUMN_NAME='idPand' and NOT c.COLUMN_NAME='provincie' and NOT c.COLUMN_NAME='postcode' and NOT c.COLUMN_NAME='land' and NOT c.COLUMN_NAME='winkel' and NOT c.COLUMN_NAME='stad'");
		List<String> paramTypes=q.getResultList();
		return paramTypes;
	}
	
	/**
	 * zoekt alle noodzakelijke parameters op van een pand
	 * dus alle parameters die op 1 staan
	 */
	@Override
	public List<String> getAlleNoodzParamTypes(Pand pand){
				// id van huidige pand
				Long id= pand.getId();
				List<String> alleParams=getAlleParameters();
				List<String> paramTypes=getAlleParamTypes();
				
				List<String> nodigeParams=getAlleNoodzakelijkeParameters(pand);	
				ArrayList<String> nodigeParamTypes=new ArrayList<String>();
				
				for(String s: nodigeParams){
					
					if(alleParams.contains(s)){
						int index= alleParams.indexOf(s);
						nodigeParamTypes.add(paramTypes.get(index));
					}
				}
				
				
				return nodigeParamTypes;
	}
	
	
	
	/**
	 * pand aanmaken in database
	 */
	@Override
	public void createPand(Pand pand){
		em.persist(pand);
	};
	
	/**
	 * updaten van een pand
	 * zorgen dat het in de databank ook aangepast is
	 */
	@Override
	public void updatePand(Pand p) {
		em.merge(p);
	}
	
	/**
	 *  pand verwijderen in database
	 */
	public void deletePand(Pand pand){
		// controleren of het pand bestaat
		if (!em.contains(pand)) {
		    pand = em.merge(pand);
		}
		
		// effectief pand verwijderen
		em.remove(pand);
		
	};
	
	/**
	 * pand zoeken op id in database 
	 */
	@Override
	public Pand findPand(Long id){
		return em.find(Pand.class, id);
	};
	
	/**
	 * lijst met alle panden via query opvragen
	 */
	@Override
	public List<Pand> getAllePanden(){
		Query q=em.createQuery("SELECT t FROM Pand t ");
		List<Pand> panden=q.getResultList();
		return panden;
	}

	/**
	 * geeft alle noodzakelijke parameters van een pand, dat gezocht wordt op id
	 * returnt ze in een lijst van strings
	 */
	@Override
	public List<String> getAlleNoodzakelijkeParametersById(long pandId) {
		 // id van huidige pand
        Long id= pandId;
        List<String> alleParams=getAlleParameters();
         
        ArrayList<String> nodigeParams=new ArrayList<String>();
        StringBuilder query= new StringBuilder();
         
         
        for(String parameter: alleParams){
            //maken van query met huidige parameter
            query.append("SELECT ").append(parameter+" ").append("FROM templates WHERE idPand=").append(id);
             
            Query q= em.createNativeQuery(query.toString());
             
            //als de value 1 is wordt de parameter toegevoegd aan de lijst met nodigeParams
            if(q.getResultList().size()!=0){
                if(q.getResultList().get(0).equals("1")){
                    nodigeParams.add(parameter);
                }
            }
             
             
            // query initializeren op "" 
            query.setLength(0);
        }
         
        return nodigeParams;
	}

	/**
	 * opslaan van de afbeelding van een pand
	 * @param valueOf is de id van het pand
	 * @param image is de byte array, het formaat waarmee het wordt opgeslaan in de DB
	 */
	@Override
	public void setAfbeelding(Long valueOf, byte[] image) {
		Pand p = pandEJB.findPand(valueOf);
		p.setAfbeelding(image);
		em.merge(p);
	};
}