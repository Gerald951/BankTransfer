package ibf2022.batch2.paf.serverstub.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.serverstub.repository.BankRepo;

@Service
public class BankService {

    @Autowired
    BankRepo bankRepo;
 
    public ResponseEntity<String> checkProcedure(String from, String to, Float amount) {

        if (bankRepo.checkBalance(from, amount)) {
            try {
                bankRepo.addFunds(to, amount);
                bankRepo.deductFunds(from, amount);
                String transactionId = generateTransactionId();
                return ResponseEntity.ok().body(transactionId);
               
            } catch (Exception e) {
                String error = e.getMessage();
                return ResponseEntity.internalServerError().body(error);
            }
        } else {
            return ResponseEntity.badRequest().body("Insufficient Funds to be deducted");
        }       
    }

    public String generateTransactionId() {
        String invoiceId = UUID.randomUUID().toString().substring(0,8);
        return invoiceId;
    }
    
}
