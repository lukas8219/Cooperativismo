package defaultObject.utility;

import java.util.Date;

import exception.VotationNotOpenException;
import exception.VotationOccurringException;
import interfaces.AgendaInterface;
import interfaces.ResultInterface;

/**
 * 
 */
public class Result implements ResultInterface{

	private AgendaInterface agenda;
	private String result;
	
	
	/**
	 * Default construct to deserialize/instantiate.
	 */
	public Result() {
	}
	
	
	/**
	 * Construct a Result Object containing fields Agenda and Result
	 * @param agenda - a Agenda Interface/implemented Object
	 * @param result - YES or NO String - non-case-sensitive
	 */
	
	public Result(AgendaInterface agenda, String result) {
		this.result = result;
		this.agenda = agenda;
	}
	
	public AgendaInterface getAgenda() {
		return agenda;
	}
	public void setAgenda(AgendaInterface agenda) {
		this.agenda = agenda;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * Calculates the result and check if it is valid to return a result.
	 * @param votedAgenda - Agenda Interface containing a votation.
	 * @return a ResultInterface
	 * @throws Exception for Agenda ID, and Votation Occurring
	 */
	public static ResultInterface getResult(AgendaInterface votedAgenda) throws Exception {
		
		if(votedAgenda.getVotationExpirationDate() == null) throw new VotationNotOpenException(votedAgenda.getId());
		if(votedAgenda.getVotationExpirationDate().after(new Date())) throw new VotationOccurringException(votedAgenda.getId());		
		int favor = votedAgenda.getFavor();
		int against = votedAgenda.getAgainst();
		
		String result;
		
		if(favor>against) {
			result = "Approved";
		} else if (against>favor) {
			result = "Refused";
		} else {
			result = "Tied";
		}
		
		return new Result(votedAgenda,result);
	}
	
}
