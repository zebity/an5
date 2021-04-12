/**
 @what Collect search statistics to allow behavior diagnostics
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;

public class an5SearchStats {
  public int noUnseeded = 0,
             noIntermediate = 0,
             noLocalEquivalentsRemoved = 0,
             noFailed = 0,
             noRevisits = 0,
             noBounded = 0,
             noFound = 0,
             maxDepth = 0,
             maxBreadth = 0,
             loops = 0,
             addHead = 0,
             addTail = 0,
             addEmpty = 0,
             addMin = 0,
             addMax = 0,
             addInsert = 0;
  public Instant start,
                 stop;
  public void updateStats(int res, int lp, int dp) {
    loops = lp;
	maxDepth = Integer.max(maxDepth, dp);
	updateStats(res);
  }
  public void checkMaxBreadth(int bd) {
	maxBreadth = Integer.max(maxBreadth, bd);  
  }
  public void startTimer() {
    start = Instant.now();
  }
  public void stopTimer() {
	stop = Instant.now();
  }
  public void updateStats(int res) {
	switch (res) {
	  case an5SearchControl.SearchResult.SOLVING:
	  case an5SearchControl.SearchResult.START:
		         noIntermediate++;
	             break;
	  case an5SearchControl.SearchResult.VISITED:
		         noRevisits++;
	             break;
	  case an5SearchControl.SearchResult.FAILED:
		         noFailed++;
	             break;
	  case an5SearchControl.SearchResult.BOUND:
	             noBounded++;
	             break;
	  case an5SearchControl.SearchResult.FOUND:
		         noFound++;
	             break;
	  case an5SearchControl.SearchResult.UNDEFINED:
	             noUnseeded++;
	             break;
	  case an5SearchControl.SearchResult.SUSPENDED:
	      default: break;
	}
  }
  public void dumpJSON(PrintStream ps) {  
    ps.println("{");
    ps.println("  \"an5SearchStats\": {");
    ps.println("    \"noUnseeded\": " + noUnseeded + ",");
    ps.println("    \"noIntermediate\": " + noIntermediate + ",");
    ps.println("    \"noLocalEquivalentsRemoved\": " + noLocalEquivalentsRemoved + ",");
    ps.println("    \"noFailed\": " + noFailed + ",");
    ps.println("    \"noRevisits\": " + noRevisits + ",");
    ps.println("    \"noBounded\": " + noBounded + ",");
    ps.println("    \"noFound\": " + noFound + ",");
    ps.println("    \"maxDepth\": " + maxDepth + ",");
    ps.println("    \"maxBreadth\": " + maxBreadth + ",");
    ps.println("    \"loops\": " + loops + ",");
    ps.println("    \"addHead\": " + addHead + ",");
    ps.println("    \"addTail\": " + addTail + ",");
    ps.println("    \"addMin\": " + addMin + ",");
    ps.println("    \"addMax\": " + addMax + ",");
    ps.println("    \"addInsert\": " + addInsert + ",");
    ps.println("    \"duration\": " + Duration.between(start, stop).getSeconds());
    ps.println("  }");
    ps.println("}");
  }
}
