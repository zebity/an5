/**
 @what Factory interface for solver template classes
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5.model;

public interface an5ClassTemplate {
  public an5Object createInstance();
  public an5Service expose();
}
