package entities;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(Pand.class)
public class Panden extends ArrayList<Pand> {
	private static final long serialVersionUID = 3305885282982134771L;


  public Panden() {
    super();
  }

  public Panden(Collection<? extends Pand> c) {
    super(c);
  }

  @XmlElement(name ="pand")
  public List<Pand> getAllePanden() {
    return this;
  }

  public void setPanden(List<Pand> panden) {
    this.addAll(panden);
  }
}