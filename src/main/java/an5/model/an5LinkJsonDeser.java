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

public class an5LinkJsonDeser extends JsonDeserializer<an5Link> {
  private static final long serialVersionUID = 1L;
  /* public an5LinkJsonDeser(Class<an5Object> t) {
    super(t);
  }
  public an5LinkJsonDeser() {
    this(null);
  } */
  @Override
  public an5Link deserialize(JsonParser jp, DeserializationContext cxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    return new an5Link(node);
    
  }
}