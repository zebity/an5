/*
 * What: an5 declared type { class, interface, link }
*/

package an5;

import java.util.ArrayList;
import java.util.List;

class an5ClassValue extends an5TypeValue {
  List<an5InterfaceValue> interfacesExposed = new ArrayList<>();
  an5ClassValue classExtended;
  an5ClassValue(String val, String pack) {
    super("class", val, pack);
  }
}