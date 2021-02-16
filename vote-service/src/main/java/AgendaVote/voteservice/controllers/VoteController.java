package AgendaVote.voteservice.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	AgendaRepository agendaCollection_mongoDB;
	
	@PostMapping
	public boolean voteFor(@RequestParam(value = "agenda", required=true) UUID agendaId, 
				@RequestParam(value="cpf", required=true) String voteeCpf, 
				@RequestParam(value="decision", required=true) String voteeDecision) {
		
		voteeDecision = voteeDecision.toLowerCase();
		
		Optional<Agenda> agendaQuery = agendaCollection_mongoDB.findById(agendaId);
		
		if( CPF.checkValid(voteeCpf) == false) throw new InvalidCpfException(voteeCpf);
		
		if(agendaQuery.isEmpty()) {
			throw new InvalidAgendaIdException(agendaId);
		} else {
			Agenda foundAgenda = agendaQuery.get();
			VoteDefault newVote = new VoteDefault(voteeCpf, voteeDecision);
			
			foundAgenda.vote(newVote);
			
			agendaCollection_mongoDB.save(foundAgenda);
			
			return true;
		}
	}
	
}
