package AgendaVote.angedavoteclient.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FinalResultDeserializer.class)
public class FinalResult {

	private String result;

	public FinalResult() {
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
