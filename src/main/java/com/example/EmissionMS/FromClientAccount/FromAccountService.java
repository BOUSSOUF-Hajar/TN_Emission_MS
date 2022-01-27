package com.example.EmissionMS.FromClientAccount;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.EmissionMS.Twilio.SMSController;
import com.example.SharedLib.entities.Compte;
import com.example.SharedLib.entities.Transfert;
import com.example.SharedLib.enums.TypeFrais;
@Service
public class FromAccountService {
	@Autowired
	  private RestTemplate restTemplate;
	public String EmiTransfert(Transfert transfert){
		
		
		
		 Long id= transfert.getEmetteur().getIdClient();
		 
		 double montant=transfert.getMontant_transfert();
	      Compte compte=this.restTemplate.getForObject(
				 "http://Gestion/get_client_compte/"+id,Compte.class);
	      System.out.print(compte.getIdCompte());
	      double solde=compte.getMontant();
	      TypeFrais typeFrais=transfert.getFrais();
	      if(typeFrais==TypeFrais.parClient) {
	    	  transfert.setMontant_operation(montant+30);
	    	  
	      }else if(typeFrais==TypeFrais.parBeneficiaire) {
	    	  transfert.setMontant_operation(montant);
	    	  transfert.setMontant_transfert(montant-30);
	    	  
	      }else if(typeFrais==TypeFrais.partages){
	    	  transfert.setMontant_operation(montant+15);
	    	  transfert.setMontant_transfert(montant-15);
	      }
	     double montantTrans=transfert.getMontant_operation();
		if(montantTrans>2000) {
			
			return "Le montant du transfert ne doit pas dépasser le plafand maximal";

		}
		if(montantTrans>solde) {
			return "Le solde du compte est insuffisant";
		}
		compte.setMontant(solde-montantTrans);
		modifySolde(compte);
		addTransfert(transfert);
		if(transfert.isNotification()) {
		sendSMS(transfert.getBeneficiaire().getTelephone(),"Vous avez recu un transfert national./n Le référence : "+transfert.getReference());
		}
		
		return "Le trasfert a été bien ajouté.";
		
	      
	}
	public void modifySolde(Compte compte) {
		HttpHeaders headers = new HttpHeaders();
	    // set `content-type` header
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

	    // create a post object
	   

	    // build the request
	    HttpEntity<Compte> entity = new HttpEntity<>(compte, headers);
	    
	    // send PUT request to update compte
	    this.restTemplate.put("http://Gestion/update_Compte/{id}", entity, compte.getIdCompte());
	}
	
	public void 	addTransfert(Transfert transfert) {
HttpHeaders headers = new HttpHeaders();
	    
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

	   
	    HttpEntity<Transfert> entity = new HttpEntity<>(transfert, headers);
	    
	    Transfert newTransfert=restTemplate.postForObject("http://Gestion/add_Transfert", entity, Transfert.class);
		
	}
	public void sendSMS(String num,String msg) {
	    SMSController.sendMessages(num, msg);
	    
	}
}
