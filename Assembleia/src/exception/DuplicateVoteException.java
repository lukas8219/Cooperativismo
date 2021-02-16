package exception;

public class DuplicateVoteException extends IllegalArgumentException {

	public DuplicateVoteException(String cpf) {
		super("CPF "+cpf+" has already voted for this Agenda!");
	}
	
}
