/**
 @what: an5 declared type { class, interface, link }
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

package an5;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

class an5InterfaceValue extends an5TypeValue {
  String baseSignature,
	     extendedSignature;
  boolean fromMemberDec = true,
		  cardinalityDefined = false;
  int cardinalityMin,
	  cardinalityMax;
  String nameTemplate = new String("%I");
  List<String> common,
	           needs,
	           provides;
  List<String[]> commonPair = null,
		         needsPair = null,
		         providesPair = null;
  List<String[]> attributes = null;
  List<String[]> constants = null;
  List<String> services = new ArrayList<>();
  List<an5InterfaceValue> interfacesExtended = new ArrayList<>();
  an5InterfaceValue(String val, String pack) {
    super("interface", val, pack);
  }
}