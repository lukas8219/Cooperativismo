package defaultObject;

import java.util.Date;
import java.util.UUID;

import interfaces.VoteInterface;

public class VoteDefault implements VoteInterface {

	private UUID id;
	private String cpf;
	private String vote;
	private Date voteDate;
	
	public VoteDefault() {
	}
	
	public VoteDefault(String cpf, String vote) {
		this.id = UUID.randomUUID();
		this.cpf = cpf;
		this.vote = vote;
		this.voteDate = new Date();
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}
	public Date getVoteDate() {
		return voteDate;
	}
	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}
	
	
}
