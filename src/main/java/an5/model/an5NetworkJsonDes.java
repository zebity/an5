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

public class an5NetworkJsonDes extends JsonDeserializer<an5Network> {
  /* private static final long serialVersionUID = 1L;
  public an5NetworkJsonDeser(Class<an5Object> t) {
    super(t);
  }
  public an5NetworkJsonDeser() {
    this(null);
  } */
  @Override
  public an5Network deserialize(JsonParser jp, DeserializationContext cxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    an5Network res = new an5Network(node);
    return res;
  }
}