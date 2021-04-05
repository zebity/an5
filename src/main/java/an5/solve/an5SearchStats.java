package an5.solve;

public class an5SearchStats {
  public int noUnseeded = 0,
             noIntermediate = 0,
             noLocalEquivalentsRemoved = 0,
             noFailed = 0,
             noRevisits = 0,
             noBounded = 0,
             noFound = 0,
             maxDepth = 0,
             maxBreadth = 0;
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
}
