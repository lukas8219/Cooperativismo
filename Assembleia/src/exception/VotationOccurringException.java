package exception;

import java.util.UUID;

public class VotationOccurringException extends IllegalArgumentException {

	public VotationOccurringException(UUID agendaId) {
		super("Votation occuring for Agenda : "+agendaId);
	}
	
}
