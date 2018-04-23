package ejb;

import javax.ejb.Local;
import entities.Pand;

import java.util.Collection;
import java.util.List;

// dient als interface voor PandManagementEJB

@Local
public interface PandManagementEJBLocal {
	
	// copyconstructor
	public void createPand(Pand pand);
	
	// pand zoeken op id
	public Pand findPand(Long id);
	
	// lijst met alle panden opvragen
	public List<Pand> getAllePanden();
	
	// een pand verwijderen
	public void deletePand(Pand pand);
	
	// een pand updaten
	public void updatePand(Pand pand);
	
	// alle panden van bepaalde winkel geven
	public List<Pand> findWinkelPanden(String winkel);
	
	public List<String> getAlleParameters();
	
	public List<String> getAlleNoodzakelijkeParameters(Pand pand);
	
	public List<String> getAlleParamTypes();
	
	public List<String> getAlleNoodzParamTypes(Pand pand);

	public List<Pand> findRegioPanden(String regio,String winkel);

	public List<Pand> findIncompleteWinkelPanden(String winkel);

	public List<Pand> findIncompleteRegioPanden(String regio, String winkel);

	public List<String> getAlleNoodzakelijkeParametersById(long pandId);

	public void setAfbeelding(Long valueOf, byte[] image);


}

