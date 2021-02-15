package AgendaVote.openvotationservice.controllers;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import AgendaVote.openvotationservice.models.Agenda;
import AgendaVote.openvotationservice.models.repository.AgendaRepo;

@RestController
@RequestMapping("/")
public class OpenVoteController {

	@Autowired
	AgendaRepo agendaRepo;
	
	@PutMapping()
	public String openVote(@RequestParam(value = "id", required=true) UUID id,
							@RequestParam(value="time", required=false) Integer minutes) {
		
		if(minutes == null) minutes = 1;
		
		Optional<Agenda> agenda = agendaRepo.findById(id);
		
		if(agenda.isEmpty()) {
			return "No Agenda Found";
		} else {
			Agenda currentAgenda = agenda.get();
			if(currentAgenda.getVotationExpirationDate() == null) {
				currentAgenda.openVote(minutes);
				agendaRepo.save(currentAgenda);
				return "Vote Opened";
			} else {
				return "Votation already opened!";
			}
		}
	}
}
