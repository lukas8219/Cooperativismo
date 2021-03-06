package interfaces;
import java.util.Date;
import java.util.UUID;

/**
 * Interface containing all setters and getters
 * Contract to build a Vote
 */
public interface VoteInterface {
	
	public UUID getId();
	public void setId(UUID id);
	
	public String getCpf();
	public void setCpf(String cpf);
	
	public String getVote();
	public void setVote(String vote);
	
	public Date getVoteDate();
	public void setVoteDate(Date date);
	
	
}
