package AgendaVote.voteservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import defaultObject.AgendaDefault;

@Document(collection = "agenda")
public class Agenda extends AgendaDefault {

}
