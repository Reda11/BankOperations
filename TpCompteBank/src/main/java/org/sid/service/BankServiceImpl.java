package org.sid.service;


import java.util.Date;

import org.sid.Entities.Compte;
import org.sid.Entities.CompteCourant;
import org.sid.Entities.Operation;
import org.sid.Entities.Retrait;
import org.sid.Entities.Versement;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankServiceImpl implements IBankService{

	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Compte consululterCompte(String codeCpte) {
		
		Compte cpt = compteRepository.findOne(codeCpte);
		if(cpt == null) throw new RuntimeException("Compte Introuvable");
		
		return cpt;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cpt = consululterCompte(codeCpte);
		
		Versement versement = new Versement(new Date(), montant, cpt);
		operationRepository.save(versement);
		cpt.setSolde(cpt.getSolde() + montant);
		compteRepository.save(cpt);
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		
		Compte cpt = consululterCompte(codeCpte);
		double faciliteCaisse = 0;
		
		if(cpt instanceof CompteCourant )
			faciliteCaisse = ((CompteCourant) cpt).getDecouvert();
		if(cpt.getSolde() + faciliteCaisse < montant)
			throw new RuntimeException("Solde insufissant");
		
		
		Retrait retrait = new Retrait(new Date(), montant, cpt);
		operationRepository.save(retrait);
		cpt.setSolde(cpt.getSolde() - montant);
		compteRepository.save(cpt);
		
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		
		retirer(codeCpte1, montant);
		verser(codeCpte2, montant);
		
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {
		
		return operationRepository.listOperation(codeCompte, new PageRequest(page, size));
	}

}
