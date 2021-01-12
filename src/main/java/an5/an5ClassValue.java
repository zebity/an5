/*
 * What: an5 declared type { class, interface, link }
*/

package an5;

import java.util.ArrayList;

class an5ClassValue extends an5TypeValue {
	ArrayList<an5InterfaceValue> interfacesExposed;
	an5ClassValue classExtended;
	an5ClassValue(String is, String val) {
      super(is, val);
	}
}