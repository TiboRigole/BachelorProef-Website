package entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;

@XmlRootElement
@Entity
@Table(name="templates")
public class Templates implements Serializable {
	private static final long serialVersionUID = 3125063178186144972L;


	// attributen van een template
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtemplates", nullable=false)
	private Long idtemplates;
	
	@Column(name="idPand", nullable=false)
	private Long idPand;
	
	@Column(name="lengtevoorgevel", nullable=false)
	private int lengtevoorgevel;
	
	@Column(name="oppervlakte", nullable=false)
	private int oppervlakte;
	
	@Column(name="parking", nullable=false)
	private int parking;
	
	@Column(name="commercieleActiviteit", nullable=false)
	private int commercieleActiviteit;
	
	@Column(name="publiekTransport", nullable=false)
	private int publiekTransport;

	@Column(name="education", nullable=false)
	private int education;

	@Column(name="bouwjaar", nullable=false)
	private int bouwjaar;
	
	@Column(name="passage", nullable=false)
	private int passage;
	
	@Column(name="toegankelijkheid", nullable=false)
	private int toegankelijkheid;
	
	@Column(name="microtoegankelijkheid", nullable=false)
	private int microtoegankelijkheid;
	
	@Column(name="shopareaappreciation", nullable=false)
	private int shopareaappreciation;
	
	@Column(name="correctiefactor",nullable=false)
	private int correctiefactor;
	
	@Column(name="lokaalmonopolie",nullable=false)
	private int lokaalmonopolie;

	public Templates(){
		super();
	}
	
	
	public Templates(Long idtemplates, Long idPand, int lengtevoorgevel, int oppervlakte, int parking,
			int commercieleActiviteit, int publiekTransport, int educationn, int bouwjaar, int passage, int toegankelijkheid, int microtoegankelijkheid,
			int shopareaappreciation, int correctiefactor, int lokaalmonopolie) {
		super();
		this.idtemplates = idtemplates;
		this.idPand = idPand;
		this.lengtevoorgevel = lengtevoorgevel;
		this.oppervlakte = oppervlakte;
		this.parking = parking;
		this.commercieleActiviteit = commercieleActiviteit;
		this.publiekTransport = publiekTransport;
		this.education = education;
		this.bouwjaar = bouwjaar;
		this.passage=passage;
		this.toegankelijkheid=toegankelijkheid;
		this.microtoegankelijkheid=microtoegankelijkheid;
		this.shopareaappreciation=shopareaappreciation;
		this.correctiefactor = correctiefactor;
		this.lokaalmonopolie=lokaalmonopolie;
	}
	
	//copyconstructor
	public Templates(Templates templates){
		this.idtemplates = templates.idtemplates;
		this.idPand = templates.idPand;
		this.lengtevoorgevel = templates.lengtevoorgevel;
		this.oppervlakte = templates.oppervlakte;
		this.parking = templates.parking;
		this.commercieleActiviteit = templates.commercieleActiviteit;
		this.publiekTransport = templates.publiekTransport;
		this.education = templates.education;
		this.bouwjaar=templates.bouwjaar;
		this.passage=templates.passage;
		this.toegankelijkheid= templates.toegankelijkheid;
		this.microtoegankelijkheid= templates.microtoegankelijkheid;
		this.shopareaappreciation=templates.shopareaappreciation;
		this.correctiefactor=templates.correctiefactor;
		this.lokaalmonopolie=templates.lokaalmonopolie;
	}

	//getters en setters
	
	public int getCorrectiefactor() {
		return correctiefactor;
	}


	public int getLokaalMonopolie() {
		return lokaalmonopolie;
	}


	public void setLokaalMonopolie(int lokaalmonopolie) {
		this.lokaalmonopolie = lokaalmonopolie;
	}


	public void setCorrectiefactor(int correctiefactor) {
		this.correctiefactor = correctiefactor;
	}


	public int getShopAreaAppreciation() {
		return shopareaappreciation;
	}


	public void setShopAreaAppreciation(int shopareaappreciation) {
		this.shopareaappreciation = shopareaappreciation;
	}


	public int getPassage() {
		return passage;
	}


	public void setPassage(int passage) {
		this.passage = passage;
	}


	public int getToegankelijkheid() {
		return toegankelijkheid;
	}


	public void setToegankelijkheid(int toegankelijkheid) {
		this.toegankelijkheid = toegankelijkheid;
	}


	public int getMicrotoegankelijkheid() {
		return microtoegankelijkheid;
	}


	public void setMicrotoegankelijkheid(int microtoegankelijkheid) {
		this.microtoegankelijkheid = microtoegankelijkheid;
	}


	public int getBouwjaar(){return bouwjaar;}
	public void setBouwjaar(int bouwjaar){this.bouwjaar=bouwjaar;}
	
	public Long getIdtemplates() {
		return idtemplates;
	}

	public void setIdtemplates(Long idtemplates) {
		this.idtemplates = idtemplates;
	}


	public Long getIdPand() {
		return idPand;
	}


	public void setIdPand(Long idPand) {
		this.idPand = idPand;
	}


	public int getLengtevoorgevel() {
		return lengtevoorgevel;
	}


	public void setLengtevoorgevel(int lengtevoorgevel) {
		this.lengtevoorgevel = lengtevoorgevel;
	}


	public int getOppervlakte() {
		return oppervlakte;
	}


	public void setOppervlakte(int oppervlakte) {
		this.oppervlakte = oppervlakte;
	}


	public int getParking() {
		return parking;
	}


	public void setParking(int parking) {
		this.parking = parking;
	}


	public int getCommercieleActiviteit() {
		return commercieleActiviteit;
	}


	public void setCommercieleActiviteit(int commercieleActiviteit) {
		this.commercieleActiviteit = commercieleActiviteit;
	}


	public int getPubliekTransport() {
		return publiekTransport;
	}


	public void setPubliekTransport(int publiekTransport) {
		this.publiekTransport = publiekTransport;
	}


	public int getEducation() {
		return education;
	}


	public void setEducation(int education) {
		this.education = education;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}


