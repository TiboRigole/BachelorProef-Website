package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import ejb.TemplatesManagementEJBLocal;
import entities.Templates;

/**
 * Bean die alles ivm de templates in de databank zal voorzien
 */
@ViewScoped
@Named("templatesBean")
@ManagedBean(name="templatesBean")
public class TemplatesBean implements Serializable {
	private static final long serialVersionUID = -3737221385235612830L;

	// CLASS ATTRIBUTES
	public Templates template;
	int winkelId;
	
	
	//DATABASE
	@EJB
	private TemplatesManagementEJBLocal templatesEJB;
	
	//CLASS METHODS
	/**
	 * het getal dat het id van het pand voorstelt uit de url,
	 * laadt de template van het overeenkomstig pand met deze id
	 * 
	 * @return de template van het overeenkomstig pand
	 */
	public Templates getTemplateById(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		winkelId = Integer.parseInt(request.getParameter("Pand").toString());
		long idQuery = Long.valueOf(winkelId);
		template = templatesEJB.findTemplateById(idQuery);
		return template;
	}
	
	/**
	 * methode die alle parameters van een bepaald pand op 0 zal zetten
	 * keert terug naar dezelfde pagina waar de methode gebruikt wordt
	 */
	public void resetWaarden(){
		templatesEJB.resetWaarden();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * methode die alle parameters van een bepaald pand op 1 zal zetten
	 * keert terug naar dezelfde pagina waar de methode gebruikt wordt
	 * 
	 */
	public void setWaarden(){
		templatesEJB.setWaarden();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * getters
	 * @return vraagt de waarde op van de huidige parameter in de databank
	 */
	public String getLengteVoorgevel(){
		int a = templatesEJB.getLengteVoorgevel();
		return a+"";
	}

	public String getOppervlakte(){
		int a = templatesEJB.getOppervlakte();
		return a+"";
	}
	
	public String getParking(){
		int a = templatesEJB.getParking();
		return a+"";
	}
	
	public String getCommercieleActiviteit(){
		int a = templatesEJB.getCommercieleActiviteit();
		return a+"";
	}
	
	public String getPubliekTransport(){
		int a = templatesEJB.getPubliekTransport();
		return a+"";
	}
	
	public String getEducation(){
		int a = templatesEJB.getEducation();
		return a+"";
	}
	
	public String getBouwjaar(){
		int a = templatesEJB.getBouwjaar();
		return a+"";
	}
	
	public String getStraat(){
		int id = winkelId;
		return templatesEJB.findStraat(id);
	}
	
	public String getPassage(){
		int a = templatesEJB.getPassage();
		return a+"";
	}

	public String getToegankelijkheid(){
		int a = templatesEJB.getToegankelijkheid();
		return a+"";
	}

	public String getMicroToegankelijkheid(){
		int a = templatesEJB.getMicroToegankelijkheid();
		return a+"";
	}
	
	public String getShopAreaAppreciation(){
		int a = templatesEJB.getShopAppreciation();
		return a+"";
	}

	public String getCorrectiefactor(){
		int a = templatesEJB.getCorrectiefactor();
		return a+"";
	}
	
	public String getLokaalMonopolie(){
		int a = templatesEJB.getLokaalMonopolie();
		return a+"";
	}
	
	/**
	 * toggle methoden
	 * zet de parameter op 1 als hij op 0 stond, of op 0 als hij op 1 stond
	 * keert dan terug naar dezelfde pagina waar de knop staat
	 */
	public void toggleLengteVoorgevel(){	
		templatesEJB.toggleLengteVoorgevel();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toggleOppervlakte(){
		templatesEJB.toggleOppervlakte();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void toggleParking(){
		templatesEJB.toggleParking();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void toggleCommercieleActiviteit(){
		templatesEJB.toggleCommercieleActiviteit();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void togglePubliekTransport(){
		templatesEJB.togglePubliekTransport();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toggleEducation(){
		templatesEJB.toggleEducation();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toggleBouwjaar(){
		templatesEJB.toggleBouwjaar();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void togglePassage(){
		templatesEJB.togglePassage();
		StringBuilder sb = new StringBuilder();
		sb.append("modifyParameters_pand.faces?Pand=");
		sb.append(winkelId);
		String a = sb.toString();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}

	public void toggleToegankelijkheid(){
			templatesEJB.toggleToegankelijkheid();
			StringBuilder sb = new StringBuilder();
			sb.append("modifyParameters_pand.faces?Pand=");
			sb.append(winkelId);
			String a = sb.toString();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}

	public void toggleMicroToegankelijkheid(){
			templatesEJB.toggleMicroToegankelijkheid();
			StringBuilder sb = new StringBuilder();
			sb.append("modifyParameters_pand.faces?Pand=");
			sb.append(winkelId);
			String a = sb.toString();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
	public void toggleShopAreaAppreciation(){
			templatesEJB.toggleShopAreaAppreciation();
			StringBuilder sb = new StringBuilder();
			sb.append("modifyParameters_pand.faces?Pand=");
			sb.append(winkelId);
			String a = sb.toString();

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	public void toggleCorrectiefactor(){
			templatesEJB.toggeCorrectieFactor();
			StringBuilder sb = new StringBuilder();
			sb.append("modifyParameters_pand.faces?Pand=");
			sb.append(winkelId);
			String a = sb.toString();

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	public void toggleLokaalMonopolie(){
			templatesEJB.toggeLokaalMonopolie();
			StringBuilder sb = new StringBuilder();
			sb.append("modifyParameters_pand.faces?Pand=");
			sb.append(winkelId);
			String a = sb.toString();

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(a);
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}





}
