package an5.model;

import java.util.List;

public class an5InterfaceSignature {
  String hash;
  List<String[]> common;
  List<String[]> needs;
  List<String[]> provides;
  List<String> services;
  String an5name;
  int min = 0,
      max = -1;
  public an5InterfaceSignature(String ifType, List<String[]> c, List<String[]> n, List<String[]> p, List<String> s, int mn, int mx) {
    common = c;
    needs = n;
    provides = p;
    services = s;
    an5name = ifType;
    min = mn;
    max = mx;
  }
  /* String[] getCommonHash() {
    
  } */
}
