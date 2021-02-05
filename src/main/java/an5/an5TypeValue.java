/*
 * What: an5 declared type { class, interface, link }
*/

package an5;

class an5TypeValue {
  String isA,
	     value,
	     inPackage;
  public boolean locked = false;
  an5TypeValue(String is, String val, String pack) {
    isA = is;
    value = val;
    inPackage = pack;
  }
}