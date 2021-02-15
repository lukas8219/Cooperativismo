package exception;

public class InvalidCpfException extends IllegalArgumentException {

	public InvalidCpfException(String cpf) {
		super("Invalid CPF : "+cpf);
	}
	
}
