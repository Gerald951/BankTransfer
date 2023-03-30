package ibf2022.batch2.paf.serverstub.repository;

import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BankMongoRepo {

    @Autowired
    MongoTemplate template;

    public Boolean insertResult(String from, String to, Float amount, String transactionId) {
        Document doc1 = new Document("from", from).append("to", to)
                                                     .append("amount", amount)
                                                    .append("date", new Date())
                                                    .append("transactionId", transactionId);
                                                    
        Document inserted = template.insert(doc1, "bankStatement");

        return null==inserted?false:true;
    }
    
}
