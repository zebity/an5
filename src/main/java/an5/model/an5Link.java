/**
 @what an5Link class provides logical or physcial link, so looks like a graph edge
         In OSI model link should only go up to datalink layer
         
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import com.fasterxml.jackson.databind.JsonNode;

public class an5Link extends an5Object {
  String an5name = "link";
  public an5Link() {  
  }
  public an5Link(an5ClassTemplate t, boolean ab) {
    super(t, ab);
  }
  // public an5Link(an5ConstructArguments args) {	  
  // }
  public an5Link(an5Link lk) {
    super(lk);
  }
  public an5Link(JsonNode nd) {
	super(nd == null ? null : nd.get("extends"));
  }
}
