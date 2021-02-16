package AgendaVote.voteservice.models;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Helper class to validate CPF with external API
 * user-info.herokuapp.com/user/{cpf} 
 */
public class CPF {
	
	/**
	 * Validates CPF
	 * @param cpf - votee CPF
	 * @return boolean - True if Valid, False if Invalid
	 */
	public static boolean checkValid(String cpf) {
		
		RestTemplate restRequest = new RestTemplate();
		
		JsonNode node = restRequest.getForObject("https://user-info.herokuapp.com/users/"+cpf, JsonNode.class);
		String cpfStatus = node.get("status").asText();
		
		if(cpfStatus.equals("ABLE_TO_VOTE")) {
			return true;
		} else if (cpfStatus.equals("UNABLE_TO_VOTE")) {
			return false;
		} else {
			return false;
		}
	}
	
}
