package AgendaVote.angedavoteclient.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import AgendaVote.angedavoteclient.models.FinalResult;
import defaultObject.AgendaDefault;

/**
 * Client API to deal with all 3 microservices
 * @author Lucas
 *
 */
@RestController
@RequestMapping("/")
public class ClientController {

	@Autowired
	RestTemplate eurekaServer;
	
	/**
	 * Creates a new Agend
	 * @param agenda
	 * @return the Agenda object with the generated ID.
	 */
	@PostMapping("/new/")
	public AgendaDefault newAgenda(@RequestBody AgendaDefault agenda) {
		return eurekaServer.postForObject("http://new-agenda-service/open/", agenda, AgendaDefault.class);
	}
	
	/**
	 * Open the votation for a Agenda.
	 * @param agendaId
	 * @param forMinutes
	 */
	@PutMapping("/open/")
	public void openVotation(@RequestParam(value = "agenda", required=true) UUID agendaId, 
							@RequestParam(value="timeLimit", required=false) Integer forMinutes) {
		
		if(forMinutes == null) {
			eurekaServer.put("http://open-vote-service/?id="+agendaId, forMinutes);
		} else {
			eurekaServer.put("http://open-vote-service/?id="+agendaId+"?time="+forMinutes, null);
		}
		
	}
	
	/**
	 * Call the final result
	 * @param agendaId
	 * @return a JsonObject containing the field "result" which can be Approved, Refused or Tied.
	 */
	@GetMapping("/result/{agendaId}")
	public FinalResult getResult(@PathVariable("agendaId") UUID agendaId) {
		return eurekaServer.getForObject("http://new-agenda-service/check/"+agendaId, FinalResult.class);
	}
	
	/**
	 * Cast a vote.
	 * @param agendaId
	 * @param cpf
	 * @param voteDecision YES/NO
	 * @return boolean TRUE if the vote was counted.
	 */
	@PostMapping("/vote/")
	public Boolean vote(@RequestParam(value="agendaId", required=true) UUID agendaId,
					@RequestParam(value="cpf", required=true) String cpf,
					@RequestParam(value="vote", required=true) String voteDecision) {
		
		return eurekaServer.postForObject("http://vote-service/?agenda="+agendaId+"&cpf="+cpf+"&decision="+voteDecision,
				null, Boolean.class);
	}
	
}
