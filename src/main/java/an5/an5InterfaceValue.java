/*
 * What: an5 declared type { class, interface, link }
*/

package an5;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

class an5InterfaceValue extends an5TypeValue {
  String baseSignature,
	     extendedSignature;
  int cardinalityMin,
	  cardinalityMax;
  List<String> base,
	           needs,
	           provides;
  List<an5InterfaceValue> interfacesExtended = new ArrayList<>();
  an5InterfaceValue(String val, String pack) {
    super("interface", val, pack);
  }
}