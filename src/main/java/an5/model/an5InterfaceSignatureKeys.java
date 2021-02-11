package an5.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class an5InterfaceSignatureKeys {
  public class mapIf {
    String sigKey,
           ifType;
    an5InterfaceInstance forInterface;
    public mapIf(String k, String t, an5InterfaceInstance i) {
      sigKey = k;
      ifType = t;
      forInterface = i;
    }
  }
  public Map<String, mapIf> ifSet = new HashMap<>();
  public an5InterfaceSignatureKeys() {
  }
  public void add(an5Object ob, an5InterfaceInstance[] ifs) {
    for (an5InterfaceInstance ifInst : ifs) {
      for (String [] sigKey : ifInst.interfaceDefinition.signatureKeys) {
        ifSet.put(sigKey[1], new mapIf(sigKey[1], sigKey[0], ifInst));
      }
    }
  }
}
  