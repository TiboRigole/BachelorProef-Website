package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name="person")
public class Person implements Serializable{
	private static final long serialVersionUID = 4990525852036485337L;
	
	
	@Column(name="idPerson", nullable=false, length=16)
	private Long id;

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Person_Group person_group;
	
	@Id
	@Column(name="login")
	private String login;
	
	
	@Column(name="name", nullable=false)
	private String name;
	
	
	@Column(name="hPassword", nullable=false)
	private String hPassword;
	
	
	@Column(name="address", nullable=false)
	private String address;
	
	@Column(name="winkel", nullable=false)
	private String winkel;
	
	@Column(name="token")
	private String token;
	
	@Column(name="werkRegio")
	private String regioWerk;
	
		

	public Person(Long id, Person_Group person_group, String login, String name, String hPassword, String address,
			String winkel, String token, String regioWerk) {
		super();
		this.id = id;
		this.person_group = person_group;
		this.login = login;
		this.name = name;
		this.hPassword = hPassword;
		this.address = address;
		this.winkel = winkel;
		this.token = token;
		this.regioWerk=regioWerk;
	}

	public Person(Person person)
	{
		this.name = person.name;
		this.login = person.login;
		this.hPassword = person.hPassword;
		this.address = person.address;
		this.token=person.token;
		this.person_group=person.person_group;
		this.regioWerk=person.regioWerk;
	}
	
	public Person(String name) {
		super();
		this.name = name;
	}
	
	public Person(){
		super();
	}
	
	
	
	public String getToken() {
		return token;
	}




	public void setToken(String token) {
		this.token = token;
	}




	public Person_Group getPerson_group() {
		return person_group;
	}

	public String getGroup(){
		if(person_group!=null){
		return person_group.getGroup();
		}
		else{
			return "";
		}
	}
	
	public void setGroup(String group){
		if(person_group==null){
			person_group=new Person_Group();
			person_group.setGroup(group);
			person_group.setPerson(this);
			
		}
		else{
		person_group.setGroup(group);
		}
	}

	public void setPerson_group(Person_Group pg) {
		this.person_group = pg;
		
	}

	
	public void setRegioWerk(String regioWerk){
		this.regioWerk=regioWerk;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	public String gethPassword() {
		return hPassword;
	}

	public void sethPassword(String hPassword) {
		this.hPassword = hPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getWinkel() {
		return winkel;
	}

	public void setWinkel(String winkel) {
		this.winkel = winkel;
	}

	public String getRegioWerk() {
		// TODO Auto-generated method stub
		return regioWerk;
	}

	
}
