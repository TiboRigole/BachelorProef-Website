package ejb;

import javax.ejb.Local;


@Local
public interface ParameterManagementEJBLocal {

	
	public String getType(String parameter);
	
	public int getAantal(String parameter);
	
	public String getBeschrijving(String parameter);
	
	public String getNaam(String parameter);

	public String getEenheid(String parameter);
}
