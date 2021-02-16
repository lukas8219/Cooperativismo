package exception;

import java.util.UUID;

public class OpenVoteException extends IllegalArgumentException {

	public OpenVoteException(UUID agendaId) {
		super("Votation already open for "+agendaId);
	}
	
}
