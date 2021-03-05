package an5.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class an5InterfaceSignatureKeys {
  public Map<String, an5MapIf> ifSet = new HashMap<>();
  public an5InterfaceSignatureKeys() {
  }
  public void add(an5Object ob, an5InterfaceInstance[] ifs) {
    for (an5InterfaceInstance ifInst : ifs) {
      for (String [] sigKey : ifInst.interfaceDefinition.signatureKeys) {
        ifSet.put(sigKey[1], new an5MapIf(sigKey[1], sigKey[0], ifInst));
      }
    }
  }
  public int canBind(an5Object o, an5Service netSrv, an5Service protoSrv) {
    int cnt = 0;
    an5MapIf lookUp = null;
    Set<String> ifFnd = new HashSet<>();
    for (an5MapIf ifInfo: o.AN5SG_sigKeyUnion.ifSet.values()) {
      lookUp = ifSet.get(ifInfo.sigKey);
      if (lookUp != null) {
    	if (! ifFnd.contains(ifInfo.forInterface.interfaceDefinition.getGUID())) {
    	  ifFnd.add(ifInfo.forInterface.interfaceDefinition.getGUID());
    	  cnt++;
    	}
      }
    }
	return cnt;
  }
}
  