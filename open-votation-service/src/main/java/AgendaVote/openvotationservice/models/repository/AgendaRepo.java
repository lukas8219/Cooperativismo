package AgendaVote.openvotationservice.models.repository;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AgendaVote.openvotationservice.models.Agenda;
import defaultObject.AgendaDefault;
import interfaces.AgendaInterface;

@Repository
public interface AgendaRepo extends MongoRepository<Agenda, UUID>{

}
