package defaultObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import exception.DuplicateVoteException;
import exception.InvalidCpfException;
import exception.VotationExpiredException;
import exception.VotationNotOpenException;
import interfaces.AgendaInterface;
import interfaces.VoteInterface;

/**
 * Default Agenda Object.
 *  
 */
public class AgendaDefault implements AgendaInterface {

	private UUID id;
	private String name;
	private String description;
	private Date votationExpirationDate;
	private int favor;
	private int against;
	private List<VoteInterface> voters;
	
	/**
	 * Default constructor for deserialization
	 */
	public AgendaDefault() {
	}
	
	/**
	 * Go-To Constructor
	 * Initiliazes all variables.
	 * 
	 * @param name
	 * @param description
	 */
	public AgendaDefault(String name, String description) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.favor = 0;
		this.against = 0;
		this.voters = new ArrayList<>();
	}
	
	/**
	 * Return the ID
	 * @return the ID
	 */
	public UUID getId() {
		return id;
	}
	/**
	 * Sets the ID
	 * @param ID
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	/**
	 * return the Name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * return the description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * return the Expiration Date
	 * @return expirationDate
	 */
	public Date getVotationExpirationDate() {
		return votationExpirationDate;
	}
	/**
	 * Sets the expiration date
	 * @param votationExpirationDate
	 */
	public void setVotationExpirationDate(Date votationExpirationDate) {
		this.votationExpirationDate = votationExpirationDate;
	}
	/**
	 * return all votes in favor of the agenda
	 * @return favor
	 */
	public int getFavor() {
		return favor;
	}
	/**
	 * Sets the votes in favor
	 * @param votes
	 */
	public void setFavor(int favor) {
		this.favor = favor;
	}
	
	/**
	 * return all votes against the agenda
	 * @return against
	 */
	public int getAgainst() {
		return against;
	}
	/**
	 * Sets the votes against
	 * @param against
	 */
	public void setAgainst(int against) {
		this.against = against;
	}
	/**
	 * Get all voters
	 */
	public List<VoteInterface> getVoters() {
		return voters;
	}
	/**
	 * sets the voters
	 * @param voters
	 */
	public void setVoters(List<VoteInterface> voters) {
		this.voters = voters;
	}
	
	/**
	 * Open the votation with n minutes from current time.
	 * @param minutes - how many minutes will be the agenda votation last
	 */
	@Override
	public void openVote(int minutes) {
		int minuteUnit = 60*1000;
		long expirationDate = System.currentTimeMillis() + (minutes*minuteUnit);
		this.votationExpirationDate = new Date(expirationDate);
	}

	
	/**
	 * Validate a vote, and add it to the agenda.
	 * Only counts if there is not duplicate CPF, and votation is not expired and opened to votation.
	 * @param newVote - a vote has to be YES or NO
	 */
	@Override
	public void vote(VoteInterface newVote) {
		
		if(this.voters == null) voters = new ArrayList<>();
		
		List<VoteInterface> duplicateCpf = this.voters.stream()
										.filter(x -> x.getCpf().equals(newVote.getCpf()))
										.collect(Collectors.toList());
		
		if(! duplicateCpf.isEmpty()) throw new DuplicateVoteException(newVote.getCpf()); // create new duplicate exception
						
		
		if(this.votationExpirationDate == null) {
			throw new VotationNotOpenException(this.id);
		}
		
		String voteDecision = newVote.getVote();
		
		if(newVote.getVoteDate().after(votationExpirationDate)) {
			throw new VotationExpiredException(this.id);
		} else {
			if(voteDecision.equals("no")) {
				this.against++;
				this.voters.add(newVote);
			} else if(voteDecision.equals("yes")) {
				this.favor++;
				this.voters.add(newVote);
			} else {
				throw new IllegalArgumentException("decision key only accepts YES/NO");
			}
		}
		
	}
}
