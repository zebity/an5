/**
  @what an5 declared type { class, interface, link }
  
  @author John Hartley - Graphica Software/Dokmai Pty Ltd.
*/

package an5;

import java.util.ArrayList;
import java.util.List;

class an5ClassValue extends an5TypeValue {
  boolean fromMemberDec = true;
  boolean abstractSpec = false;
  List<an5InterfaceValue> interfacesExposed = new ArrayList<>();
  an5ClassValue classExtended;
  List<an5InterfaceVariableValue> interfacesReflected = new ArrayList<>();
  List<an5ClassVariableValue> contained = new ArrayList<>();
  List<String[]> attributes = new ArrayList<>();
  List<String[]> constants = null;
  List<an5ServiceSetValue> networkServices = new ArrayList<>();
  an5ClassValue(String val, String pack) {
    super("class", val, pack);
  }
}