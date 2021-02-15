package AgendaVote.voteservice.models.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AgendaVote.voteservice.models.Agenda;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, UUID>{

}
