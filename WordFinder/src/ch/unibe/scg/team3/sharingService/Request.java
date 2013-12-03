package ch.unibe.scg.team3.sharingService;

import ch.unibe.scg.team3.user.User;

public class Request {
	protected String requestid;
	protected String initiator;
	protected String subject;
	public Request(String requestid, String initiator, String subject) {
		this.requestid = requestid;
		this.initiator = initiator;
		this.subject = subject;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Request ))
		{
			return false;
		}
		Request other = (Request) o;
		if(!other.getRequestid().equals(this.getRequestid())){
			return false;
		}
		return true;
	}
}
