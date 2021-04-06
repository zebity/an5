/**
 @what: an5 declared type { class, interface, link }
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
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