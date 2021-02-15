package AgendaVote.newagendaservice.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AgendaVote.newagendaservice.models.Agenda;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, UUID>{

}
