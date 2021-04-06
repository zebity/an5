/**
@what An outline of what generated an5 java object would look like

@author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package proto;

import an5.model.*;

public class AN5CL_switch extends an5Element implements AN5IF_ethernet_port_base_t {
  AN5CL_ethernet_port_base_t[] ports;
  String name;
  public AN5CL_switch(String p1, int p2) {
    name = p1;
    // ports = new AN5CL_ethernet_port_base_t()[p2];
  }
}
