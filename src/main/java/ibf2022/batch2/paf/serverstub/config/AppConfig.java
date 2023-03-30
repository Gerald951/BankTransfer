package ibf2022.batch2.paf.serverstub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {

    @Value("${mongo.url}")
    private String url;

    @Bean
    public MongoTemplate createMongoTemplate() {
        MongoClient client = MongoClients.create(url);

        MongoTemplate template = new MongoTemplate(client, "banktransfer");

        return template;
    }
    
}

