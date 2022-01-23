package com.example.EmissionMS.FromAgentAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SharedLib.entities.Agent;
import com.example.SharedLib.entities.Transfert;
@RestController
public class FromAgentAccountController {
	 @Autowired
		private FromAgentAccountService service;
	 	@GetMapping("/getTransfert")
	 	public Agent[]  get() {
	 		return service.get();
	 	}
		@GetMapping("/fromAgentAccount")
		public String fromAccount(@RequestBody Transfert transfert) {
			return service.EmiTransfert(transfert);
		}
	
}
