package entities;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


import java.sql.*;
import java.util.List;


@XmlRootElement
@Entity
@Table(name="pand")
@SecondaryTable(name = "templates", pkJoinColumns = @PrimaryKeyJoinColumn(name = "idPand", referencedColumnName = "idPand"))
public class Pand implements Serializable {
	private static final long serialVersionUID = 3125063178186144972L;

	// attributen van een pand
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idPand", nullable=false)
	private long id;
	
	@Column(name="straat", nullable=false)
	private String straat;

	@Column(name="land", nullable=false)
	private String land;

	@Column(name="postcode", nullable=false)
	private String postcode;

	@Column(name="provincie", nullable=false)
	private String provincie;

	@Column(name="stad", nullable=false)
	private String stad;

	@Column(name="winkel", nullable=false)
	private String winkel;
	
	@Column(name="commercieleActiviteit", nullable=false)
	private String commercieleActiviteit;
	
	@Column(name="oppervlakte", nullable=false)
	private double oppervlakte;
	
	@Column(name="lengtevoorgevel", nullable=false)
	private double lengtevoorgevel;
	
	@Column(name="parking", nullable=false)
	private int parking;
	
	@Column(name="education", nullable=false)
	private int education;
	
	@Column(name="publiekTransport", nullable=false)
	private int publiekTransport;
	
	@Column(name="afbeelding", nullable=false)
	private byte[] afbeelding;
	
	@Column(name="lat")
	private double lat;
	
	@Column(name="longi")
	private double longi;
	
	@Column(name="completed")
	private int completed;
	
	@Column(name="bouwjaar")
	private	int bouwjaar;
	
	@Column(name="passage")
	private int passage;
	
	@Column(name="toegankelijkheid")
	private int toegankelijkheid;
	
	@Column(name="microtoegankelijkheid")
	private int microtoegankelijkheid;
	
	@Column(name="shopareaappreciation")
	private int shopareaappreciation;
	
	@Column(name="correctiefactor")
	private int correctiefactor;
	
	@Column(name="lokaalmonopolie")
	private int lokaalmonopolie;
	
	public int getLokaalmonopolie() {
		return lokaalmonopolie;
	}

	public void setLokaalmonopolie(int lokaalmonopolie) {
		this.lokaalmonopolie = lokaalmonopolie;
	}

	public Pand() {
		super();
	}
	
	public Pand(long id, String straat, String land, String postcode, String provincie, String stad, String winkel,
			double oppervlakte, double lengtevoorgevel, byte[] afbeelding, int parking, String commercieleActiviteit, 
			int publiekTransport, double lat, double longi, int bouwjaar,int completed, int passage, int toegankelijkheid, 
			int microtoegankelijkheid, int shopareaappreciation, int correctiefactor, int lokaalmonopolie) {
		super();
		this.id = id;
		this.straat = straat;
		this.land = land;
		this.postcode = postcode;
		this.provincie = provincie;
		this.stad = stad;
		this.winkel = winkel;
		this.oppervlakte = oppervlakte;
		this.lengtevoorgevel = lengtevoorgevel;
		this.parking = parking;
		this.commercieleActiviteit=commercieleActiviteit;
		this.publiekTransport=publiekTransport;
		this.afbeelding=afbeelding;
		this.lat=lat;
		this.longi=longi;
		this.completed=completed;
		this.bouwjaar= bouwjaar;
		this.passage=passage;
		this.toegankelijkheid=toegankelijkheid;
		this.microtoegankelijkheid=microtoegankelijkheid;
		this.shopareaappreciation=shopareaappreciation;
		this.correctiefactor=correctiefactor;
		this.lokaalmonopolie=lokaalmonopolie;
	}

	// copyconstructor
	public Pand(Pand pand) {
		this.id = pand.id;
		this.straat = pand.straat;
		this.land = pand.land;
		this.postcode = pand.postcode;
		this.provincie = pand.provincie;
		this.stad = pand.stad;
		this.winkel = pand.winkel;
		this.oppervlakte = pand.oppervlakte;
		this.lengtevoorgevel = pand.lengtevoorgevel;
		this.parking = pand.parking;
		this.commercieleActiviteit=pand.commercieleActiviteit;
		this.publiekTransport=pand.publiekTransport;
		this.afbeelding=pand.afbeelding;
		this.lat=pand.lat;
		this.longi=pand.longi;
		this.completed=pand.completed;
		this.bouwjaar=pand.bouwjaar;
		this.passage=pand.passage;
		this.toegankelijkheid=pand.toegankelijkheid;
		this.microtoegankelijkheid=pand.microtoegankelijkheid;
		this.shopareaappreciation=pand.shopareaappreciation;
		this.correctiefactor=pand.correctiefactor;
		this.lokaalmonopolie=pand.lokaalmonopolie;
	}

	// getters en setters
	public int getLokaalMonopolie() {
		return lokaalmonopolie;
	}

	public void setLokaalMonopolie(int lokaalmonopolie) {
		this.lokaalmonopolie = lokaalmonopolie;
	}

	public int getCorrectiefactor() {
		return correctiefactor;
	}

	public void setCorrectiefactor(int correctiefactor) {
		this.correctiefactor = correctiefactor;
	}

	public int getShopareaappreciation() {
		return shopareaappreciation;
	}

	public void setShopareaappreciation(int shopareaappreciation) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLongi() {
		return longi;
	}

	public void setLongi(double longi) {
		this.longi = longi;
	}

	public byte[] getAfbeelding() {
		return afbeelding;
	}

	public void setAfbeelding(byte[] afbeelding) {
		this.afbeelding = afbeelding;
	}

	public double getOppervlakte() {
		return oppervlakte;
	}


	public void setOppervlakte(double oppervlakte) {
		this.oppervlakte = oppervlakte;
	}

	public double getLengtevoorgevel() {
		return lengtevoorgevel;
	}

	public void setLengtevoorgevel(double lengtevoorgevel) {
		this.lengtevoorgevel = lengtevoorgevel;
	}

	public String getCommercieleActiviteit() {
		return commercieleActiviteit;
	}

	public void setCommercieleActiviteit(String commercieleActiviteit) {
		this.commercieleActiviteit = commercieleActiviteit;
	}

	public void setBouwjaar(int bouwjaar){
		this.bouwjaar=bouwjaar;
	}
	
	public int getBouwjaar(){
		return bouwjaar;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getWinkel() {
		return winkel;
	}

	public void setWinkel(String winkel) {
		this.winkel = winkel;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getProvincie() {
		return provincie;
	}

	public void setProvincie(String provincie) {
		this.provincie = provincie;
	}

	public String getStad() {
		return stad;
	}
	
	public void setStad(String stad) {
		this.stad = stad;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getPubliekTransport(){
		return publiekTransport;
	}
	
	public void setPubliekTransport(int publiekTransport){
		this.publiekTransport=publiekTransport;
	}


	public int getParking() {
		return parking;
	}

	public void setParking(int parking) {
		this.parking = parking;
	}

}