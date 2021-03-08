/**
 Manage binding between interfaces
*/

package an5.model;

public class an5Binding {
  enum bindState { OPEN, BASE_MATCH, REFLECTING, COMMITTED };
  an5Interface aRef = null,
			   bRef = null;
  an5Service member = null;
  int id;
  String name;
  bindState state = bindState.OPEN;
  public an5Binding(String nm, int n) {
    name = nm;
    id = n;
  }
  public an5Binding(an5Binding bd) {
	state = bd.state;
    name = bd.name;
    id = bd.id;
    if (bd.member != null) {
      if (bd.member instanceof an5ServiceMap) {
        member = new an5ServiceMap((an5ServiceMap)bd.member);
      } /* else if (bd.member instanceof an5ServiceList) { */
    	/* change to only use an5ServiceMap */
        /* member = new an5ServiceMap((an5ServiceMap)bd.member);
      } */
    }
    aRef = bd.aRef;
    bRef = bd.bRef;
  }
}