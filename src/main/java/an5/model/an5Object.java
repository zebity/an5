/**
 an5 model base class.
   Needs to support:
     discovery
     interface binding
  @author John Hartley - Graphica Software/Dokmai Pty Ltd.
 */

package an5.model;

import java.util.HashMap;
import java.util.Map;

public class an5Object {
  public boolean abstractSpec = false;
  public boolean mandatory = false;
  public Map<String, an5VariableInstance> clVars = new HashMap<>();
  public an5VarUtil varUtil = new an5VarUtil();
  public an5Object getInstance(String nmPat, int i) {
    return null;
  }
  public an5Object() {}
}
