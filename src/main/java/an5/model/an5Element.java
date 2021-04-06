/**
 @what an5 Network Elements is the base for NE devices such as Router, Switch, Computer
         an5Link objects are not derived from an5Element nor an5Network
         
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5.model;

public class an5Element extends an5Object {  
  public an5Element() {
  }
  public an5Element(an5ConstructArguments args) {  
  }
  public an5Element(an5Element e) {
    super(e);
  }
}
