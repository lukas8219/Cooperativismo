package AgendaVote.voteservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import defaultObject.AgendaDefault;

/**
 * Subclass to implement @Document notation.
 */
@Document(collection = "agenda")
public class Agenda extends AgendaDefault {

}
