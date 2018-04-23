package ejb;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ParameterManagementEJB implements ParameterManagementEJBLocal {
	@PersistenceContext(unitName = "Project2017-G4")
	private EntityManager em;
	
	@EJB
	private PandManagementEJBLocal pandEJB;
	
	@EJB
	private UserManagementEJBLocal userEJB;
	
	@Resource
	private SessionContext ctx;
	
	/**
	 * aantal van de parameter opvragen aan de hand van zijn id
	 * aantal is bijvoorbeeld het getal tot waar de score loopt: bvb van 1-10 of van 1-3
	 */
	@Override
	public int getAantal(String parameter){
		StringBuilder queryString= new StringBuilder ();
		int aantal=0;
		queryString.append("Select aantal from parameter where idParameter='").append(parameter).append("';");
		Query q=em.createNativeQuery(queryString.toString());
		if(q.getResultList().get(0)!=null){
		String aantalString=q.getResultList().get(0).toString();
		aantal=Integer.parseInt(aantalString);
		}
		
		return aantal;
	}
	
	/**
	 * type van de parameter opvragen aan de hand van zijn id
	 */
	@Override
	public String getType(String parameter){
		StringBuilder queryString= new StringBuilder ();
		System.out.println("de parameters 12345= "+parameter);
		queryString.append("Select type from parameter where idParameter='").append(parameter).append("';");
		Query q=em.createNativeQuery(queryString.toString());
		System.out.println("de string is" + q.getResultList().get(0).toString());
		String type=q.getResultList().get(0).toString();
		
		
		return type;
	}
	
	/**
	 * de beschrijving van de parameter opvragen aan de hand van zijn id
	 */
	@Override
	public String getBeschrijving(String parameter){
		StringBuilder queryString= new StringBuilder ();
		queryString.append("Select beschrijving from parameter where idParameter='").append(parameter).append("';");
		Query q=em.createNativeQuery(queryString.toString());
		String beschrijving=q.getResultList().get(0).toString();
		
		
		return beschrijving;
	}
	
	/**
	 * de naam van de parameter opvragen aan de hand van zijn id
	 */
	@Override
	public String getNaam(String parameter){
		StringBuilder queryString= new StringBuilder ();
		queryString.append("Select naam from parameter where idParameter='").append(parameter).append("';");
		Query q=em.createNativeQuery(queryString.toString());
		String naam=q.getResultList().get(0).toString();
		
		
		return naam;
	}

	/**
	 * een eenheid van een type parameter opvragen aan de hand van zijn id
	 */
	@Override
	public String getEenheid(String parameter){
		StringBuilder queryString= new StringBuilder ();
		queryString.append("Select eenheid from parameter where idParameter='").append(parameter).append("';");
		Query q=em.createNativeQuery(queryString.toString());
		String naam=q.getResultList().get(0).toString();
		
		
		return naam;
	}
	
	
}
