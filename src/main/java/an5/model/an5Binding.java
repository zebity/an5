/**
 @what Manage binding between interfaces
         Bindings have a state which can be:
           Open - available to bind
           Matched - in uncommitted binding, so resource allocation is delayed
           Reflecting - is reflecting partial binding and/or service outcome
           Committed - has committed binding, which means all resources are allocated
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

package an5.model;

public class an5Binding {
  public static class bindState { final static int OPEN = 0x01, BASE_MATCH = 0x02, REFLECTING = 0x04,
	                                COMMITTED = 0x08, BOUND = 0x0E, ALL = 0x0F; }

  an5InterfaceInstance aIRef = null,
			           bIRef = null;
  an5Object aORef = null,
		    bORef = null;
  an5Service member = null;
  an5Binding boundTo = null;
  int id;
  String name;
  int state = bindState.OPEN;
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
    aIRef = bd.aIRef;
    bIRef = bd.bIRef;
    aORef = bd.aORef;
    bORef = bd.bORef;
    boundTo = bd.boundTo;
  }
  int bind(an5Object ao, an5InterfaceInstance ai, an5Object bo, an5InterfaceInstance bi, an5Binding link, an5InterfaceMatch match) {
	/* ignore cardinality, reflecting and service exposed for initial test */
    int res = 0;
    aORef = ao;
    aIRef = ai;
    bORef = bo;
    bIRef = bi;
    boundTo = link;
    state = bindState.BASE_MATCH;
    link.aORef = bo;
    link.aIRef = bi;
    link.bORef = ao;
    link.bIRef = ai;
    link.boundTo = this;
    link.state = bindState.BASE_MATCH;
    if (bIRef == null || aIRef == null) {
      res = -1;
    }
    return res;
  }
  int reBind(an5Object cloneAO, an5InterfaceInstance cloneAI, an5Object cloneBO, an5InterfaceInstance cloneBI, an5Binding link) {
	/* ignore cardinality, reflecting and service exposed for initial test */
    int res = 0;
    bORef = cloneBO;
    bIRef = cloneBI;
    boundTo = link;
    link.bORef = cloneAO;
    link.bIRef = cloneAI;
    link.boundTo = this;
    return res;
  }
}