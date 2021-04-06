/**
 @what Interface Map table row helper class
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

public class an5InterfaceTable {
  public int id;
  public String name;
  public an5InterfaceInstance instance;
  public an5InterfaceTable(int i, String n, an5InterfaceInstance iF) {
    id = i;
    name = n;
	instance = iF;
  }
}
