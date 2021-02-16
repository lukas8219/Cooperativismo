package AgendaVote.openvotationservice.controllers;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AgendaVote.openvotationservice.OpenVotationServiceApplication;
import AgendaVote.openvotationservice.models.Agenda;
import AgendaVote.openvotationservice.models.repository.AgendaRepo;
import exception.InvalidAgendaIdException;
import exception.OpenVoteException;

@RestController
@RequestMapping("/")
public class OpenVoteController {

	@Autowired
	AgendaRepo agendaCollection_mongoDB;
	
	@PutMapping()
	public void openVote(@RequestParam(value = "id", required=true) UUID agendaId,
							@RequestParam(value="time", required=false) Integer forMinutes) throws Exception {
		
		if(forMinutes == null) forMinutes = 1;
		
		Optional<Agenda> agendaQuery = agendaCollection_mongoDB.findById(agendaId);
		
		if(agendaQuery.isEmpty()) {
			throw new InvalidAgendaIdException(agendaId);
		} else {
			Agenda foundAgenda = agendaQuery.get();
			if(foundAgenda.getVotationExpirationDate() == null) {
				foundAgenda.openVote(forMinutes);
				agendaCollection_mongoDB.save(foundAgenda);
			} else {
				throw new OpenVoteException(foundAgenda.getId());
			}
		}
	}
}
