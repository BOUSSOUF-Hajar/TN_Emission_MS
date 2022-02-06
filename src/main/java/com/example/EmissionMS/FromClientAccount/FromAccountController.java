package com.example.EmissionMS.FromClientAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.SharedLib.entities.*;
@RestController
@CrossOrigin("*")
public class FromAccountController {
	 @Autowired
	private FromAccountService service;
	
	
	@PutMapping("/fromClientAccount/{id}")
	public String fromAccount(@RequestBody Transfert transfert,@PathVariable Long id) {
		return service.EmiTransfert(transfert,id);
	}
}
