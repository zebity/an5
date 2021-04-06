/**
 @what Hold the set of interface signature keys to allow fast lookup

 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.HashMap;
import java.util.Map;

public class an5InterfaceSignatureKeys {
  public Map<String, an5MapIf> ifSet = new HashMap<>();
  public an5InterfaceSignatureKeys() {
  }
  public void add(an5Object ob, an5InterfaceInstance[] ifs) {
    for (an5InterfaceInstance ifInst : ifs) {
      for (String [] sigKey : ifInst.interfaceDefinition.signatureKeys.values()) {
        ifSet.put(sigKey[1], new an5MapIf(sigKey[1], sigKey[0], ifInst, ob));
      }
    }
  }
  public void add(an5Object ob, an5InterfaceTable[] tab) {
    for (an5InterfaceTable ifTab : tab) {
	  for (String [] sigKey : ifTab.instance.interfaceDefinition.signatureKeys.values()) {
	    ifSet.put(sigKey[1], new an5MapIf(sigKey[1], sigKey[0], ifTab.instance, ob));
	  }
	}
  }
}
  