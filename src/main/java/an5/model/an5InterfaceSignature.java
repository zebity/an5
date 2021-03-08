package an5.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class an5InterfaceSignature {
  public Map<String, String[]> common = new LinkedHashMap<>();
  Map<String, String[]> needs = new LinkedHashMap<>();
  Map<String, String[]> provides = new LinkedHashMap<>();
  public Map<String, String> services = new LinkedHashMap<>();
  String an5name;
  int min = 0,
      max = -1;
  public an5InterfaceSignature(String ifType, List<String[]> c, List<String[]> n, List<String[]> p, List<String> s, int mn, int mx) {
    for (String[] nv : c) common.put(nv[0], new String[]{nv[0], nv[1]});
    for (String[] nv : n) needs.put(nv[0], new String[]{nv[0], nv[1]});
    for (String[] nv : p) provides.put(nv[0], new String[]{nv[0], nv[1]});
    for (String nv : s) services.put(nv, new String(nv));
    an5name = ifType;
    min = mn;
    max = mx;
  }
  public an5InterfaceSignature(String ifType, String[][] c, String[][] n, String[][] p, String[] s, int mn, int mx) {
	for (String[] nv : c) common.put(nv[0], new String[]{nv[0], nv[1]});
	for (String[] nv : n) needs.put(nv[0], new String[]{nv[0], nv[1]});
	for (String[] nv : p) provides.put(nv[0], new String[]{nv[0], nv[1]});
	for (String nv : s) services.put(nv, new String(nv));
	an5name = ifType;
	min = mn;
	max = mx;
  }
  String getSignatureKey () {
	StringBuilder key = new StringBuilder();
    for (String[] s: common.values()) {
      if (s[0].equals("service")) {
    	key.append(s[1]); /* for service use pair value */
      }
      else {
    	key.append(s[0]); /* other use pair name */
      }
      key.append("__");
    }
    return new String(key.toString());
  }
}
