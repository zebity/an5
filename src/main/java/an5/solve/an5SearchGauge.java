/**
 @what The guage method provides interface from generic AND/OR solver and the queue
         management, to allow SCORE/COST priority for search
         
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

abstract public class an5SearchGauge {
  abstract public int[] gauge(int type);
}
