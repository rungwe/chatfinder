package com.example;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;



@Controller
@RestController
public class EmailReceiptController {
	
	
	
	
	@RequestMapping(value="/events",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> ReceiveEvents(@RequestBody List<SendGridEvent> events ){
		//get the mongodb object
		DB db = DatabaseWrapper.db;
		
		//select the database
        DBCollection receipts = db.getCollection("EmailReceipts");
        
        //Index by message id
        receipts.createIndex(new BasicDBObject("sg_message_id",1));
        
        for(SendGridEvent event: events){
        	//query object
        	BasicDBObject queryObject = new BasicDBObject();
        	queryObject.append("sg_message_id", event.getSg_message_id());
        	
        	//jasonify the object
        	Gson gson = new Gson();
            BasicDBObject obj = (BasicDBObject)JSON.parse(gson.toJson(event));
            
            //check if object exist then update otherwise create and insert a new object
            receipts.update(queryObject,obj,true,false);
        }
        
       
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/GetReceiptById",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<SendGridEvent> GetReceiptById(@RequestParam(value="messageId") String messageId){
		DB db = DatabaseWrapper.db;
		
		
        Jongo jongo = new Jongo(db);
        MongoCollection receipts = jongo.getCollection("EmailReceipts");
        
        //query object
        BasicDBObject queryObject = new BasicDBObject();
    	queryObject.append("sg_message_id", messageId);
    	
    	
        SendGridEvent receipt = receipts.findOne(queryObject.toString()).as(SendGridEvent.class);
		if(receipt!=null){
			return new ResponseEntity<SendGridEvent>(receipt,HttpStatus.OK);
		}
		else{
			return new ResponseEntity<SendGridEvent>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value="/GetReceiptByEmailTimestamp",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<SendGridEvent> GetReceiptByEmailTimestamp(HttpServletRequest requestParams){
		String email = requestParams.getParameter("email");
		String time = requestParams.getParameter("timestamp");
		
		if(email==null || time==null){
			return new ResponseEntity<SendGridEvent>(HttpStatus.BAD_REQUEST);
		}
		
		long timestamp = Long.parseLong(time);
		DB db = DatabaseWrapper.db;
		
		Jongo jongo = new Jongo(db);
        MongoCollection receipts = jongo.getCollection("EmailReceipts");
        
        //query object
        BasicDBObject queryObject = new BasicDBObject();
    	queryObject.append("email", email);
    	queryObject.append("timestamp", timestamp);
    	
    	
        SendGridEvent receipt = receipts.findOne(queryObject.toString()).as(SendGridEvent.class);
		if(receipt!=null){
			return new ResponseEntity<SendGridEvent>(receipt,HttpStatus.OK);
		}
		else{
			return new ResponseEntity<SendGridEvent>(HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	
}
