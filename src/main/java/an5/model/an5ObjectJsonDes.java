/**
 @what Class to support Jackson JSON deserializer
 
 @note Should replace an5ConstructArguments
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class an5ObjectJsonDes extends JsonDeserializer<an5Object> {
  private static final long serialVersionUID = 1L;
  /* public an5ObjectJsonDeser(Class<an5Object> t) {
    super(t);
  }
  public an5ObjectJsonDeser() {
    this(null);
  } */
  @Override
  public an5Object deserialize(JsonParser jp, DeserializationContext cxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    return new an5Object(node);  
  }
}