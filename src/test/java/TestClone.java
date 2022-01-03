/**
 @what To run Solver you have to clone each solution variation
         So need to have a robust clone logic ...
         
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
import an5.generic.dctypes.*;


public class TestClone {
  public static void main(String[] args){
	int i = 0;
		
	ObjectMapper objectMapper = new ObjectMapper();
	// SimpleModule module = new SimpleModule("an5ObjectJSONSerializer");
	SimpleModule module = new SimpleModule();
	module.addSerializer(an5Object.class, new an5ObjectJsonSer());
	module.addSerializer(an5Element.class, new an5ElementJsonSer());
	module.addSerializer(an5Network.class, new an5NetworkJsonSer());
	module.addSerializer(AN5CL_cat6_cable.class, new AN5SR_cat6_cable());
	module.addSerializer(AN5CL_pcie_nic.class, new AN5SR_pcie_nic());
	module.addSerializer(AN5CL_computer.class, new AN5SR_computer());
	module.addSerializer(AN5CL_switch.class, new AN5SR_switch());
	module.addDeserializer(an5Object.class, new an5ObjectJsonDeser());
	module.addDeserializer(an5Element.class, new an5ElementJsonDeser());
	module.addDeserializer(an5Network.class, new an5NetworkJsonDeser());
	module.addDeserializer(AN5CL_cat6_cable.class, new AN5DR_cat6_cable());
	module.addDeserializer(AN5CL_pcie_nic.class, new AN5DR_pcie_nic());
	module.addDeserializer(AN5CL_computer.class, new AN5DR_computer());
	module.addDeserializer(AN5CL_switch.class, new AN5DR_switch());
	objectMapper.registerModule(module);
	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
	/* Switch */
	String swdef = "{\"an5name\":\"switch\",\"name\":\"simple-switch\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":24,\"template\":\"p%I\"}]}";

	/* 3 x Computer */
	String[] cpdef = {"{\"an5name\":\"computer\",\"name\":\"hal-1\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4,\"template\":\"p%I\"}]}",
	      "{\"an5name\":\"computer\",\"name\":\"hal-2\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4,\"template\":\"pci%I\"}]}",
	      "{\"an5name\":\"computer\",\"name\":\"hal3\",\"reflects\":[{\"name\":\"slot\",\"policy\":\"STATIC\",\"size\":4,\"template\":\"p%I\"}]}"};
	    
	/* 4 * NIC */
	String[] nicdef = {"{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2,\"template\":\"p%I\"}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2,\"template\":\"p%I\"}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2,\"template\":\"p%I\"}]}",
	      "{\"an5name\":\"pcie_nic\",\"reflects\":[{\"name\":\"port\",\"policy\":\"STATIC\",\"size\":2,\"template\":\"p%I\"}]}"};
	    
	/* 4 * Cat6 */
	String[] cabdef = {"{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};",
	      "{\"an5name\":\"cat6_cable\",\"type\":\"physical\",\"length\":100};"};
	    
	AN5CL_switch sw1 = null;
	AN5CL_computer cp[] = {null, null, null};
	AN5CL_pcie_nic nic[] = {null, null, null, null};
	AN5CL_cat6_cable cab[] = {null, null, null, null};
	    
	try {
	  sw1 = new AN5CL_switch(new ObjectMapper().readTree(swdef));
	  for (i = 0; i < cpdef.length; i++) {
		cp[i] = objectMapper.readValue(cpdef[i], AN5CL_computer.class);
      }
      for (i = 0; i < nicdef.length; i++) {
		nic[i] = objectMapper.readValue(nicdef[i], AN5CL_pcie_nic.class);
	  }
	  for (i = 0; i < cabdef.length; i++) {
	    cab[i] = objectMapper.readValue(cabdef[i], AN5CL_cat6_cable.class);
	  }

	} catch (JsonMappingException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	} catch (JsonProcessingException e1) {
	  // TODO Auto-generated catch block
	  e1.printStackTrace();
	}

    an5Object[] use = {sw1, cp[0], cp[1], nic[0], nic[1], cab[0], cab[1] };
    List<an5Object> parts = new ArrayList<>();
    for (an5Object ob : use) parts.add(ob);
    AN5TP_ethernet_lan  netPrototype = new AN5TP_ethernet_lan();
    AN5TP_ethernet_node nodePrototype = new AN5TP_ethernet_node();
    an5Network netResult = (an5Network)netPrototype.createInstance();
    
    /* Connect up network and test clone */
    
    System.out.println("switch reports as" + sw1.toString());
  }

}
