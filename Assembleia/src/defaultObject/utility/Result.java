package defaultObject.utility;

import java.util.Date;

import interfaces.AgendaInterface;
import interfaces.ResultInterface;

public class Result implements ResultInterface{

	private AgendaInterface agenda;
	private String result; // Approved ! Refused
	
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
	
	public static ResultInterface getResult(AgendaInterface agenda) throws Exception {
		
		if(agenda.getVotationExpirationDate() == null) throw new Exception("Votation not Open!");
		if(agenda.getVotationExpirationDate().after(new Date())) throw new Exception("Votation Expired!");
		
		int favor = agenda.getFavor();
		int against = agenda.getAgainst();
		String result;
		
		
		if(favor>against) {
			result = "Approved";
		} else if (against>favor) {
			result = "Refused";
		} else {
			result = "Tied";
		}
		
		return new Result(agenda,result);
	}
	
}
