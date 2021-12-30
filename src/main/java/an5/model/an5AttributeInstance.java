/**
 @what Holds the class field attribute instance

 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5AttributeInstance extends an5VariableInstance {
  public an5Interface interfaceDefinition;
  List<an5Binding> bindings = new ArrayList<>();
  public an5AttributeInstance(String varNm, String val) {
	super(varNm);
  }
}
