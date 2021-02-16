package AgendaVote.newagendaservice.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializer class
 *
 */
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

		JsonNode rootNode = p.getCodec().readTree(p);
		String name = rootNode.get("name").asText();
		String description = rootNode.get("description").asText();

		return new Agenda(name, description);
	}

}
