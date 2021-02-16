package exception;

/**
 * Called when the CPF was invalid. Calling the API on Heroku App.
 * @author Lucas
 *
 */
public class InvalidCpfException extends IllegalArgumentException {

	public InvalidCpfException(String cpf) {
		super("Invalid CPF : "+cpf);
	}
	
}
