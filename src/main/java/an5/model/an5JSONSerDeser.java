package an5.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class an5JSONSerDeser {
	private static ObjectMapper objectMapper = new ObjectMapper();
	// SimpleModule module = new SimpleModule("an5ObjectJSONSerializer");
	private static SimpleModule module = new SimpleModule();
	private static boolean stage1 = stage1Init();
	private static boolean stage2 = false;
	
	static boolean stage1Init() {
	  module.addSerializer(an5Object.class, new an5ObjectJsonSer());
	  module.addSerializer(an5Element.class, new an5ElementJsonSer());
	  module.addSerializer(an5Network.class, new an5NetworkJsonSer());
	  module.addDeserializer(an5Object.class, new an5ObjectJsonDes());
	  module.addDeserializer(an5Element.class, new an5ElementJsonDes());
	  module.addDeserializer(an5Network.class, new an5NetworkJsonDes());
	  return true;
	}
	
	public static void addSerDeser(Class forClass, JsonSerializer<?> ser, JsonDeserializer<?> deser) {
	  module.addSerializer(forClass, ser);
	  module.addDeserializer(forClass, deser);
	}
	public static boolean stage2Init() {
	  objectMapper.registerModule(module);
	  objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	  objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  stage2 = true;
	  return stage2;
	}
	public static ObjectMapper mapper() {
	  return objectMapper;
	}
}
