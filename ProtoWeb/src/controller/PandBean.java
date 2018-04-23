package controller;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import ejb.PandManagementEJBLocal;
import ejb.ParameterManagementEJBLocal;
import entities.Pand;

/**
 * Bean die alles ivm panden behandelt op de website
 */
@ViewScoped
@Named("pandBean")
@ManagedBean(name = "pandBean")
public class PandBean implements Serializable {
    private static final long serialVersionUID = -3737221385235612830L;
 
    // CLASS ATTRIBUTES
 
    /**
     * mogelijkheid tot uitvoeren van methodes die betrekking hebben tot de panden en hun databank
     */
    @EJB
    private PandManagementEJBLocal pandEJB;
 
    /**
     * mogelijkheid tot uitvoeren van methodes die betrekking hebben tot de templates en hun databank
     */
    @EJB
    private ParameterManagementEJBLocal parameterEJB;
 
    /**
     * algemene variabelen, zodat we ze niet telkens per methode moeten opnieuw defini�ren
     * kunnen dan ook gebruikt worden over meerdere paginas
     */
    private List<Pand> panden;
    private Pand pand = new Pand();
    private String lat;
    private String longi;
 
    // html gerelateerde variabelen
    private HtmlPanelGroup group;
    private HtmlOutputLabel label;
    private HtmlOutputLabel info;
    private HtmlForm form;
 
    // CLASS METHODS
 
    /**
     * deze methode wordt gebruikt om dynamisch een stuk van de site weer te
     * geven waar de noodzakelijke parameters ingevuld kunnen worden
     * gebruikt bij regiomanager/updatePand, waar er stukken html geladen moeten worden
     * aan de hand van de waarden van de template van het overeenkomstig pand
     */
    public void dynamicHTML() {
    	
        // opvragen van lijst met alle noodzakelijke parameters
        List<String> params = pandEJB.getAlleNoodzakelijkeParameters(pand);
        // types van noodz parameters opvragen
        List<String> paramTypes = pandEJB.getAlleNoodzParamTypes(pand);
 
        // overlopen van alle params
        for (int i = 0; i < params.size(); i++) {
 
            String type = parameterEJB.getType(params.get(i));
 
            HtmlPanelGroup group1 = new HtmlPanelGroup();
            group1.setStyleClass("form-group");
            group1.setLayout("block");
 
            label = new HtmlOutputLabel();
            String param = params.get(i);
            String naamParam = parameterEJB.getNaam(param);
            label.setValue(naamParam);
            label.setStyle("color:white");
 
            //per type wordt een invulbaar veld met zijn overeenkomstige opmaak gegenereerd
            if (type.equals("optie") || type.equals("score")) {
 
                HtmlCommandButton info_button = new HtmlCommandButton();
                info_button
                        .setStyle("text-decoration:none; color:white; font-size:15px; background:none; border: none;");
                info_button.setOnclick("openInfo(this)");
                info_button.setId(param + "_info_button");
                info_button.setValue("info_outline");
                info_button.setStyleClass("material-icons");
                info_button.setType("button");
 
                HtmlSelectOneMenu select = new HtmlSelectOneMenu();
                select.setStyleClass("form-control");
                select.setStyle("width:100px");
                ValueExpression expr = getValueExpression("#{pandBean.pand." + params.get(i) + "}");
                select.setValueExpression("value", expr);
 
                int a = 0;
                if (type.equals("optie")) {
                    a = 1;
                } else if (type.equals("score")) {
                    a = 0;
                }
                for (a = a; a < parameterEJB.getAantal(param) + 1; a++) {
 
                    UISelectItem item = new UISelectItem();
 
                    item.setItemValue(Integer.toString(a));
                    select.getChildren().add(item);
                }
 
                group1.getChildren().add(label);
                group1.getChildren().add(info_button);
                group1.getChildren().add(select);
 
                group.getChildren().add(group1);
            }
 
            else {
                System.out.println("dit is lengte of oppervlakte");
 
                HtmlInputText inputPutText = new HtmlInputText();
                ValueExpression expr = getValueExpression("#{pandBean.pand." + params.get(i) + "}");
                inputPutText.setValueExpression("value", expr);
                StringBuilder sb = new StringBuilder();
                sb.append("geefExtraInfo('").append(params.get(i)).append("')");
                inputPutText.setOnfocus(sb.toString());
                inputPutText.setStyleClass("form-control");
 
                HtmlCommandButton info_button = new HtmlCommandButton();
                info_button
                        .setStyle("text-decoration:none; color:white; font-size:15px; background:none; border: none;");
                info_button.setOnclick("openInfo(this)");
                info_button.setId(param + "_info_button");
                info_button.setValue("info_outline");
                info_button.setStyleClass("material-icons");
                info_button.setType("button");
 
                info = new HtmlOutputLabel();
                info.setId(params.get(i));
                info.setStyle("color:#ccff99");
 
                // toevoegen van label en inputtext aan group onder panel group
                group1.getChildren().add(label);
                group1.getChildren().add(info_button);
                group1.getChildren().add(inputPutText);
                group1.getChildren().add(info);
 
                // group gelinkt aan huidige parameter toevoegen aan
                // overkoepelende group
                group.getChildren().add(group1);
            }
        }
 
    }
 
