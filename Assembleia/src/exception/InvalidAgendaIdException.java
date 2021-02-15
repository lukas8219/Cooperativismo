package exception;

import java.util.UUID;

public class InvalidAgendaIdException extends IllegalArgumentException {

	public InvalidAgendaIdException(UUID id) {
		super("Not a valid Agenda ID for found: "+id);
	}
	
}
