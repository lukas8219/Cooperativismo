package exception;

import java.util.UUID;

public class VotationExpiredException extends IllegalArgumentException{

	public VotationExpiredException(UUID id) {
		super("Votation expired for "+id);
	}
	
}
