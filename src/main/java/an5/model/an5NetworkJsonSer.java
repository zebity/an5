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
	gen.writeStartObject();
	gen.writeStringField("an5name", vo.an5name);
	gen.writeFieldName("extends");
	super.serialize(val, gen, provider);
	gen.writeEndObject();
  }
}