/**
 @what Test what generated code might look like...
 
  
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

package proto;

import java.util.List;
import an5.model.an5Interface;


class AN5CL_ethernet_port extends an5Interface implements AN5IF_ethernet_port {
  // boolean concrete = false;
  // an5InterfaceSignature signature;
  String name,
         MAC;
   public AN5CL_ethernet_port(List<Object[]> p1, String p2, String p3) {
     // signatureSet = p1;
     name = p2;
     MAC = p3;
   }
}