    private ValueExpression getValueExpression(String expression) {
        ExpressionFactory expressionFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        ELContext expressionContext = FacesContext.getCurrentInstance().getELContext();
        return expressionFactory.createValueExpression(expressionContext, expression, Object.class);
    }
 
    /**
     * lijst met alle panden opvragen
     * @return lijst met alle panden
     */
    public List<Pand> getPanden() {
        // zie GETTERS AND SETTERS
        this.getAllePanden();
        return panden;
    }
 
    /**
     * String met alle parameters die aan een pand kunnen toegekend worden
     * @return lijst met alle parameters
     */
    public String getAlleParameters() {
        StringBuilder sb = new StringBuilder();
        for (String s : pandEJB.getAlleParameters()) {
            sb.append(s);
        };
        return sb.toString();
    }
 
    /**
     * 
     * @return String met alle noodzakelijke parameters, die bij ieder pand moeten ingevuld worden
     * (gelinkt aan templates klasse)
     */
    public String getAlleNoodzakelijkeParameters() {
        StringBuilder sb = new StringBuilder();
        for (String n : pandEJB.getAlleNoodzakelijkeParameters(pand)) {
            sb.append(n + " ");
        }
        List<String> paramTypes = pandEJB.getAlleNoodzParamTypes(pand);
        return sb.toString();
    }
 
    /**
     * De huidige gebruiker mag enkel de panden zien van de winkel waarin hij werkt
     * @return List<Pand> die van de winkel zijn waar de gebruiker werkt
     */
    public List<Pand> getWinkelPanden() {
        this.panden = pandEJB.findWinkelPanden(null);
        return panden;
    }
 
    /**
     * De huidige gebruiker wil niet alle panden van de winkel zijn, hij wil enkel de
     * panden zien van zijn regio (hier gebruikten we provincie).
     * @return alle winkels van de provincie waar de gebruiker woont
     */
    public List<Pand> getRegioPanden() {
        this.panden = pandEJB.findRegioPanden(null, null);
        return panden;
    }
 
    /**
     * check als een parameter een noodzakelijke paramter is
     * @param de te checken parameter
     * @return true als noodzakelijk, false als niet noodzakelijk
     */
    public boolean checkCondition(String s) {
        List<String> list = pandEJB.getAlleNoodzakelijkeParameters(pand);
        for (String str : list) {
            if (str.trim().contains(s))
                return true;
        }
        return false;
    }
 
    /**
     * enkel gebruikt door expantionmanager
     * maakt gebruik van de pand databank
     * 
     * maakt een pand aan, returnt een html pagina
     * 
     * @return de html pagina die alle panden weergeeft
     */
    public String createPand() {
         
        if (!lat.equals("")) {
            System.out.println("Bestaand adres");
            pand.setLat(Double.parseDouble(lat));
            pand.setLongi(Double.parseDouble(longi));
 
            Pand foo = new Pand();
            foo = this.pand;
            foo.setProvincie(sanitise(pand.getProvincie()));
            foo.setStad(sanitise(pand.getStad()));
            foo.setStraat(sanitise(pand.getStraat()));
 
            pandEJB.createPand(pand);
        }
 
        else {
            System.out.println("Vals adres");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage facesMessage = new FacesMessage("Adres onvindbaar");
            facesContext.addMessage(null, facesMessage);
            return null;
             
        }
 
        return "openProject.xhtml";
    }
 
