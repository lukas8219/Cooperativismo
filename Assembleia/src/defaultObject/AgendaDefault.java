package defaultObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import exception.InvalidCpfException;
import exception.VotationExpiredException;
import exception.VotationNotOpenException;
import interfaces.AgendaInterface;
import interfaces.VoteInterface;

public class AgendaDefault implements AgendaInterface {

	private UUID id;
	private String name;
	private String description;
	private Date votationExpirationDate;
	private int favor;
	private int against;
	private List<VoteInterface> voters;
	
	public AgendaDefault() {
	}
	
	public AgendaDefault(String name, String description) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.favor = 0;
		this.against = 0;
		this.voters = new ArrayList<>();
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getVotationExpirationDate() {
		return votationExpirationDate;
	}
	public void setVotationExpirationDate(Date votationExpirationDate) {
		this.votationExpirationDate = votationExpirationDate;
	}
	public int getFavor() {
		return favor;
	}
	public void setFavor(int favor) {
		this.favor = favor;
	}
	public int getAgainst() {
		return against;
	}
	public void setAgainst(int against) {
		this.against = against;
	}
	public List<VoteInterface> getVoters() {
		return voters;
	}
	public void setVoters(List<VoteInterface> voters) {
		this.voters = voters;
	}

	@Override
	public void openVote(int minutes) {
		long time = System.currentTimeMillis() + (minutes*60*1000);
		this.votationExpirationDate = new Date(time);
	}

	@Override
	public void vote(VoteInterface vote) throws Exception {
		
		if(this.voters == null) voters = new ArrayList<>();
		
		List<VoteInterface> matchingCPF = this.voters.stream()
				.filter(x -> x.getCpf().equals(vote.getCpf()))
				.collect(Collectors.toList());
		
		if(! matchingCPF.isEmpty()) throw new InvalidCpfException(vote.getCpf());
						
		
		if(this.votationExpirationDate == null) {
			throw new VotationNotOpenException(this.id);
		}
		
		String decision = vote.getVote();
		
		if(vote.getVoteDate().after(votationExpirationDate)) {
			throw new VotationExpiredException(this.id);
		} else {
			if(decision.equals("no")) {
				this.against++;
				this.voters.add(vote);
			} else if(decision.equals("yes")) {
				this.favor++;
				this.voters.add(vote);
			} else {
				throw new Exception("Only YES/NO voting options");
			}
		}
		
	}
}
