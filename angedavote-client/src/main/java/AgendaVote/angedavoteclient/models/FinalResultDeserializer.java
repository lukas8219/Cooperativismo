package AgendaVote.angedavoteclient.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class FinalResultDeserializer extends StdDeserializer<FinalResult>{

	public FinalResultDeserializer() {
		this(null);
	}
	
	protected FinalResultDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FinalResult deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
		JsonNode root = p.getCodec().readTree(p);
		
		FinalResult result = new FinalResult();
		result.setResult(root.get("result").asText());
		
		return result;
	}

}
