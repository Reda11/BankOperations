package org.sid.service;

import org.sid.Entities.Compte;
import org.sid.Entities.Operation;
import org.springframework.data.domain.Page;

public interface IBankService {

	
	public Compte consululterCompte(String codeCpte);
	public void verser(String codeCpte, double montant);
	public void retirer(String codeCpte, double montant);
	public void virement(String codeCpte1,String codeCpte2 , double montant);
	public Page<Operation> listOperation(String codeCompte,int page, int size);
	
}
