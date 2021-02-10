package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceSignature {
  public List<String[]> common = new ArrayList<>();
  List<String[]> needs = new ArrayList<>();
  List<String[]> provides = new ArrayList<>();
  List<String> services = new ArrayList<>();
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
  public an5InterfaceSignature(String ifType, String[][] c, String[][] n, String[][] p, String[] s, int mn, int mx) {
	for (String[] nv : c) common.add(new String[]{nv[0], nv[1]});
	for (String[] nv : n) needs.add(new String[]{nv[0], nv[1]});
	for (String[] nv : p) provides.add(new String[]{nv[0], nv[1]});
	for (String nv : s) services.add(new String(nv));
	an5name = ifType;
	min = mn;
	max = mx;
  }
  String getSignatureKey () {
	StringBuilder key = new StringBuilder();
    for (int i = 0; i < common.size(); i++) {
      if (common.get(i)[0].equals("service")) {
    	key.append(common.get(i)[1]); /* for service use pair value */
      }
      else {
    	key.append(common.get(i)[0]); /* other use pair name */
      }
      key.append("__");
    }
    return new String(key.toString());
  }
}
