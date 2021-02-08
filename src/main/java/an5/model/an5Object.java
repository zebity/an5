/**
 an5 model base class.
   Needs to support:
     discovery
     interface binding
     Has Java class id, unique id [local to VM] and persistent unique id which persists and
     would be same over multiple VM instances
  @author John Hartley - Graphica Software/Dokmai Pty Ltd.
 */

package an5.model;

import java.util.HashMap;
import java.util.Map;

public class an5Object {
  public boolean abstractSpec = false;
  public boolean mandatory = false;
  public String[] uniqueId = new String[2];
  public String[] persistentUniqueId = new String[2];
  public Map<String, an5VariableInstance> clVars = new HashMap<>();
  public an5VarUtil varUtil = new an5VarUtil();
  public an5Object getInstance(String nmPat, int i) {
    return null;
  }
  public an5Object() {}
}
