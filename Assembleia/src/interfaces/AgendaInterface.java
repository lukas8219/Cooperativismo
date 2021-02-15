package interfaces;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AgendaInterface {

	public UUID getId();
	public void setId(UUID id);
	
	public String getName();
	public void setName(String name);
	
	public String getDescription();
	public void setDescription(String desc);
	
	public Date getVotationExpirationDate();
	public void setVotationExpirationDate(Date expiration);
	
	public int getFavor();
	public void setFavor(int favor);
	
	public int getAgainst();
	public void setAgainst(int against);
	
	public List<VoteInterface> getVoters();
	public void setVoters(List<VoteInterface> votee);
	
	public void openVote(int timeLimit);
	public void vote(VoteInterface vote) throws Exception;
	
}
