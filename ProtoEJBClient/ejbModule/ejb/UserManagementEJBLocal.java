package ejb;

import java.util.List;

import javax.ejb.Local;

import entities.Person;
import entities.Person_Group;

@Local
public interface UserManagementEJBLocal {

	public Person findPerson(String login);
	
	public Person findPersonById(long id);
	
	public String getWinkel();
	
	public void addUser(Person person);
	
	//toegevoegd door tibo
	public String getNaam();
	
	//toegevoegd door tibo
	public List<Person> findAllUsers();
	
	public void resetPassword(Person person);
	
	public void updateUser(Person person);

	public String getId();

	public String getAdres();

	public String getLogin();

	public Person findSelf();

	public boolean comparePaswoord(String codeerPaswoord);

	public void sethPassWord(String nieuwPaswoord);

}
