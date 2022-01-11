/**
 @what Match interfaces which has 3 parts:
         common - this has the signature
         needs - what the interface needs
         provides - what the interface provides
         The result of binding can be reflected "service"

 @note Need to:
         Allow partial binding and reflection of this
         Add regex pattern matching support
         
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import an5.an5Logging;
import an5.an5Logging.LogVerbose;
import an5.an5Logging.SyslogLevel;

public class an5InterfaceMatch {
  public static class matchState { public static final char all = 'a', none = 'n', partial = 'p',  empty = 'e', undefined = 'u';}
  public int fromKeySz = 0,
	         toKeySz = 0,
	         fromSetSz = 0,
	         toSetSz = 0;
  public boolean[] keyMatch = null;
  public char[][] sigMatch = null;
  public int[][] servicesEnabled = null;
  public char matchResult = matchState.none; 
  an5Logging log = new an5Logging(LogVerbose.VERBOSE, SyslogLevel.debug);
  
  void matchSignature(an5Interface from, an5Interface to) {
	int i, j, k,
	    min, max, overall,
	    matched = 0, notMatched = 0,
	    fromSz, toSz;
    fromKeySz = from.signatureKeys.size();
    toKeySz = to.signatureKeys.size();
    String[] toNamVal = null;

    /* DBG: if (from instanceof AN5CL_ethernet_port_baset || to instanceof AN5CL_ethernet_port_baset) {
        boolean stop = true;
    } */
    min = Integer.min(fromKeySz, toKeySz);
    max = Integer.max(fromKeySz, toKeySz);
    keyMatch = new boolean[max];
    
    i = 0;
    for (String[] fromKey : from.signatureKeys.values()) {
      String[] toKey = to.signatureKeys.get(fromKey[1]);
      if (toKey != null) {
        keyMatch[i] = true;
      } else {
    	keyMatch[i] = false;
      }
      i++;
    }
    for (j = i; j < max; j++) {
      keyMatch[j] = false;
    }
    
    fromSetSz = i = from.signatureSet.size();
    toSetSz = j = to.signatureSet.size();
      
    log.DBG(6, "AN5:MatchSignature - keys[" + fromKeySz + "," + toKeySz + "]: from[" + fromSetSz + "]: " + from.getGUID() + " to[" + toSetSz + "]: " + to.getGUID());
    
    min = Integer.min(fromSetSz, toSetSz);
    max = Integer.max(fromSetSz, toSetSz);
    overall = max + 1;
    sigMatch = new char[overall][3];
    servicesEnabled = new int[max][4];
    for (k = 0; k < max; k++) {
      sigMatch[k][0] = sigMatch[k][1] = sigMatch[k][2] = matchState.undefined;
      servicesEnabled[k][0] = servicesEnabled[k][1] = servicesEnabled[k][2] = servicesEnabled[k][3] = -1;
    }
    sigMatch[overall-1][0] = sigMatch[overall-1][1] = sigMatch[overall-1][2] = matchState.empty;
    
    for (k = 0; i > 0 && j > 0; i--, j--, k++) {
      /* go from lowest @end to highest @front */
      servicesEnabled[k][0] = i - 1;
      servicesEnabled[k][1] = from.signatureSet.get(i - 1).services.size();
      servicesEnabled[k][2] = j - 1;
      servicesEnabled[k][3] = to.signatureSet.get(j - 1).services.size();
      
      /* common <-> common */
      matched = notMatched = 0;
      fromSz = from.signatureSet.get(i - 1).common.size();
      toSz = to.signatureSet.get(j - 1).common.size();
      log.DBG(6, "  signature set - common - from sz[" + fromSz + "] to sz[" + toSz + "]");
      for (String[] fromNamVal : from.signatureSet.get(i - 1).common.values()) {
        toNamVal = to.signatureSet.get(j - 1).common.get(fromNamVal[0]);
        if (toNamVal == null) {
          notMatched++;
          log.DBG(6, "  common - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
        		     "\" to: null");

        } else {
          log.DBG(6, "  common - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
       		     "\" common - to: " + toNamVal[0] + " = \"" + toNamVal[1] + "\"");
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 0, fromSz, toSz, matched, notMatched, sigMatch, overall - 1);
      /* needs <-> provides */
      matched = notMatched = 0;
      fromSz = from.signatureSet.get(i - 1).needs.size();
      toSz = to.signatureSet.get(j - 1).provides.size();
      log.DBG(6, "  signature set - needs - from sz[" + fromSz + "] provides to sz[" + toSz + "]");
      for (String[] fromNamVal : from.signatureSet.get(i - 1).needs.values()) {
        toNamVal = to.signatureSet.get(j - 1).provides.get(fromNamVal[0]);
        if (toNamVal == null) {
          log.DBG(6, "  needs - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
      		         "\"  provides - to: null");
          notMatched++;
        } else {
          log.DBG(6, "  needs - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
         		     "\"  provides - to: " + toNamVal[0] + " = \"" + toNamVal[1] + "\"");
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 1, fromSz, toSz, matched, notMatched, sigMatch, overall - 1);
      /* provides <-> needs */
      matched = notMatched = 0;
      fromSz = from.signatureSet.get(i - 1).provides.size();
      toSz = to.signatureSet.get(j - 1).needs.size();
      log.DBG(6, "  signature set - provides - from sz[" + fromSz + "] needs to sz[" + toSz + "]");
      for (String[] fromNamVal : from.signatureSet.get(i - 1).provides.values()) {
        toNamVal = to.signatureSet.get(j - 1).needs.get(fromNamVal[0]);
        if (toNamVal == null) {
          log.DBG(6, "  provides - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
       		         "\"  needs - to: null");
          notMatched++;
        } else {
          log.DBG(6, "  provides - from: " + fromNamVal[0] + " = \"" + fromNamVal[1] +
          		     "\"  needs - to: " + toNamVal[0] + " = \"" + toNamVal[1] + "\"");
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 2, fromSz, toSz, matched, notMatched, sigMatch, overall -1);
    }
    setMatchSummary();
    for (i = 0; i < overall; i++ ) {
      log.DBG(6, "  sigMatch[" + i + "] - cm: '" + sigMatch[i][0] + "' pr: '" + sigMatch[i][1] +
        		     "' nd: '" + sigMatch[i][2] + "'");
    }
  }
  void setMatchSummary() {
	if ((sigMatch[sigMatch.length-1][0] == matchState.all ||
         sigMatch[sigMatch.length-1][0] == matchState.partial) &&
		(sigMatch[sigMatch.length-1][1] == matchState.all ||
		 sigMatch[sigMatch.length-1][1] == matchState.partial) &&
		(sigMatch[sigMatch.length-1][2] == matchState.all ||
		 sigMatch[sigMatch.length-1][2] == matchState.partial)) {
      matchResult = matchState.partial;
  	  if (sigMatch[sigMatch.length-1][0] == matchState.all &&
  		  sigMatch[sigMatch.length-1][1] == matchState.all &&
  		  sigMatch[sigMatch.length-1][2] == matchState.all) {
  	    matchResult = matchState.all;
  	  }
	}
  }
  public void matchDetails(int k, int cnp, int fromSz, int toSz, int matched, int notMatched, char[][] sigMatch, int ovIdx) {
    boolean sameSz = fromSz == toSz;
    
	if (sameSz && matched == 0 && notMatched == 0) {
      sigMatch[k][cnp] = matchState.empty;
    } else if (sameSz && matched > 0 && notMatched == 0) {
      sigMatch[k][cnp] = matchState.all;
    } else if (matched > 0 && notMatched > 0) {
      sigMatch[k][cnp] = matchState.partial;
    } else if (matched == 0 && notMatched > 0) {
      sigMatch[k][cnp] = matchState.none;
    } else if ((! sameSz) && matched == 0 && notMatched == 0) {
      sigMatch[k][cnp] = matchState.none;
    }
    /* provide overall union of results */
    if (sigMatch[ovIdx][cnp] == matchState.empty || sigMatch[k][cnp] == matchState.partial) {
      sigMatch[ovIdx][cnp] = sigMatch[k][cnp];
    } else if (sigMatch[ovIdx][cnp] == sigMatch[k][cnp]) {
      /* leave set to all */
    } else if (sigMatch[ovIdx][cnp] == matchState.all && sigMatch[k][cnp] == matchState.none) {
      sigMatch[ovIdx][cnp] = matchState.partial;
    }
  }
}
