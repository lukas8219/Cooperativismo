package exception;

import java.util.UUID;

/**
 * Called when the Votation is already Opened, and the API tried to open it again.
 * @author Lucas
 *
 */
public class OpenVoteException extends IllegalArgumentException {

	public OpenVoteException(UUID agendaId) {
		super("Votation already open for "+agendaId);
	}
	
}
