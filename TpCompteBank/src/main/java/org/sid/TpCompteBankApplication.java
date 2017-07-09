package org.sid;

import java.util.Date;

import org.sid.Entities.Client;
import org.sid.Entities.Compte;
import org.sid.Entities.CompteCourant;
import org.sid.Entities.CompteEpargne;
import org.sid.Entities.Retrait;
import org.sid.Entities.Versement;
import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpCompteBankApplication implements CommandLineRunner{

	@Autowired	
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	
	@Autowired
	private IBankService bankService;
	
	public static void main(String[] args) {
		
		SpringApplication.run(TpCompteBankApplication.class, args);
		
		
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Client c1 = clientRepository.save(new Client("ATOINI REDA","rida.atoini@gmail.com"));
		Client c2 = clientRepository.save(new Client("ATOINI JALAL","jalal.at@gmail.com"));
		
		Compte cpt1 = compteRepository.save(new CompteCourant("CC1", new Date(), 10000, c1, 7000));
		Compte cpt2 = compteRepository.save(new CompteEpargne("CE1", new Date(), 8000, c2, 5.5));
		
		operationRepository.save(new Versement(new Date(), 4000, cpt1));
		operationRepository.save(new Versement(new Date(), 100, cpt1));
		operationRepository.save(new Versement(new Date(), 7000, cpt1));
		operationRepository.save(new Retrait(new Date(), 1000, cpt1));
		
		
		operationRepository.save(new Versement(new Date(), 3000, cpt2));
		operationRepository.save(new Versement(new Date(), 500, cpt2));
		operationRepository.save(new Versement(new Date(), 9000, cpt2));
		operationRepository.save(new Retrait(new Date(), 2000, cpt2));
		
		
		
		bankService.verser("CC1", 5555555);
		
		
	}
}
