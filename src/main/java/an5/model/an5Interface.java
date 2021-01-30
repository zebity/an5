package an5.model;

import java.util.ArrayList;
import java.util.List;

abstract public class an5Interface extends an5Object {
  public List<an5InterfaceSignature> signatureSet = new ArrayList<>();
  public List<String> signatureFrom = new ArrayList<>();
  public an5Interface() {
    super();
  }
  public void addSignatureSet(an5InterfaceSignature sig) {
    signatureSet.add(0, sig);
    signatureFrom.add(0, sig.an5name);
  }
}