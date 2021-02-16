package AgendaVote.newagendaservice.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import AgendaVote.newagendaservice.models.Agenda;
import AgendaVote.newagendaservice.repository.AgendaRepository;
import defaultObject.utility.Result;
import exception.InvalidAgendaIdException;
import interfaces.ResultInterface;


/**
 * Controller Class:
 * Mapping : "/open/", "/", "/check/{agendaId"
 */
@RestController
@RequestMapping("/")
public class AgendaController{

	
	@Autowired
	AgendaRepository agendaCollection_mongoDB;
	
	/**
	 * Mapping to publish new Agenda
	 * @param newAgenda - JSON Object containing fields name, description.
	 */
	@PostMapping("/open/")
	public Agenda publishNewAgenda(@RequestBody Agenda newAgenda) {
		agendaCollection_mongoDB.save(newAgenda);
		return newAgenda;
	}
	
	
	/**
	 * Mapping function to return all Agendas.
	 * @return List of Agenda Objects
	 */
	@GetMapping
	public List<Agenda> getAllAgenda(){
		return agendaCollection_mongoDB.findAll();
	}
	
	
	/**
	 * Mapping function to return the votation result.
	 * @param agendaId
	 * @return A Result Object
	 * @throws Exception such as Invalid Agenda, default Exception, VotationOccuring Exception.
	 */
	@GetMapping("/check/{agendaId}")
	public ResultInterface getVotationResult(@PathVariable("agendaId") UUID agendaId) throws Exception {
		
		Optional<Agenda> agendaQuery = agendaCollection_mongoDB.findById(agendaId);
		
		if( ! agendaQuery.isEmpty()) {
			Agenda foundAgenda = agendaQuery.get();
			return Result.getResult(foundAgenda);
		} else {
			throw new InvalidAgendaIdException(agendaId);
		}
	}
}
