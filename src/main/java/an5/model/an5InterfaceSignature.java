package an5.model;

import java.util.List;

public class an5InterfaceSignature {
  String hash;
// List<Tuple2<String, String>> listPairs;
  List<String[]> common;
  List<String[]> needs;
  List<String[]> provides;
  List<String> services;
//  an5Service service;
  public an5InterfaceSignature(List<String[]> c, List<String[]> n, List<String[]> p) {
    common = c;
    needs = n;
    provides = p;
  }
}
