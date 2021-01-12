/*
 * What: an5 declared type { class, interface, link }
*/

package an5;

import java.lang.String;
import java.util.ArrayList;

class an5InterfaceValue extends an5TypeValue {
	String baseSignature,
	       extendedSignature;
	int cardinalityMin,
	    cardinalityMax;
	ArrayList<String> base,
	                  needs,
	                  provides;
	ArrayList<an5InterfaceValue> interfacesExtended;
	an5InterfaceValue(String is, String val) {
      super(is, val);
	}
}