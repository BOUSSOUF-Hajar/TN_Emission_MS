package com.example.EmissionMS.FromAgentAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SharedLib.entities.Agent;
import com.example.SharedLib.entities.Transfert;
@RestController
@CrossOrigin("*")
public class FromAgentAccountController {
	 @Autowired
		private FromAgentAccountService service;
	 	@GetMapping("/getTransfert")
	 	public Agent[]  get() {
	 		return service.get();
	 	}
		@PutMapping("/fromAgentAccount/{id}")
		public String fromAccount(@RequestBody Transfert transfert,@PathVariable Long id) {
			return service.EmiTransfert(transfert,id);
		}
	
}
