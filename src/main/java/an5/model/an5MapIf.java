package an5.model;

public class an5MapIf {
  public String sigKey,
		        ifType;
  public an5InterfaceInstance forInterface = null;
  public an5Object ref = null;
  public an5MapIf() {
  }
  public an5MapIf(String k, String t, an5InterfaceInstance i) {
    sigKey = k;
    ifType = t;
	forInterface = i;
  }
  public an5MapIf(String k, String t, an5InterfaceInstance i, an5Object o) {
    sigKey = k;
	ifType = t;
    forInterface = i;
    ref = o;
  }
}
