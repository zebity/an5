/**
  @what Class to support Jackson JSON serializer
 
 @note Should replace an5ConstructArguments
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.io.IOException;
import java.io.PrintStream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import an5.model.an5Network.Cloned;

public class an5NetworkJsonSer extends an5ObjectJsonSer {
  private static final long serialVersionUID = 1L;
  public an5NetworkJsonSer(Class<an5Object> t) {
    super(t);
  }
  public an5NetworkJsonSer() {
    this(null);
  }
  @Override
  public void serialize(an5Object val, JsonGenerator gen, SerializerProvider provider) throws IOException {
	an5Network vo = (an5Network)val;
    int i = 0;	
	if (vo.cloned) {
      vo.decoupleClone(vo.clonedFrom, Cloned.ALL);
	}
	gen.writeStartObject();
	gen.writeStringField("an5name", vo.an5name);
	
	gen.writeFieldName("services");
    gen.writeStartArray();	
	for (i = 0; i < vo.AN5AT_serviceUnion.size(); i++) {
	  gen.writeStartObject();
	  gen.writeStringField("name", vo.AN5AT_serviceUnion.getService(i));
	  int[] cd = vo.AN5AT_serviceUnion.getCardinality(i);
	  gen.writeNumberField("min", cd[0]);
	  gen.writeNumberField("max", cd[1]);
	  gen.writeEndObject();
    }
	gen.writeEndArray();
	
	gen.writeFieldName("members");
    gen.writeStartArray();
	for (an5Object o: vo.members.values()) {
	  gen.writeStartObject();
	  gen.writeStringField("class",  o.getClass().getSimpleName());
	  gen.writeStringField("GUID",  o.getGUID());
	  gen.writeEndObject();
    }
	gen.writeEndArray();
	
	gen.writeFieldName("candidates");
    gen.writeStartArray();
	for (an5Object o: vo.candidates.values()) {
	  gen.writeStartObject();
	  gen.writeStringField("class",  o.getClass().getSimpleName());
	  gen.writeStringField("GUID",  o.getGUID());
	  an5Path po = (an5Path)o;
	  gen.writeFieldName("path");
	  gen.writeStartArray();
	  for (an5Object pmo: po.path.values()) {
		 gen.writeStartObject();
		 gen.writeStringField("class",  pmo.getClass().getSimpleName());
		 gen.writeStringField("GUID",  pmo.getGUID());
		 gen.writeEndObject();
	  }
	  gen.writeEndArray();
	  gen.writeEndObject();
    }
	gen.writeEndArray();

	gen.writeFieldName("extends");
	super.serialize(val, gen, provider);
	gen.writeEndObject();
  }
}