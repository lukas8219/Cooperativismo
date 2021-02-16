package exception;

import java.util.UUID;
/**
 * Called when the API tries to get the result without the votation end
 * @author Lucas
 *
 */
public class VotationOccurringException extends IllegalArgumentException {

	public VotationOccurringException(UUID agendaId) {
		super("Votation occuring for Agenda : "+agendaId);
	}
	
}
