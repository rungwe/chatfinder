package com.example;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DatabaseWrapper {
	
	static MongoClientURI uri  = new MongoClientURI("mongodb://heroku_08nfrlls:cbe4h2bkhivk9a7345jb634bj3@ds023435.mlab.com:23435/heroku_08nfrlls"); 
    static MongoClient client = new MongoClient(uri);
    @SuppressWarnings({ "deprecation" })
	public static DB db = client.getDB(uri.getDatabase());
}
