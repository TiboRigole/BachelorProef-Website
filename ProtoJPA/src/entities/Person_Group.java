package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="person_group")
public class Person_Group {
	
	/* @Id
	 * @JoinColumn(name = "login")
	 * Person person;
	 */
	@Column(name="idGroup")
	private String group;
	
	
	
	
	@Id
	@JoinColumn(name="login")
	private Person person;
	
	
	
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	

}
