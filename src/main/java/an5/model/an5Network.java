package an5.model;

import java.util.Map;

public class an5Network extends an5Object {
  public Map<String, an5Element> hasMembers;
  public an5Network() {  
  }
  public an5Network(an5ConstructArguments args) {  
  }
  public an5Service providesServices() {
    return new an5ServiceList((an5ServiceMap)AN5AT_serviceUnion);  
  }
}
