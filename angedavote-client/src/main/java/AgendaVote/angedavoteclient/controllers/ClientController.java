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

@RestController
@RequestMapping("/")
public class ClientController {

	@Autowired
	RestTemplate serverConnection;
	
	@PostMapping("/new/")
	public AgendaDefault newAgenda(@RequestBody AgendaDefault agenda) {
		return serverConnection.postForObject("http://new-agenda-service/open/", agenda, AgendaDefault.class);
	}
	
	@PutMapping("/open/")
	public void openVotation(@RequestParam(value = "agenda", required=true) UUID agendaId, 
							@RequestParam(value="timeLimit", required=false) Integer forMinutes) {
		
		if(forMinutes == null) {
			serverConnection.put("http://open-vote-service/?id="+agendaId, forMinutes);
		} else {
			serverConnection.put("http://open-vote-service/?id="+agendaId+"?time="+forMinutes, null);
		}
		
	}
	
	@GetMapping("/result/{agendaId}")
	public FinalResult getResult(@PathVariable("agendaId") UUID agendaId) {
		return serverConnection.getForObject("http://new-agenda-service/check/"+agendaId, FinalResult.class);
	}
	
	@PostMapping("/vote/")
	public Boolean vote(@RequestParam(value="agendaId", required=true) UUID agendaId,
					@RequestParam(value="cpf", required=true) String cpf,
					@RequestParam(value="vote", required=true) String voteDecision) {
		
		return serverConnection.postForObject("http://vote-service/?agenda="+agendaId+"&cpf="+cpf+"&decision="+voteDecision,
				null, Boolean.class);
	}
	
}
