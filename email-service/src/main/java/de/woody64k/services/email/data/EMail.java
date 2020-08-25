package de.woody64k.services.email.data;

public class EMail {
    private String sender;
    private String reciever;
    private String heading;
    private String text;

    public String getSender() {
	return sender;
    }

    public void setSender(String sender) {
	this.sender = sender;
    }

    public String getReciever() {
	return reciever;
    }

    public void setReciever(String reciever) {
	this.reciever = reciever;
    }

    public String getHeading() {
	return heading;
    }

    public void setHeading(String heading) {
	this.heading = heading;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

}
