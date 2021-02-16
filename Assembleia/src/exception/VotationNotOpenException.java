package exception;

import java.util.UUID;

/**
 * Response to a vote try when the Expiration Date is null.
 * Called when the votation hasnt been open
 * @author Lucas
 *
 */
public class VotationNotOpenException extends RuntimeException {
	
	public VotationNotOpenException(UUID id) {
		super("Votation is not Open for "+id);
	}
}
