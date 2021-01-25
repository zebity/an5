/**
 Manage binding between interfaces
*/

package an5.model;

class an5Binding {
	enum bindState { OPEN, BASE_MATCH, REFLECTING, COMMITTED };
	an5Interface aRef = null,
			     bRef = null;
	an5Service member;
}