/**
 @what An example network create test file
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import an5.model.*;
import an5.solve.*;
import an5.generic.dctypes.*;


public class BuildMiniNetwork {
  public static void main(String[] args){
    int i = 0;
    
	/* ObjectMapper objectMapper = new ObjectMapper();
    // SimpleModule module = new SimpleModule("an5ObjectJSONSerializer");
	SimpleModule module = new SimpleModule();
	module.addSerializer(an5Object.class, new an5ObjectJsonSer());
	module.addSerializer(an5Element.class, new an5ElementJsonSer());
	module.addSerializer(an5Network.class, new an5NetworkJsonSer());
	module.addSerializer(AN5CL_cat6_cable.class, new AN5SR_cat6_cable());
	module.addSerializer(AN5CL_pcie_nic.class, new AN5SR_pcie_nic());
	module.addSerializer(AN5CL_computer.class, new AN5SR_computer());
	module.addSerializer(AN5CL_switch.class, new AN5SR_switch());
	module.addDeserializer(an5Object.class, new an5ObjectJsonDes());
	module.addDeserializer(an5Element.class, new an5ElementJsonDes());
	module.addDeserializer(an5Network.class, new an5NetworkJsonDes());
	module.addDeserializer(AN5CL_cat6_cable.class, new AN5DR_cat6_cable());
	module.addDeserializer(AN5CL_pcie_nic.class, new AN5DR_pcie_nic());
	module.addDeserializer(AN5CL_computer.class, new AN5DR_computer());
	module.addDeserializer(AN5CL_switch.class, new AN5DR_switch());
	objectMapper.registerModule(module);
	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); */
    
    an5JSONSerDeser.stage2Init();
		
	/* Switch */
	String swdef = "{\"an5name\":\"switch\",\"name\":\"simple-switch\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":24}]}";

	/* 12 x Computer */
	String[] cpdef = {"{\"an5name\":\"computer\",\"name\":\"hal-1\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-2\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-3\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-4\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-5\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-6\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-7\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-8\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-9\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-10\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-11\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}",
	  "{\"an5name\":\"computer\",\"name\":\"hal-12\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4}]}"};
	    
	/* 12 * NIC */
	String[] nicdef = {"{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2}]}"};
	    
	/* 13 * Cat6 */
	String[] cabdef = {"{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};"};
	    
	AN5CL_switch sw1 = null;
    AN5CL_computer cp[] = {null, null, null, null, null, null, null, null, null, null, null, null};
    AN5CL_pcie_nic nic[] = {null, null, null, null, null, null, null, null, null, null, null, null};
	AN5CL_cat6_cable cab[] = {null, null, null, null, null, null, null, null, null, null, null, null, null};
	    
	try {
	  sw1 = new AN5CL_switch(new ObjectMapper().readTree(swdef));
	  for (i = 0; i < cpdef.length; i++) {
		cp[i] = an5JSONSerDeser.mapper().readValue(cpdef[i], AN5CL_computer.class);
      }
	  for (i = 0; i < nicdef.length; i++) {
	    nic[i] = an5JSONSerDeser.mapper().readValue(nicdef[i], AN5CL_pcie_nic.class);
	  }
	  for (i = 0; i < cabdef.length; i++) {
		cab[i] = an5JSONSerDeser.mapper().readValue(cabdef[i], AN5CL_cat6_cable.class);
      }

	} catch (JsonMappingException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	} catch (JsonProcessingException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	}
	    
	an5Object[] use = {sw1,
	    		       cp[0], cp[1], cp[2], cp[3], cp[4], cp[5],
	    		       cp[6], cp[7], cp[8], cp[9], /* cp11, cp12, */
	    		       nic[0], nic[1], nic[2], nic[3], nic[4], nic[5],
	    		       nic[6], nic[7], nic[8], nic[9], /* nic[10], nic[11], */
	    		       cab[0], cab[1], cab[2], cab[3], cab[4], cab[5],
	    		       cab[6], cab[7], cab[8], cab[9], cab[10] /* , cab[11], cab[12] */};

    List<an5Object> parts = new ArrayList<>();
    for (an5Object ob : use) parts.add(ob);
    
    AN5TP_ethernet_lan  netPrototype = new AN5TP_ethernet_lan();
    // AN5TP_ethernet_node nodePrototype = new AN5TP_ethernet_node();
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
    // ctrl.turnOff(offFlags3);
    // ctrl.turnOff(offFlags4);
    // ctrl.turnOn(onFlags1);
    
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
