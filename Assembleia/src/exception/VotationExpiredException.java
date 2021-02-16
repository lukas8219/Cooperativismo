package exception;

import java.util.UUID;

/**
 * Called when someone tries to vote, and the votation has already expired
 * @author Lucas
 *
 */
public class VotationExpiredException extends IllegalArgumentException{

	public VotationExpiredException(UUID id) {
		super("Votation expired for "+id);
	}
	
}
