// package an5.test;

// import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import an5.model.*;
import an5.solve.*;
import an5.generic.dctypes.*;


public class BuildMiniNetwork {
  public static void main(String[] args){
	// System.out.println(args[0]);  
    // Scanner s = new Scanner(System.in);
    // System.out.println("Enter file to load: ");
    // String str = s.next();
    // s.close();
	
    
    /* Switch */
    String[][] swdef = {{"name", "simple-switch"},
    		            {"port", "[24]", "p%I"}};
    
    /* 3 x Computer */
    String[][] cpdef1 = {{"name", "hal-1"},
            {"slot", "[4]", "pci%I"}};
    String[][] cpdef2 = {{"name", "hal-2"},
            {"slot", "[4]", "pci%I"}};
    String[][] cpdef3 = {{"name", "hal-3"},
            {"slot", "[4]", "pci%I"}};
    
    /* 4 * NIC */
    String[][] nicdef1 = {{"port", "[2]", "p%I"}};
    String[][] nicdef2 = {{"port", "[2]", "p%I"}};
    String[][] nicdef3 = {{"port", "[2]", "p%I"}};
    String[][] nicdef4 = {{"port", "[2]", "p%I"}};

    String[][] cabdef1 = {{"type", "physical"},
    		              {"length", "1"}};
    String[][] cabdef2 = {{"type", "physical"},
                          {"length", "1"}};
    String[][] cabdef3 = {{"type", "physical"},
                          {"length", "1"}};
    String[][] cabdef4 = {{"type", "physical"},
                          {"length", "1"}};
    
    AN5CL_switch sw1 = new AN5CL_switch(new an5ConstructArguments(swdef));
        
    AN5CL_computer cp1 = new AN5CL_computer(new an5ConstructArguments(cpdef1));
    AN5CL_computer cp2 = new AN5CL_computer(new an5ConstructArguments(cpdef2));
    AN5CL_computer cp3 = new AN5CL_computer(new an5ConstructArguments(cpdef3));

    AN5CL_pcie_nic nic1 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef1));
    AN5CL_pcie_nic nic2 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef2));
    AN5CL_pcie_nic nic3 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef3));
    AN5CL_pcie_nic nic4 = new AN5CL_pcie_nic(new an5ConstructArguments(nicdef4));

    AN5CL_cat6_cable cab1 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef1));
    AN5CL_cat6_cable cab2 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef2));
    AN5CL_cat6_cable cab3 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef3));
    AN5CL_cat6_cable cab4 = new AN5CL_cat6_cable(new an5ConstructArguments(cabdef4));


    an5Object[] use = {sw1, cp1, cp2, cp3, nic1, nic2, nic3, nic4, cab1, cab2, cab3, cab4 };
    List<an5Object> parts = new ArrayList<>();
    for (an5Object ob : use) parts.add(ob);
    AN5TP_ethernet_lan  netTemplate = new AN5TP_ethernet_lan();
    an5Network netResult = (an5Network)netTemplate.createInstance();
    an5Template netType = new an5CreateNetwork(netTemplate);
    
    an5Goal makeNet = new an5Goal(netType, netResult, parts);
    int res = makeNet.solve();
    
    System.out.println("switch reports as" + sw1.toString());
  }

}
