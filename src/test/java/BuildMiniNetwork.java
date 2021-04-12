/**
 @what An example network create test file
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

import java.util.ArrayList;
import java.util.List;

import an5.model.*;
import an5.solve.*;
import an5.generic.dctypes.*;


public class BuildMiniNetwork {
  public static void main(String[] args){
	    
    /* Switch */
    String[][] swdef = {{"name", "simple-switch"},
    		            {"port", "[24]"}};
    
    /* 3 x Computer */
    String[][] cpdef1 = {{"name", "hal-1"},
            {"slot", "[4]"}};
    String[][] cpdef2 = {{"name", "hal-2"},
            {"slot", "[4]"}};
    String[][] cpdef3 = {{"name", "hal-3"},
            {"slot", "[4]"}};
    String[][] cpdef4 = {{"name", "hal-4"},
            {"slot", "[4]"}};
    String[][] cpdef5 = {{"name", "hal-5"},
            {"slot", "[4]"}};
    String[][] cpdef6 = {{"name", "hal-6"},
            {"slot", "[4]"}};
    
    /* 4 * NIC */
    String[][] nicdef1 = {{"port", "[2]"}};
    String[][] nicdef2 = {{"port", "[2]"}};
    String[][] nicdef3 = {{"port", "[2]"}};
    String[][] nicdef4 = {{"port", "[2]"}};
    String[][] nicdef5 = {{"port", "[2]"}};
    String[][] nicdef6 = {{"port", "[2]"}};

    /* 4 * Cat6 */
    String[][] cabdef1 = {{"type", "physical"},
    		              {"length", "1"}};
    String[][] cabdef2 = {{"type", "physical"},
                          {"length", "1"}};
    String[][] cabdef3 = {{"type", "physical"},
                          {"length", "1"}};
    String[][] cabdef4 = {{"type", "physical"},
                          {"length", "1"}};
    String[][] cabdef5 = {{"type", "physical"},
            {"length", "1"}};
    String[][] cabdef6 = {{"type", "physical"},
            {"length", "1"}};
    
    AN5CL_switch sw1 = new AN5CL_switch(new an5ConstructArguments(swdef));
        
    AN5CL_computer cp1 = new AN5CL_computer(new an5ConstructArguments(cpdef1));
    AN5CL_computer cp2 = new AN5CL_computer(new an5ConstructArguments(cpdef2));
    AN5CL_computer cp3 = new AN5CL_computer(new an5ConstructArguments(cpdef3));
    AN5CL_computer cp4 = new AN5CL_computer(new an5ConstructArguments(cpdef4));
    AN5CL_computer cp5 = new AN5CL_computer(new an5ConstructArguments(cpdef5));
    AN5CL_computer cp6 = new AN5CL_computer(new an5ConstructArguments(cpdef6));

    AN5CL_pcie_nic nic1 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef1));
    AN5CL_pcie_nic nic2 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef2));
    AN5CL_pcie_nic nic3 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef3));
    AN5CL_pcie_nic nic4 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef4));
    AN5CL_pcie_nic nic5 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef5));
    AN5CL_pcie_nic nic6 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef6));

    AN5CL_cat6_cable cab1 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef1));
    AN5CL_cat6_cable cab2 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef2));
    AN5CL_cat6_cable cab3 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef3));
    AN5CL_cat6_cable cab4 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef4));
    AN5CL_cat6_cable cab5 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef5));
    AN5CL_cat6_cable cab6 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef6));


    an5Object[] use = {sw1,
    		           cp1, cp2, cp3, cp4, /* cp5, cp6, */
    		           nic1, nic2, nic3, nic4, nic5, /* nic6, */
    		           cab1, cab2, cab3, cab4 , cab5, cab6 };
    List<an5Object> parts = new ArrayList<>();
    for (an5Object ob : use) parts.add(ob);
    AN5TP_ethernet_lan  netPrototype = new AN5TP_ethernet_lan();
    AN5TP_ethernet_node nodePrototype = new AN5TP_ethernet_node();
    an5Network netResult = (an5Network)netPrototype.createInstance();
    an5Template netTemplate = new an5CreateNetwork(null, new an5Object[]{netPrototype, nodePrototype}, parts, netResult);
    an5SearchControl ctrl = new an5SearchControl();
    int offFlags1 = an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS |
    		       an5SearchControl.SearchOptions.COST;
    int offFlags2 = an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS;
    int onFlags = an5SearchControl.SearchOptions.DEPTH;
    ctrl.turnOff(offFlags2);
    // ctrl.turnOn(onFlags);
    
    an5Goal makeNet = new an5Goal(netTemplate, ctrl);
    int res = makeNet.solve();
    
    System.out.println("Result was: " + ctrl.resultString(res));
    ctrl.dumpStats(System.out);
    
    for (an5GoalTree t: makeNet.found) {
      an5FoundGoal resGoal = (an5FoundGoal)t;
      if (resGoal.goal instanceof an5JoinNetwork) {
    	an5JoinNetwork jn =  (an5JoinNetwork)(resGoal.goal);
    	an5Network no = jn.joinNet;
    	no.dumpJSON(System.out);
      }
    }
    System.out.println("switch reports as" + sw1.toString());
  }

}
