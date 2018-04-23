package controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import ejb.UserManagementEJBLocal;
import entities.Person;

/**
 * klasse die voornamelijk de mapping is tussen de user databank
 * bevat alle methoden die usergebonden zijn
 * bevat veel methoden om naar de volgende pagina te gaan
 */
@ViewScoped
@Named("userBean")
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {
	private static final long serialVersionUID = 6737147724536164355L;

	//CLASS ATTRIBUTEN
	/**
	 * zodat we toegang hebben tot de user databank
	 */
	@EJB
	private UserManagementEJBLocal userEJB;
	
	/**
	 * globale variabelen, zodat we ze geen 2 keer moeten definieren
	 */
	private Person person = new Person();
	private String inputLogin;
	private List<Person> personen;
	private String confirmPass;
	private String waarde;

	//CLASS METHODEN
	
	/**
	 * link methoden, om naar een andere pagina te gaan via een button
	 * @return
	 */
	public String triggerLogin() {
		return "login.xhtml";
	}

	public String triggerAddNewUser() {
		return "addNewUser.xhtml";
	}

	public String triggerAllUsers() {
		return "allUsers.xhtml";
	}

	public String triggerParameters() {
		return "modifyParameters_selectwinkel.xhtml";
	}

	public String triggerHomeAdmin() {
		return "index.xhtml";
	}

	public String triggerHome() {
		return "index.xhtml";
	}

	public String triggerNieuwPand() {
		return "startNieuw.xhtml";
	}

	public String returnHome() {
		return "index.xhtml";
	}

	/**
	 * ook een link methode, maar op een andere manier dan hierboven
	 */
	public void myAccount() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("updateEigenGegevens.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Wordt gebruikt om te vermelden in de mail die naar support gestuurd wordt
	 * Op die manier kan admin op de hoogte gesteld w van foute verwijzingen
	 * toepassing op intError en notFound pages
	 * 
	 * @return de URL die naar een foutpagina wijst OF null bij usermanipulatie
	 */
	public String getURL() {
		String vorige = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
		return vorige;
	}
	
	/**
	 * nog steeds link methodes, maar op de andere manier
	 */
	public void regioBackHome() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("openProject.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void expBackHome() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("openProject.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void regioEditGegevens() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editUpdateEigenGegevens.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void regioEditPassword() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editUpdateEigenPassword.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * niet-actieve sessie opvangen, actieve sessie heeft per definitie een rol
	 * 
	 * @see splitsing()
	 */
	public void activateRole() {
		String rol = "";
		try {
			Person p = userEJB
					.findPerson(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
			rol = p.getGroup();
			splitsing(rol);
		} catch (Exception e) {
			System.out.println("Geen actieve sessie");
		}

	}

	/**
	 * verwijst een regiomanager door naar zijn index page
	 */
	public void activateRegioManager() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProtoWeb/regiomanager");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * verwijst een expansiemanager door naar zijn index page
	 */
	public void activateExpansionManager() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProtoWeb/expansionmanager");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * verwijst een admin door naar zijn index page
	 */
	public void activateAdmin() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProtoWeb/admin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * het bedrijf waarvoor regio- of expansiemanager werkt
	 * 
	 * @return naam van het bedrijf dat klant is bij retailsonar
	 */
	public String getUserWinkel() {
		String winkel = userEJB.getWinkel();
		return winkel;
	}

	/**
	 * Kan met of zonder actieve sessie
	 * 
	 * @return volledige naam van een gebruiker, null indien geen sessie
	 */
	public String getNaam() {
		String naam = "";
		try {
			naam = userEJB.getNaam();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return naam;
	}

	/**
	 * 
	 * @return alle gebruikers uit de database
	 */
	public List<Person> getAllUsers() {
		this.personen = userEJB.findAllUsers();
		return personen;
	}

	/**
	 * userEJB accessen om wachtwoord aan te passen voorbeeld: jan janssens ->
	 * jan.j
	 */
	public void resetPassword() {
		person = userEJB.findPerson(waarde);
		System.out.println(waarde);
		userEJB.resetPassword(person);
		System.out.println("paswoord is gereset");
	}

	/**
	 * zet de globale person variabele naar de persoon waarvan het id al ingesteld is
	 */
	public void findUserId() {
		person = userEJB.findPersonById(person.getId());
	}

	/**
	 * zet de globale variabele person op zichzelf, de persoon die ingelogd is
	 */
	public void findUserById() {
		person = userEJB.findSelf();
	}

	/**
	 * automatisering van de login
	 * @return de variabele die in de url staat
	 */
	public String getURIID() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getParameter("User").toString();

	}

	/**
	 * doorverwijzing naar homepagina van een rol
	 * 
	 * @param s is de string-value van de rol van een persoon
	 */
	public void splitsing(String s) {

		String rol = s;

		if (rol.equalsIgnoreCase("admin")) {
			System.out.println("Admin sessie actief");
			activateAdmin();
		}

		if (rol.equalsIgnoreCase("regiomanager")) {
			System.out.println("Regiomanager sessie actief");
			activateRegioManager();
		}

		if (rol.equalsIgnoreCase("expansionmanager")) {
			System.out.println("Expansionmanager sessie actief");
			activateExpansionManager();
		}

	}

	/**
	 * toevoegen van een nieuwe gebruiker met uitgebreide logica -Admin bedrijf
	 * wordt standaard op Nvt gezet -Wachtwoorden moeten dezelfde zijn -Login
	 * moet uniek zijn -Wachtwoord moet veilig zijn
	 * 
	 * @return null indien fout, redirect indien succes
	 */
	public String addNewUser() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// Forceer bedrijf op admin
		if (this.person.getGroup().equals("Admin")) {
			this.person.setWinkel("Nvt (Admin)");
		}

		if (!person.gethPassword().equals(confirmPass)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paswoorden komen niet overeen",
					"ze moeten gelijk zijn");
			ctx.addMessage("form:confirmPassword", message);
			System.out.println("misse paswoorden");
			return null;
		}

		// lijst maken van alle logins

		HashSet<String> nicknames = new HashSet<>();
		List<Person> allePersonen = getAllUsers();
		for (int i = 0; i < allePersonen.size(); i++) {
			nicknames.add(personen.get(i).getLogin());
		}

		// kijken of de login er reeds instaat
		if (nicknames.contains(person.getLogin())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gebruikersnaam reeds in gebruik ",
					null);
			ctx.addMessage("form:login", message);
			System.out.println("gebruikersnaam reeds gebruikt");
			return null;
		}

		// verander door !false of zet deze if-clause in annotatie als je
		// bvb login:a password:a wilt aanmaken
		if (!veiligWachtwoord()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Paswoord dient een hoofdletter, kleine letter, speciaal teken en een cijfer te bevatten ", null);
			ctx.addMessage("form:hPassword", message);
			System.out.println("wachtwoord niet veilig");
			return null;
		}

		userEJB.addUser(person);
		return "allUsers.faces?faces-redirect=true";

	}

	/**
	 * checken als een wachtwoord veilig is
	 * een wachtwoord is veilig als:
	 * 			minstens 1 kleine letter
	 * 			minstens 1 hoodfletter
	 * 			minstens 1 cijfer
	 * 			minstens 1 speciaal teken
	 * 			minstens 4 karakters dus
	 * 
	 * @return 	true als het wachtwoord veilig is
	 * 			false als het wachtwoord onveilig is
	 */
	private boolean veiligWachtwoord() {
		String reader = person.gethPassword();
		int aantalCijfers = 0;
		int aantalKlein = 0;
		int aantalGroot = 0;
		int aantalSpeciale = 0;
		int pos;
		
		for (int i = 0; i < reader.length(); i++) {
			pos = (int) reader.charAt(i);

			if (pos > 47 && pos < 58) {
				aantalCijfers++;

			} else if (pos > 64 + 32 && pos < 91 + 32) {
				aantalKlein++;
			}

			else if (pos > 64 && pos < 91) {
				aantalGroot++;
			}

			else {
				aantalSpeciale++;
			}

		}
		return (aantalCijfers > 0 && aantalKlein > 0 && aantalGroot > 0 && aantalSpeciale > 0);

	}

	/**
	 * zelfde methode als hierboven maar met de string als paramameter
	 * @param de string die we willen checken op veilig wachtwoord
	 * @return 	true als het wachtwoord veilig is
	 * 			false als het wachtwoord onveilig is
	 */
	private boolean veiligWachtwoord(String ww) {
		int aantalCijfers = 0;
		int aantalKlein = 0;
		int aantalGroot = 0;
		int aantalSpeciale = 0;
		int pos;

		for (int i = 0; i < ww.length(); i++) {
			pos = (int) ww.charAt(i);

			if (pos > 47 && pos < 58) {
				aantalCijfers++;

			} else if (pos > 64 + 32 && pos < 91 + 32) {
				aantalKlein++;
			}

			else if (pos > 64 && pos < 91) {
				aantalGroot++;
			}

			else {
				aantalSpeciale++;
			}

		}
		return (aantalCijfers > 0 && aantalKlein > 0 && aantalGroot > 0 && aantalSpeciale > 0);

	}

	/**
	 * methode wordt opgeroepen in het admin/updateUser gedeelte
	 * 
	 * methode die ervoor zorgt dat de user ook aangepast wordt in te databank
	 * 
	 * @return de volgende pagina die we willen inladen na het drukken op de knop die 
	 * 			de methode oproept
	 */
	public String updatePerson() {
		userEJB.updateUser(person);
		person = new Person();
		return "allUsers.faces?faces-redirect=true";
	}

	/**
	 * uitloggen van een gebruiker
	 * 
	 * deactiveert de huidige actieve sessie
	 * 
	 * keert terug naar het inlogscherm
	 */
	public void logout() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		try {
			ec.redirect(ec.getRequestContextPath() + "#{request.contextPath}/index.faces");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * methode die gebruikt wordt bij expantiemanager en regiomanager
	 * om hun eigen gegevens aan te passen, om paswoord te resetten
	 * 
	 * @return de volgende webpagina waar we naartoe willen bij het drukken op de knop
	 */
	public String regioUpdateSelf() {
		userEJB.updateUser(person);
		person = new Person();
		return "updateEigenGegevens.xhtml";
	}

	/**
	 * nieuw paswoord instellen voor regio en expantiemanager
	 * 
	 * checkt als het oude paswoord correct is
	 * checkt als het nieuwe wachtwoord veilig is
	 * checkt als de 2 velden hetzelfde zijn 
	 * 
	 * indien alle bovenstaande kloppen: encrypteert het nieuwe wachtwoord en past het aan in de DB
	 * indien een fout: geeft de fout weer op de webpagina (facesMessage)
	 */
	public void regioTryEditPassword() {
		// opvragen van de ingevulde waarden staan
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String oldPassword = request.getParameter("oldpass");
		String newPassword1 = request.getParameter("newpass1");
		String newPassword2 = request.getParameter("newpass2");

		String codeerPaswoord = "";
		try {
			codeerPaswoord = Base64.getEncoder().encodeToString(
					MessageDigest.getInstance("SHA-256").digest(oldPassword.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean compareOudePaswoorden = userEJB.comparePaswoord(codeerPaswoord);

		boolean nieuwGelijk = newPassword1.equals(newPassword2);

		boolean nieuwVeilig = veiligWachtwoord(newPassword1);

		// voor het uitprinten naar de pagina dat er fouten zijn
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (!compareOudePaswoorden) {
			// warningmessage dat het oude wachtwoord niet gelijk
			System.out.println("oud wachtwoord niet juist");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oud paswoord niet correct", null);
			ctx.addMessage("my-form:newpass3", message);
			}
		else {
			// als de paswoorden gelijk zijn
			
			if (nieuwGelijk) {
				// als de paswoorden dezelfde zijn en gelijk zijn
				
				if (nieuwVeilig) {
					// als de paswoorden veilig zijn
					
					// pas ze aan
					String nieuwPaswoord = "";
					try {
						nieuwPaswoord = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256")
								.digest(newPassword1.getBytes(StandardCharsets.UTF_8)));
					} catch (NoSuchAlgorithmException e) {
						System.out.println("er is geen zon algoritme : Userbean :: regioTryEditPassword() ");
						e.printStackTrace();
					}

 					// aanpassen in de databank
					userEJB.sethPassWord(nieuwPaswoord);

					// message dat paswoorden succesvol gereset zijn
					System.out.println("reset succes");
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Paswoorden successvol gereset", null);
					ctx.addMessage("my-form:newpass3", message);

				} else {
					if (!nieuwVeilig) {
						// als de paswoorden niet veilig zijn
						
						// message dat ze niet veilig zijn
						System.out.println("niet veilig");
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Nieuw gekozen paswoord is niet veilig", null);
						ctx.addMessage("my-form:newpass3", message);
					}
				}
			} else {
				// als de paswoorden niet gelijk zijn
				
				// message dat ze niet gelijk zijn
				System.out.println("niet gelijk");
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"De nieuwe paswoorden zijn niet gelijk", null);
				ctx.addMessage("my-form:newpass3", message);
			}
		}

		// einde methode
	}

	/////////////////////////// GETTERS EN SETTERS /////////////////////////////////////////
	public String getWaarde() {
		return waarde;
	}

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

	public String getInputLogin() {
		return inputLogin;
	}

	public void setInputLogin(String inputLogin) {
		this.inputLogin = inputLogin;
	}

	public String getPersonLogin() {
		return userEJB.getLogin();
	}

	public String getPersonAdres() {
		return userEJB.getAdres();
	}

	public String getPersonWinkel() {
		return userEJB.getWinkel();
	}
	
	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
