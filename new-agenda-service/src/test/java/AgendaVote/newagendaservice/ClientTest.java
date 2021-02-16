package AgendaVote.newagendaservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import defaultObject.AgendaDefault;

@SpringBootTest
class ClientTest {
	
	String host = "http://localhost:8080";
	String[] examples = {"novo presidente","novo comissario","tesoureiro"};
	String[] cpf = {"02010181018", "51315561042","38770553033"};
	String expiredAgenda = "906c239e-57ca-4b35-a8ee-91c308751330";
	String duplicate = "a79c66b3-8fa0-45c6-9db1-1f0bcf75d68a"; // 51315561042 duplicate
	String duplicateCPF = "51315561042";
	RestTemplate rest = new RestTemplate();
	
	@Test
	void DefaultTest() throws InterruptedException {
			
			AgendaDefault currentAgenda = new AgendaDefault(examples[0],examples[0]);
			currentAgenda = rest.postForObject(host+"/new/", currentAgenda, AgendaDefault.class);
			UUID id = currentAgenda.getId();
			
			rest.put(host+"/open/?agenda="+id, null); //openVotation
			
			for(int k = 0; k<cpf.length;k++) { //Vote
				while(true) { //repeat until the CPF can vote.
					try {
						boolean voted = rest.postForObject(host+"/vote/?agendaId="+id+
								"&cpf="+cpf[k]+"&vote=yes", null, Boolean.class);
						if(voted == true) { 
							System.out.println("Cpf validado"); 
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			System.out.println("Esperando um minuto até pegar Resultado.");
			Thread.sleep(60000);
			
			JsonNode result = rest.getForObject(host+"/result/"+id, JsonNode.class);
			
			assertEquals("Approved" , result.get("result").asText());
			
	}
	
	@Test
	void DuplicateCPF() throws InterruptedException {
				while(true) { //repeat until the CPF can vote.
					try {
						boolean voted = rest.postForObject(host+"/vote/?agendaId="+duplicate+
								"&cpf="+duplicateCPF+"&vote=yes", null, Boolean.class);
						if(voted == true) fail();
					} catch (Exception e) {
						if(e.getMessage().contains("has already voted for this Agenda")) {
							assertTrue(true);
							break;
						}
					}
				}
			}
			
	@Test
	void VotationExpired() throws InterruptedException {
			
			while(true) { //repeat until the CPF can vote.
					try {
						boolean voted = rest.postForObject(host+"/vote/?agendaId="+expiredAgenda+
								"&cpf="+"89829802027"+"&vote=yes", null, Boolean.class);
						if(voted == true) { 
							fail();
							break;
						}
					} catch (Exception e) {
						if(e.getMessage().contains("Votation expired for")) {
							assertTrue(true);
							break;
						}
					}
				}
	}
	
	@Test
	void VotationOccuring() throws InterruptedException {
			
			AgendaDefault currentAgenda = new AgendaDefault(examples[0],examples[0]);
			currentAgenda = rest.postForObject(host+"/new/", currentAgenda, AgendaDefault.class);
			UUID id = currentAgenda.getId();
			
			rest.put(host+"/open/?agenda="+id, null); //openVotation
			
			for(int k = 0; k<cpf.length;k++) { //Vote
				while(true) { //repeat until the CPF can vote.
					try {
						boolean voted = rest.postForObject(host+"/vote/?agendaId="+id+
								"&cpf="+cpf[k]+"&vote=yes", null, Boolean.class);
						if(voted == true) { 
							System.out.println("Cpf validado"); 
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			System.out.println("Esperando um minuto até pegar Resultado.");
			Thread.sleep(10000);
			
			try {
				JsonNode result = rest.getForObject(host+"/result/"+id, JsonNode.class);
			} catch (Exception e) {
				if(e.getMessage().contains("Votation occuring for")) {
					assertTrue(true);
				}
			}
			
	}
	
	@Test
	void AlreadyOpenTest() throws InterruptedException {
			
			try {
				rest.put(host+"/open/?agenda="+expiredAgenda, null); //openVotation
			} catch (Exception e) {
				if(e.getMessage().contains("Votation already open for")) {
					assertTrue(true);
				}
			}
			

			
	}

}
