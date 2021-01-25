package an5.model;

import java.util.List;

abstract public class an5Interface extends an5Object {
  public List<Object[]> signatureSet = null;
  boolean concrete = false;
  
  public int derivedFrom() {
    return 0;
  }
  public int getNoSignatures() {
    return 0;
  }
}