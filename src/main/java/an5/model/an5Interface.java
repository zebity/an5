package an5.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

abstract public class an5Interface extends an5Object {
  public static class keyItem { static final int keyName = 0, keySig = 1;};
  public List<an5InterfaceSignature> signatureSet = new ArrayList<>();
  public Map<String, String[]> signatureKeys = new LinkedHashMap<>();
  String lastKey = new String();
  public an5Interface() {
    super();
  }
  public void addSignatureSet(an5InterfaceSignature sig) {
    signatureSet.add(0, sig);
    if (sig.getSignatureKey().length() > 0) {
      StringBuilder key = new StringBuilder();
      for (int i = 0; i < signatureSet.size(); i++) {
        String sigKey = signatureSet.get(i).getSignatureKey();
        if (sigKey.length() > 0) {
          key.append(sigKey.toString());
        }
      }
      if (key.length() > 0) {
        signatureKeys.put(key.toString(), new String[]{new String(sig.an5name), key.toString()});
        lastKey = new String(key.toString());
      }
    }
  }
  public Collection<String> getServiceSignatureValues(int i) {
    return signatureSet.get(i).services.values();
  }
  public an5Binding getBinding(String nmPat, int i) {
	String nm = nmPat.replace("%I", String.valueOf(i));
    an5Binding res = new an5Binding(nm, i);
    return res;
  }
  public an5Binding getBinding(an5Binding bd) {
    return new an5Binding(bd);
  }
  public an5InterfaceMatch matchSignature(an5Interface to) {
    an5InterfaceMatch res = new an5InterfaceMatch();
    res.matchSignature(this, to);
    return res;
  }
  public String getLastKey() {
    return lastKey;
  }
}