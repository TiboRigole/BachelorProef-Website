package ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entities.Templates;

@Stateless
public class TemplatesManagementEJB implements TemplatesManagementEJBLocal {

	@PersistenceContext(unitName="Project2017-G4")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;
	
	@EJB
	private TemplatesManagementEJBLocal templatesEJB;
	
	private Templates template;
    /**
     * Default constructor. 
     */
    public TemplatesManagementEJB() {
        // TODO Auto-generated constructor stub
    }
	
    
    //hier volgen de andere methoden
	public Templates findTemplateById(long id){
		Query q = em.createQuery("SELECT p FROM Templates p WHERE p.idPand =:id");
		q.setParameter("id", id);
		List<Templates> templates = q.getResultList();
		
		if(templates.size() == 1){
			template = templates.get(0);
			return templates.get(0);
		}
		else{ return null;}
	};
	
	@Override
	public void resetWaarden(){
		template.setLengtevoorgevel(0);
		template.setOppervlakte(0);
		template.setParking(0);
		template.setCommercieleActiviteit(0);
		template.setPubliekTransport(0);
		template.setEducation(0);
		template.setBouwjaar(0);
		template.setPassage(0);
		template.setToegankelijkheid(0);
		template.setMicrotoegankelijkheid(0);
		template.setShopAreaAppreciation(0);
		template.setCorrectiefactor(0);
		template.setLokaalMonopolie(0);
		em.merge(template);
	};
	
	@Override
	public void setWaarden() {
		template.setLengtevoorgevel(1);
		template.setOppervlakte(1);
		template.setParking(1);
		template.setCommercieleActiviteit(1);
		template.setPubliekTransport(1);
		template.setEducation(1);
		template.setBouwjaar(1);
		template.setPassage(1);
		template.setToegankelijkheid(1);
		template.setMicrotoegankelijkheid(1);
		template.setShopAreaAppreciation(1);
		template.setCorrectiefactor(1);
		template.setLokaalMonopolie(1);
		em.merge(template);
	}
	
	@Override
	public void toggleLengteVoorgevel(){
		if (template.getLengtevoorgevel()==1){template.setLengtevoorgevel(0);}
		else{template.setLengtevoorgevel(1);}
		em.merge(template);
	}
	
	@Override
	public void toggleOppervlakte(){
		if (template.getOppervlakte()==1){template.setOppervlakte(0);}
		else{template.setOppervlakte(1);}
		em.merge(template);
	}
	
	@Override
	public void toggleParking(){
		if (template.getParking()==1){template.setParking(0);}
		else{template.setParking(1);}
		em.merge(template);
	}
	
	@Override
	public void toggleCommercieleActiviteit(){
		if (template.getCommercieleActiviteit()==1){template.setCommercieleActiviteit(0);}
		else{template.setCommercieleActiviteit(1);}
		em.merge(template);
	};
	
	@Override
	public void togglePubliekTransport(){
		if (template.getPubliekTransport()==1){template.setPubliekTransport(0);}
		else{template.setPubliekTransport(1);}
		em.merge(template);
	}

	
	@Override
	public void toggleEducation(){
		if (template.getEducation()==1){template.setEducation(0);}
		else{template.setEducation(1);}
		em.merge(template);
	}

	@Override
	public int getLengteVoorgevel(){
		return template.getLengtevoorgevel();
	};

	@Override
	public int getOppervlakte() {
		return template.getOppervlakte();
	}

	@Override
	public int getParking() {
		return template.getParking();
	}


	@Override
	public int getCommercieleActiviteit() {
		return template.getCommercieleActiviteit();
	}


	@Override
	public int getPubliekTransport() {
		return template.getPubliekTransport();
	}


	@Override
	public int getEducation() {
		return template.getEducation();
	}
	
	@Override
	public int getBouwjaar() {
		return template.getBouwjaar();
	}

	@Override
	public void toggleBouwjaar(){
		if (template.getBouwjaar()==1){template.setBouwjaar(0);}
		else{template.setBouwjaar(1);}
		em.merge(template);
	}
	
	@Override
	public String findStraat(int id){
		StringBuilder sb = new StringBuilder();
		sb.append("select straat from pand where idPand=");
		sb.append(id);
		sb.append(";");
		Query q = em.createNativeQuery(sb.toString());
		String straat =q.getResultList().get(0).toString();
		return straat;
	}

	@Override
	public void toggleMicroToegankelijkheid() {
		if (template.getMicrotoegankelijkheid()==1){template.setMicrotoegankelijkheid(0);}
		else{template.setMicrotoegankelijkheid(1);}
		em.merge(template);
		
	}



	@Override
	public void toggleToegankelijkheid() {
		if (template.getToegankelijkheid()==1){template.setToegankelijkheid(0);}
		else{template.setToegankelijkheid(1);}
		em.merge(template);
		
	}



	@Override
	public void togglePassage() {
		if (template.getPassage()==1){template.setPassage(0);}
		else{template.setPassage(1);}
		em.merge(template);
	}


	@Override
	public int getPassage() {
		return template.getPassage();
	}


	@Override
	public int getToegankelijkheid() {
		return template.getToegankelijkheid();
	}


	@Override
	public int getMicroToegankelijkheid() {
		return template.getMicrotoegankelijkheid();
	}


	@Override
	public void toggleShopAreaAppreciation() {
		if (template.getShopAreaAppreciation()==1){template.setShopAreaAppreciation(0);}
		else{template.setShopAreaAppreciation(1);}
		em.merge(template);
	}


	@Override
	public int getShopAppreciation() {
		return template.getShopAreaAppreciation();
	}


	@Override
	public void toggeCorrectieFactor() {
		if (template.getCorrectiefactor()==1){template.setCorrectiefactor(0);}
		else{template.setCorrectiefactor(1);}
		em.merge(template);	
	}


	@Override
	public int getCorrectiefactor() {
		return template.getCorrectiefactor();
	}


	@Override
	public int getLokaalMonopolie() {
		return template.getLokaalMonopolie();
	}


	@Override
	public void toggeLokaalMonopolie() {
		if (template.getLokaalMonopolie()==1){template.setLokaalMonopolie(0);}
		else{template.setLokaalMonopolie(1);}
		em.merge(template);	
	}
	
}


