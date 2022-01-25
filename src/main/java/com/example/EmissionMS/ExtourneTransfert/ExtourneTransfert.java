package com.example.EmissionMS.ExtourneTransfert;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.EmissionMS.Twilio.SMSController;
import com.example.SharedLib.entities.Compte;
import com.example.SharedLib.entities.Transfert;
import com.example.SharedLib.enums.*;

@RestController
public class ExtourneTransfert {
	@Autowired
	  private RestTemplate restTemplate;
	@GetMapping("/extourner_transfert")
	public String extourne(@RequestBody Transfert transfert) {
		if(transfert.getEtat()!=EtatTransfert.à_servir) {
			return "";
		}
		 LocalDate date = LocalDate.now();
		@SuppressWarnings("deprecation")
		Date dateNow=new Date(date.toString());
		if(transfert.getDate_demission().getDate()!=dateNow.getDate()) {
			return "Vous ne pouvez pas extourner ce transfert";
		}
		 Long id= transfert.getAgent().getIdClient();
		 Compte compte=this.restTemplate.getForObject(
				 "http://Gestion/get_client_compte/"+id,Compte.class);
		 compte.setMontant(compte.getMontant()+transfert.getMontant_operation());
		 modifySolde(compte);
		 transfert.setEtat(EtatTransfert.extourne);
		 modifyTransfert(transfert);
		 if(transfert.isNotification()) {
			 sendSMS(transfert.getEmetteur().getTelephone(),"Votre transfert national a été extouné./n Le référence : "+transfert.getReference());
				
		 }
		return "Le transfert a été bien extourné";
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
	public void modifyTransfert(Transfert transfert) {
		HttpHeaders headers = new HttpHeaders();
	    // set `content-type` header
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    // set `accept` header
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

	    // create a post object
	   

	    // build the request
	    HttpEntity<Transfert> entity = new HttpEntity<>(transfert, headers);

	    // send PUT request to update compte
	    this.restTemplate.put("http://Gestion/update_Transfert/{id}", entity, transfert.getId());
	}
	public void sendSMS(String num,String msg) {
		
		   
	    SMSController.sendMessages(num, msg);
	    
	}
}
