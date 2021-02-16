package exception;

/** 
 * Called when theres already a Vote with that CPF
 */
public class DuplicateVoteException extends IllegalArgumentException {

	public DuplicateVoteException(String cpf) {
		super("CPF "+cpf+" has already voted for this Agenda!");
	}
	
}
