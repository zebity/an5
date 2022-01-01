/**
 @what Test JSON Constructors using Jackson
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import an5.model.*;
import an5.solve.*;
import an5.generic.dctypes.*;


public class JSONConstruct {
  public static void main(String[] args){
	    
    /* Switch */
    String swdef = "{\"an5name\":\"switch\",\"name\":\"simple-switch\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":24}]}";

    
    /* 12 x Computer */
    String cpdef1 = "{\"an5name\":\"computer\",\"name\":\"hal-1\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef2 = "{\"an5name\":\"computer\",\"name\":\"hal-2\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef3 = "{\"an5name\":\"computer\",\"name\":\"hal-3\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef4 = "{\"an5name\":\"computer\",\"name\":\"hal-4\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef5 = "{\"an5name\":\"computer\",\"name\":\"hal-5\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef6 = "{\"an5name\":\"computer\",\"name\":\"hal-6\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef7 = "{\"an5name\":\"computer\",\"name\":\"hal-7\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef8 = "{\"an5name\":\"computer\",\"name\":\"hal-8\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef9 = "{\"an5name\":\"computer\",\"name\":\"hal-9\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef10 = "{\"an5name\":\"computer\",\"name\":\"hal-10\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef11 = "{\"an5name\":\"computer\",\"name\":\"hal-11\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    String cpdef12 = "{\"an5name\":\"computer\",\"name\":\"hal-12\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}";
    
    /* 12 * NIC */
    String nicdef1 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef2 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef3 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef4 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef5 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef6 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef7 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef8 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef9 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef10 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef11 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    String nicdef12 = "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}";
    
    /* 13 * Cat6 */
    String cabdef1 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef2 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef3 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef4 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef5 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef6 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef7 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef8 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef9 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef10 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef11 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef12 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    String cabdef13 = "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};";
    
    AN5CL_switch sw1 = null;
	AN5CL_computer cp1 = null;
	AN5CL_computer cp2 = null;
	AN5CL_computer cp3 = null;
	AN5CL_computer cp4 = null;
	AN5CL_computer cp5 = null;
	AN5CL_computer cp6 = null;
	AN5CL_computer cp7 = null;
    AN5CL_computer cp8 = null;
	AN5CL_computer cp9 = null;
	AN5CL_computer cp10 = null;
	AN5CL_computer cp11 = null;
	AN5CL_computer cp12 = null;
    AN5CL_pcie_nic nic1 = null;
    AN5CL_pcie_nic nic2 = null;
    AN5CL_pcie_nic nic3 = null;
    AN5CL_pcie_nic nic4 = null;
    AN5CL_pcie_nic nic5 = null;
    AN5CL_pcie_nic nic6 = null;
    AN5CL_pcie_nic nic7 = null;
    AN5CL_pcie_nic nic8 = null;
    AN5CL_pcie_nic nic9 = null;
    AN5CL_pcie_nic nic10 = null;
    AN5CL_pcie_nic nic11 = null;
    AN5CL_pcie_nic nic12 = null;
    AN5CL_cat6_cable cab1 = null;
    AN5CL_cat6_cable cab2 = null;
    AN5CL_cat6_cable cab3 = null;
    AN5CL_cat6_cable cab4 = null;
    AN5CL_cat6_cable cab5 = null;
    AN5CL_cat6_cable cab6 = null;
    AN5CL_cat6_cable cab7 = null;
    AN5CL_cat6_cable cab8 = null;
    AN5CL_cat6_cable cab9 = null;
    AN5CL_cat6_cable cab10 = null;
    AN5CL_cat6_cable cab11 = null;
    AN5CL_cat6_cable cab12 = null;
    AN5CL_cat6_cable cab13 = null;
    
	try {
	  sw1 = new AN5CL_switch(new ObjectMapper().readTree(swdef));
	  cp1 = new AN5CL_computer(new ObjectMapper().readTree(cpdef1));
	  cp2 = new AN5CL_computer(new ObjectMapper().readTree(cpdef2));
	  cp3 = new AN5CL_computer(new ObjectMapper().readTree(cpdef3));
	  cp4 = new AN5CL_computer(new ObjectMapper().readTree(cpdef4));
	  cp5 = new AN5CL_computer(new ObjectMapper().readTree(cpdef5));
	  cp6 = new AN5CL_computer(new ObjectMapper().readTree(cpdef6));
	  cp7 = new AN5CL_computer(new ObjectMapper().readTree(cpdef7));
	  cp8 = new AN5CL_computer(new ObjectMapper().readTree(cpdef8));
	  cp9 = new AN5CL_computer(new ObjectMapper().readTree(cpdef9));
	  cp10 = new AN5CL_computer(new ObjectMapper().readTree(cpdef10));
	  cp11 = new AN5CL_computer(new ObjectMapper().readTree(cpdef11));
	  cp12 = new AN5CL_computer(new ObjectMapper().readTree(cpdef12));
	  nic1 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef1));
	  nic2 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef2));
	  nic3 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef3));
	  nic4 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef4));
	  nic5 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef5));
	  nic6 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef6));
	  nic7 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef7));
	  nic8 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef8));
	  nic9 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef9));
	  nic10 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef10));
	  nic11 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef11));
	  nic12 = new AN5CL_pcie_nic(new ObjectMapper().readTree(nicdef12));
	  cab1 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef1));
	  cab2 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef2));
	  cab3 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef3));
	  cab4 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef4));
	  cab5 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef5));
	  cab6 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef6));
	  cab7 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef7));
      cab8 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef8));
      cab9 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef9));
	  cab10 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef10));
	  cab11 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef11));
	  cab12 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef12));
	  cab13 = new AN5CL_cat6_cable(new ObjectMapper().readTree(cabdef13));

	} catch (JsonMappingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (JsonProcessingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    an5Object[] use = {sw1,
    		           cp1, cp2, cp3, cp4, cp5, cp6,
    		           cp7, cp8, /* cp9, cp10, cp11, cp12, */
    		           nic1, nic2, nic3, nic4, nic5, nic6,
    		           nic7, nic8, /* nic9, nic10, nic11, nic12, */
    		           cab1, cab2, cab3, cab4, cab5, cab6,
    		           cab7, cab8, cab9 /* , cab10, cab11, cab12, cab13 */};
    List<an5Object> parts = new ArrayList<>();
    for (an5Object ob : use) parts.add(ob);
    /* AN5TP_ethernet_lan  netPrototype = new AN5TP_ethernet_lan();
    AN5TP_ethernet_node nodePrototype = new AN5TP_ethernet_node();
    an5Network netResult = (an5Network)netPrototype.createInstance();
    an5Template netTemplate = new an5CreateNetwork(null, new an5Object[]{netPrototype, nodePrototype}, parts, netResult);
    an5SearchControl ctrl = new an5SearchControl();
    int offFlags1 = an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS |
    		       an5SearchControl.SearchOptions.COST;
    int offFlags2 = an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS;
    int offFlags3 = an5SearchControl.SearchOptions.COST;
    int offFlags4 = an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS |
    		        an5SearchControl.SearchOptions.BIND_UNIQUE;
    int onFlags1 = an5SearchControl.SearchOptions.DEPTH;
    // ctrl.turnOff(offFlags1);
    ctrl.turnOff(offFlags3);
    // ctrl.turnOff(offFlags4);
    ctrl.turnOn(onFlags1);
    */
    
    // an5Goal makeNet = new an5Goal(netTemplate, ctrl);
    // int res = makeNet.solve();
    
    // System.out.println("Result was: " + ctrl.resultString(res));
    // ctrl.dumpStats(System.out);

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("an5ObjectJSONSerializer");
    module.addSerializer(an5Object.class, new an5ObjectJsonSer());
    module.addSerializer(an5Element.class, new an5ElementJsonSer());
    module.addSerializer(an5Network.class, new an5NetworkJsonSer());
    module.addSerializer(AN5CL_cat6_cable.class, new AN5SR_cat6_cable());
    module.addSerializer(AN5CL_pcie_nic.class, new AN5SR_pcie_nic());
    module.addSerializer(AN5CL_computer.class, new AN5SR_computer());
    module.addSerializer(AN5CL_switch.class, new AN5SR_switch());
    module.addDeserializer(an5Object.class, new an5ObjectJsonDeser());
    /* module.addDeserializer(an5Element.class, new an5ElementJsonDeser());
    module.addDeserializer(an5Element.class, new an5ElementJsonDeser());
    module.addDeserializer(an5Network.class, new an5NetworkJsonDeser());
    module.addDeserializer(AN5CL_cat6_cable.class, new AN5DR_cat6_cable());
    module.addDeserializer(AN5CL_pcie_nic.class, new AN5DR_pcie_nic());
    module.addDeserializer(AN5CL_computer.class, new AN5DR_computer());
    module.addDeserializer(AN5CL_switch.class, new AN5DR_switch()); */
    objectMapper.registerModule(module);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    String partToJSON = new String();;

    for (an5Object o: parts) {
      try {
        partToJSON = objectMapper.writeValueAsString(o);
      } catch (JsonProcessingException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
      }
      System.out.println("part: " + o.toString() + " >>");      
      System.out.println(partToJSON);
    }
  }
}
