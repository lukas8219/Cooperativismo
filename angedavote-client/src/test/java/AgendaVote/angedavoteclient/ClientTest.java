package AgendaVote.angedavoteclient;

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
	String example = "tesoureiro";
	String[] cpf = {"02010181018", "51315561042","38770553033"};
	RestTemplate rest = new RestTemplate();
	UUID id;
	
	@Test
	void DefaultTest() throws InterruptedException {
			
			AgendaDefault currentAgenda = new AgendaDefault(example,example);
			currentAgenda = rest.postForObject(host+"/new/", currentAgenda, AgendaDefault.class);
			id = currentAgenda.getId();
			
			rest.put(host+"/open/?agenda="+id, null); //openVotation
			
			System.out.println("Tentando abrir 2 vezes");
			
			AlreadyOpenTest(id);
			
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
			
			System.out.println("Tentando votar com o mesmo CPF");
			DuplicateCPF(cpf[0], id);
			
			System.out.println("Tentando pegar resultado com votação em Andamento!");
			VotationOccuring(id);
			
			System.out.println("Esperando resultado...");
			Thread.sleep(60000);
			JsonNode result = rest.getForObject(host+"/result/"+id, JsonNode.class);
			assertEquals("Approved" , result.get("result").asText());
			
			System.out.println("Tentando votar depois de expirado! ");
			VotationExpired(id);
			
	}
	
	void DuplicateCPF(String duplicateCPF, UUID id) throws InterruptedException {
				while(true) { //repeat until the CPF can vote.
					try {
						boolean voted = rest.postForObject(host+"/vote/?agendaId="+id+
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
			
	void VotationExpired(UUID expiredAgenda) throws InterruptedException {
			
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
	
	void VotationOccuring(UUID id) throws InterruptedException {
			try {
				JsonNode result = rest.getForObject(host+"/result/"+id, JsonNode.class);
			} catch (Exception e) {
				if(e.getMessage().contains("Votation occuring for")) {
					assertTrue(true);
				}
			}
			
	}
	
	void AlreadyOpenTest(UUID id) throws InterruptedException {
			
			try {
				rest.put(host+"/open/?agenda="+id, null); //openVotation
			} catch (Exception e) {
				if(e.getMessage().contains("Votation already open for")) {
					assertTrue(true);
				}
			}
			

			
	}

}
