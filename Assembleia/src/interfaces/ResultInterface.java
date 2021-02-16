package interfaces;

/**
 * Interface containing all getters and setters.
 * Contract to build a Result
 */
public interface ResultInterface {

	public AgendaInterface getAgenda();
	public void setAgenda(AgendaInterface agenda);
	
	public String getResult();
	public void setResult(String result);
	
	
}
