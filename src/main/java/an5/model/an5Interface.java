package an5.model;

import java.util.ArrayList;
import java.util.List;

abstract public class an5Interface extends an5Object {
  public List<an5InterfaceSignature> signatureSet = new ArrayList<>();
  public void loadSignatures() {}
}