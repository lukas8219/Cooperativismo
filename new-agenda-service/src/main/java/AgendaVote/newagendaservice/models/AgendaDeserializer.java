package AgendaVote.newagendaservice.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class AgendaDeserializer extends StdDeserializer<Agenda>{

	public AgendaDeserializer() {
		this(null);
	}
	
	protected AgendaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Agenda deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		JsonNode root = p.getCodec().readTree(p);
		
		return new Agenda(root.get("name").asText(), root.get("description").asText());
	}

}
