/**
  @what Class to support Jackson JSON serializer
 
 @note Should replace an5ConstructArguments
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class an5ObjectJsonSer extends StdSerializer<an5Object> {
  private static final long serialVersionUID = 1L;
  public an5ObjectJsonSer(Class<an5Object> t) {
    super(t);
  }
  public an5ObjectJsonSer() {
    this(null);
  }
  @Override
  public void serialize(an5Object val, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("an5name", val.an5name);
    // gen.writeStartObject();
    gen.writeBooleanField("goalSpec", val.goalSpec);
    gen.writeBooleanField("mandatory", val.mandatory);
    gen.writeStringField("GUID", val.getGUID());
    if (val.persistentUniqueId[0] != null) {
      gen.writeArrayFieldStart("persistentUniqueId");
      gen.writeArray(val.persistentUniqueId, 0, val.persistentUniqueId.length);
    }
    /* for (an5VariableInstance var: val.AN5AT_classes.values()) {
      var.	
    } */
    // gen.writeEndObject();
    gen.writeEndObject();
  }
}