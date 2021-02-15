package exception;

import java.util.UUID;

public class VotationNotOpenException extends RuntimeException {
	
	public VotationNotOpenException(UUID id) {
		super("Votation is not Open for "+id);
	}
}
