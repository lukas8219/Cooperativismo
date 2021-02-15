package AgendaVote.voteservice.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AgendaVote.voteservice.models.Agenda;
import AgendaVote.voteservice.models.CPF;
import AgendaVote.voteservice.models.repository.AgendaRepository;
import defaultObject.VoteDefault;
import exception.InvalidAgendaIdException;
import exception.InvalidCpfException;

@RestController
@RequestMapping("/")
public class VoteController {

	@Autowired
	AgendaRepository agendaRepo;
	
	@PutMapping
	public boolean voteFor(@RequestParam(value = "agenda", required=true) UUID agendaId, 
				@RequestParam(value="cpf", required=true) String cpf, 
				@RequestParam(value="decision", required=true) String decision) throws Exception {
		
		Optional<Agenda> agenda = agendaRepo.findById(agendaId);
		
		if(CPF.checkValid(cpf) == false) throw new InvalidCpfException(cpf);
		
		if(agenda.isEmpty()) {
			throw new InvalidAgendaIdException(agendaId);
		} else {
			Agenda currentAgenda = agenda.get();
			VoteDefault newVote = new VoteDefault(cpf, decision.toLowerCase());
			
			currentAgenda.vote(newVote);
			
			agendaRepo.save(currentAgenda);
			
			return true;
		}
	}
	
}
