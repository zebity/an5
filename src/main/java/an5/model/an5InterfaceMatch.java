package an5.model;

public class an5InterfaceMatch {
  char all = 'a',
	   none = 'n',
	   partial = 'p',
	   empty = 'e';
  public int fromKeySz = 0,
	         toKeySz = 0,
	         fromSetSz = 0,
	         toSetSz = 0;
  public boolean[] keyMatch = null;
  public char[][] sigMatch = null;
  public int[][] servicesEnabled = null;
  
  void matchSignature(an5Interface from, an5Interface to) {
	int i, j, k,
	    min, max, overall,
	    matched = 0, notMatched = 0;
	boolean sameSz = true;
    fromKeySz = from.signatureKeys.size();
    toKeySz = to.signatureKeys.size();
    String[] toNamVal = null;
    
    min = Integer.min(fromKeySz, toKeySz);
    max = Integer.max(fromKeySz, toKeySz);
    keyMatch = new boolean[max];
    
    for (i = 0; i < min; i++) {
      keyMatch[i] = from.signatureKeys.get(i)[1].equals(to.signatureKeys.get(i)[1]);
    }
    for (j = min; j < max; j++) {
      keyMatch[j] = false;
    }
    
    fromSetSz = i = from.signatureSet.size();
    toSetSz = j = to.signatureSet.size();
      
    min = Integer.min(fromSetSz, toSetSz);
    max = Integer.max(fromSetSz, toSetSz);
    overall = max + 1;
    sigMatch = new char[overall][3];
    for (k = 0; k < 3; k++) {
      sigMatch[overall-1][k] = empty;
    }
    servicesEnabled = new int[max][4];
    for (k = 0; k < max; k++) {
      servicesEnabled[k][0] = servicesEnabled[k][1] = servicesEnabled[k][2] = servicesEnabled[k][3] = -1;
    }
    
    for (k = 0; i > 0 && j > 0; i--, j--, k++) {
      /* common <-> common */
      matched = notMatched = 0;
      servicesEnabled[k][0] = i - 1;
      servicesEnabled[k][1] = from.signatureSet.get(i - 1).services.size();
      servicesEnabled[k][2] = j - 1;
      servicesEnabled[k][3] = to.signatureSet.get(j - 1).services.size();
      sameSz = from.signatureSet.get(i - 1).common.size() == from.signatureSet.get(j - 1).common.size();
      for (String[] fromNamVal : from.signatureSet.get(i - 1).common.values()) {
        toNamVal = to.signatureSet.get(j - 1).common.get(fromNamVal[0]);
        if (toNamVal == null) {
          notMatched++;
        } else {
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 0, sameSz, matched, notMatched, sigMatch, overall - 1);
      /* needs <-> provides */
      matched = notMatched = 0;
      sameSz = from.signatureSet.get(i - 1).needs.size() == from.signatureSet.get(j - 1).provides.size();
      for (String[] fromNamVal : from.signatureSet.get(i - 1).needs.values()) {
        toNamVal = to.signatureSet.get(j - 1).provides.get(fromNamVal[0]);
        if (toNamVal == null) {
          notMatched++;
        } else {
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 1, sameSz, matched, notMatched, sigMatch, overall - 1);
      /* provides <-> needs */
      matched = notMatched = 0;
      sameSz = from.signatureSet.get(i - 1).provides.size() == from.signatureSet.get(j - 1).needs.size();
      for (String[] fromNamVal : from.signatureSet.get(i - 1).provides.values()) {
        toNamVal = to.signatureSet.get(j - 1).needs.get(fromNamVal[0]);
        if (toNamVal == null) {
          notMatched++;
        } else {
          if (fromNamVal[1].equals(toNamVal[1])) {
    	    matched++;
    	  }
        }
      }
      matchDetails(k, 2, sameSz, matched, notMatched, sigMatch, overall -1);
    }
  }
  public void matchDetails(int k, int cnp, boolean sameSz, int matched, int notMatched, char[][] sigMatch, int ovIdx) {
    if (sameSz && matched == 0 && notMatched == 0) {
      sigMatch[k][cnp] = empty;
    } else if (sameSz && matched > 0 && notMatched == 0) {
      sigMatch[k][cnp] = all;
    } else if (matched > 0 && notMatched > 0) {
      sigMatch[k][cnp] = partial;
    } else if (matched == 0 && notMatched > 0) {
      sigMatch[k][cnp] = none;
    }
    /* provide overall union of results */
    if (sigMatch[ovIdx][cnp] == empty || sigMatch[k][cnp] == partial) {
      sigMatch[ovIdx][cnp] = sigMatch[k][cnp];
    } else if (sigMatch[ovIdx][cnp] == sigMatch[k][cnp]) {
      /* leave set to all */
    } else if (sigMatch[ovIdx][cnp] == all && sigMatch[k][cnp] == none) {
      sigMatch[ovIdx][cnp] = partial;
    }
  }
}
