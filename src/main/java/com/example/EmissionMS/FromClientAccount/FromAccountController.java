package com.example.EmissionMS.FromClientAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.SharedLib.entities.*;
@RestController
public class FromAccountController {
	 @Autowired
	private FromAccountService service;
	
	
	@GetMapping("/fromClientAccount")
	public String fromAccount(@RequestBody Transfert transfert) {
		return service.EmiTransfert(transfert);
	}
}
