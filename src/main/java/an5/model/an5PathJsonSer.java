/**
  @what Class to support Jackson JSON serializer
 
 @note Should replace an5ConstructArguments
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class an5PathJsonSer extends an5ObjectJsonSer {
  private static final long serialVersionUID = 1L;
  public an5PathJsonSer(Class<an5Object> t) {
    super(t);
  }
  public an5PathJsonSer() {
    this(null);
  }
  @Override
  public void serialize(an5Object val, JsonGenerator gen, SerializerProvider provider) throws IOException {
	an5Path vo = (an5Path)val;
    gen.writeStartObject();
    gen.writeStringField("an5name", vo.an5name);
    gen.writeArrayFieldStart("pathList");
    for (an5Object o : vo.path.values()) {
      /* Write out each item in list */
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}