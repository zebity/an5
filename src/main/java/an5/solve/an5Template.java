package an5.solve;

import java.util.List;

import an5.model.*;

abstract public class an5Template {
  public an5Template connectElements(an5Network n, an5Element a, an5Element b) {
	an5Template res = null;
	return res;
  }
  public an5Template CreateNetwork(an5Network proto, List<an5Object> from, an5Network net) {
    return null;
  }
  public an5Template joinNetwork(an5Network n, List<an5Element> ele) {
	an5Template res = null;
	return res;
  }
  public an5Template networkPath(an5Network n, an5Element a, an5Element b) {
	an5Template res = null;
	return res;
  }
  public an5Template connectNetworks(an5Network n1, an5Element a, an5Network n2, an5Element b) {
	an5Template res = null;
	return res;
  }
}
