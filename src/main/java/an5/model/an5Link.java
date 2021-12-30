/**
 @what an5Link class provides logical or physcial link, so looks like a graph edge
         In OSI model link should only go up to datalink layer
         
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;


public class an5Link extends an5Object {
  String an5name = "link";
  public an5Link() {  
  }
  public an5Link(an5ConstructArguments args) {	  
  }
  public an5Link(an5Link lk) {
    super(lk);
  }
}
