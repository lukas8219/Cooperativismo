package AgendaVote.newagendaservice.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import AgendaVote.newagendaservice.models.Agenda;
import AgendaVote.newagendaservice.repository.AgendaRepository;
import defaultObject.utility.Result;
import exception.InvalidAgendaIdException;
import interfaces.ResultInterface;

@RestController
@RequestMapping("/")
public class AgendaController{

	
	@Autowired
	AgendaRepository agendaRepo;
	
	@PutMapping("/open/")
	public String newAgenda(@RequestBody Agenda pauta) {
		agendaRepo.save(pauta);
		return "done";
	}
	
	@GetMapping
	public List<Agenda> test(){
		return agendaRepo.findAll();
	}
	
	@GetMapping("/check/{agendaId}")
	public ResultInterface getResult(@PathVariable("agendaId") UUID agendaId) throws Exception {
		
		Optional<Agenda> agenda = agendaRepo.findById(agendaId);
		
		if( ! agenda.isEmpty()) {
			Agenda currentAgenda = agenda.get();
			return Result.getResult(currentAgenda);
		} else {
			System.out.println("teste");
			throw new InvalidAgendaIdException(agendaId);
		}
	}
}
