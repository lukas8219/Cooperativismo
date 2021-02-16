package exception;

import java.util.UUID;
/**
 * Called when No Agenda was found
 * @author Lucas
 *
 */
public class InvalidAgendaIdException extends IllegalArgumentException {

	public InvalidAgendaIdException(UUID id) {
		super("Not a valid Agenda ID for found: "+id);
	}
	
}
