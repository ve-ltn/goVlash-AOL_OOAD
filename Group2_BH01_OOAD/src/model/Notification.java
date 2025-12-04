package model;

import java.time.LocalDateTime;

public class Notification {
	private int notifID;
	private int recipientID;
	private String message;
	private LocalDateTime createdAt;
	private boolean isRead;
	
	public Notification(int notifID, int recipientID, String message, LocalDateTime createdAt, boolean isRead) {
		super();
		this.notifID = notifID;
		this.recipientID = recipientID;
		this.message = message;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}

	public int getNotifID() {
		return notifID;
	}

	public void setNotifID(int notifID) {
		this.notifID = notifID;
	}

	public int getRecipientID() {
		return recipientID;
	}

	public void setRecipientID(int recipientID) {
		this.recipientID = recipientID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
