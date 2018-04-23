package ejb;

import javax.ejb.Local;

import entities.Templates;

@Local
public interface TemplatesManagementEJBLocal {

Templates findTemplateById(long idQuery);
	
	void toggleLengteVoorgevel();

	void toggleOppervlakte();

	void toggleParking();

	void toggleCommercieleActiviteit();

	void togglePubliekTransport();

	void toggleEducation();

	int getLengteVoorgevel();

	int getOppervlakte();

	int getParking();

	int getCommercieleActiviteit();

	int getPubliekTransport();

	int getEducation();

	void resetWaarden();

	int getBouwjaar();

	void toggleBouwjaar();

	String findStraat(int id);

	void toggleMicroToegankelijkheid();

	void toggleToegankelijkheid();

	void togglePassage();

	int getPassage();

	int getToegankelijkheid();

	int getMicroToegankelijkheid();

	void toggleShopAreaAppreciation();

	int getShopAppreciation();

	void toggeCorrectieFactor();

	int getCorrectiefactor();

	int getLokaalMonopolie();

	void toggeLokaalMonopolie();

	void setWaarden();

	
}

