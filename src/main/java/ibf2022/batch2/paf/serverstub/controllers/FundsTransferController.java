package ibf2022.batch2.paf.serverstub.controllers;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.batch2.paf.serverstub.service.BankMongoSvc;
import ibf2022.batch2.paf.serverstub.service.BankService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

@Controller
@RequestMapping("/api/transfer")
public class FundsTransferController {

	@Autowired
	BankMongoSvc bankMongoSvc;

	@Autowired
	BankService bankService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postTransfer(@RequestBody String payLoad) {
		JsonReader reader = Json.createReader(new StringReader(payLoad));
        JsonObject jo = reader.readObject();
		
		String accNameFrom = jo.getString("srcAcct");
		String accNameTo = jo.getString("destAcct");
		Float transferAmount = Float.parseFloat(jo.getJsonNumber("amount").toString());

		ResponseEntity<String> transaction = bankService.checkProcedure(accNameFrom, accNameTo, transferAmount);
		 if(transaction.getStatusCode() == HttpStatus.BAD_REQUEST || transaction.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("error", transaction.getBody());
			JsonObject errorJson = builder.build();
			return ResponseEntity.badRequest().body(errorJson.toString());
		 } else {
			bankMongoSvc.insertResult(accNameFrom, accNameTo, transferAmount, transaction.getBody());
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add("transactionId", transaction.getBody().toString());
			JsonObject transferJson = builder.build();
			return ResponseEntity.ok().body(transferJson.toString());
		 }


		// Transfer successful return the following JSON object
		// { "transactionId", "aTransactionId" }
		//
		// Transfer failed return the following JSON object
		// { "message", "Error message" }

	}
}
