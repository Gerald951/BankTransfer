package ibf2022.batch2.paf.serverstub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.serverstub.repository.BankMongoRepo;

@Service
public class BankMongoSvc {

    @Autowired
    BankMongoRepo bankMongoRepo;

    public Boolean insertResult(String from, String to, Float amount, String Id) {
        return bankMongoRepo.insertResult(from, to, amount, Id);
    }
    
}
