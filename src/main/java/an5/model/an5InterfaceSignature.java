package an5.model;

import java.util.List;

public class an5InterfaceSignature {
  String hash;
// List<Tuple2<String, String>> listPairs;
  List<String[]> common;
  List<String[]> needs;
  List<String[]> provides;
  List<String> services;
  String an5name;
  public an5InterfaceSignature(String ifType, List<String[]> c, List<String[]> n, List<String[]> p, List<String> s) {
    common = c;
    needs = n;
    provides = p;
    services = s;
    an5name = ifType;
  }
}
