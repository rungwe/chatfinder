package com.example;

public class SendGridEvent {
	private String sg_message_id;
	private String event;
	private String email;
	private long timestamp;

	
	public SendGridEvent(String sg_message_id, String status, String email, long timestamp) {
		super();
		this.sg_message_id = sg_message_id;
		this.event = status;
		this.email = email;
		this.timestamp = timestamp;
	}
	public SendGridEvent(){
		
	}
	public String getSg_message_id() {
		return sg_message_id;
	}
	public void setSg_message_id(String sg_message_id) {
		this.sg_message_id = sg_message_id;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String status) {
		this.event = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