    /**
     * methode wordt gebruikt bij het invullen van locaties
     * 
     * kuist de string op naar het correcte formaat
     * west-vlaanderen --> West-Vlaanderen
     * gent --> Gent
     * 
     * 
     * @param de input string
     * @return de output string, die opgeruimd is
     */
    private String sanitise(String s) {
 
        String sanitised = "";
         
        if (s.length() > 0) {
 
            int[] posities = new int[s.length()];
            posities[0] = 1; // sowieso beginnen met hoofdletter
 
            for (int i = 1; i < s.length() - 1; i++) {
                char tr = s.charAt(i); // trigger
                if (tr == ' ' | tr == '-') {
                    posities[i + 1] = 1;
                }
            }
 
            for (int i = 0; i < s.length(); i++) {
                String karakter = s.substring(i, i + 1);
                if (posities[i] > 0) {
                    sanitised += karakter.toUpperCase();
                } else {
                    sanitised += karakter.toLowerCase();
                }
            }
        }
 
        return sanitised;

    }
 
    /**
     * @param pand dat we zullen verwijderen
     * @return de homepagina
     */
    public String deletePand(Pand pand) {
 
        pandEJB.deletePand(pand);
        return "openProject.faces?faces-redirect=true";
    }
 
    /**
     * verandert de waarden van een pand naar de net ingevulde waarden
     * @return de homepagina
     */
    public String updatePand() {
        //System.out.println(pand.getOppervlakte());
        pandEJB.updatePand(pand);
        return "openProject.faces?faces-redirect=true";
    }
 
    /**
     * zet de klasse globale variabele 'pand' het pand met de overeenkomstige pand id
     */
    public void findPand() {
        pand = pandEJB.findPand(pand.getId());
    }
 
    /**
     * 
     * kijkt naar de databank pand, geeft een lijst van alle panden in de databank terug
     * @return lijst met alle panden in de databank
     */
    public List<String> getAllWinkels() {
        this.panden = pandEJB.getAllePanden();
 
        // declaratie van de lijst
        List<String> winkelLijst = new ArrayList<String>();
        String winkel;
        for (Pand pand : panden) {
            winkel = pand.getWinkel();
            if (!winkelLijst.contains(winkel)) {
 
                // toevoegen
                winkelLijst.add(winkel);
            }
 
        }
 
        return winkelLijst;
    }
 
    /**
     * als we in een url hebben staan "jfdkk.com?Winkel=9" geeft deze 9 terug
     * wordt gebruikt bij het doorgeven van een parameter via een url (admin/updateParameter)
     * 
     * @return de waarde die Winkel heeft in een URL
     */
    public String getWinkelTxt() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        return request.getParameter("Winkel").toString();
    }

    /**
     * analoog voor de methode hierboven, maar voor de parameter "pand"
     * wordt ook gebruikt bij het doorgeven van een parameter via een url (admin/updateParameter)
     * @return
     */
    public Long getId(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
         return Long.valueOf(request.getParameter("pand").toString());
     }
    
    /**
     * wordt gebruikt bij het admin scherm waar we de parameters kunnen updaten
     * 
     * geeft alle panden van een bepaalde winkel terug
     * 
     * @return de lijst met panden van een bepaalde winkel
     */
    public List<Pand> findWinkelPanden() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        String winkel = request.getParameter("Winkel").toString();
 
        List<Pand> pandLijst = pandEJB.findWinkelPanden(winkel);
 
        return pandLijst;
    }
 
    ////////////////////////// GETTERS AND SETTERS /////////////////////////////////////////
    public Pand getPand() {
        return pand;
    }
 
    public ParameterManagementEJBLocal getParameterEJB() {
        return parameterEJB;
    }
 
    public void setParameterEJB(ParameterManagementEJBLocal parameterEJB) {
        this.parameterEJB = parameterEJB;
    }
 
    public void setPand(Pand pand) {
        this.pand = pand;
    }
 
    public void setWinkel(String winkel) {
        this.pand.setWinkel(winkel);
    }
 
    public void getAllePanden() {
        this.panden = pandEJB.getAllePanden();
    }
 
    public PandManagementEJBLocal getPandEJB() {
        return pandEJB;
    }
 
    public void setPandEJB(PandManagementEJBLocal pandEJB) {
        this.pandEJB = pandEJB;
    }
 
    public void setGroup(HtmlPanelGroup group) {
        this.group = group;
    }
 
    public HtmlPanelGroup getGroup() {
        return group;
    }
 
    public void setForm(HtmlForm form) {
        this.form = form;
    }
 
    public HtmlForm getForm() {
        return form;
    }
 
    public String getLat() {
        return lat;
    }
 
    public void setLat(String lat) {
        this.lat = lat;
    }
 
    public String getLongi() {
        return longi;
    }
 
    public void setLongi(String longi) {
        this.longi = longi;
    }
 
}