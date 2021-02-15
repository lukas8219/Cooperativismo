package AgendaVote.voteservice.models;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class CPF {

	public static boolean checkValid(String cpf) {
		RestTemplate rest = new RestTemplate();
		
		JsonNode node = rest.getForObject("https://user-info.herokuapp.com/users/"+cpf, JsonNode.class);
		String validation = node.get("status").asText();
		
		if(validation.equals("ABLE_TO_VOTE")) {
			return true;
		} else if (validation.equals("UNABLE_TO_VOTE")) {
			return false;
		} else {
			return false;
		}
	}
	
}
