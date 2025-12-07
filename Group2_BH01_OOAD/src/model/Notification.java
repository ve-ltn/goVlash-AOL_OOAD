package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import util.MySQLConnection;

public class Notification {
	private int notifID;
	private int recipientID;
	private String message;
	private LocalDateTime createdAt;
	private boolean isRead;
	
	private final Connection db = MySQLConnection.getInstance().getConnection();
	
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
	
	public List<Notification> getNotificationsByRecipientId(int recipientId) {
		List<Notification> list = new ArrayList<Notification>();
		
		String query = "SELECT * FROM notification "
				+ "WHERE RecipientID = ? ";
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, recipientId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs != null && rs.next()) {
				list.add(new Notification(
						rs.getInt("NotifID"), 
						rs.getInt("RecipientID"), 
						rs.getString("Message"), 
						rs.getTimestamp("CreatedAt").toLocalDateTime(), 
						rs.getBoolean("IsRead")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Notification getNotificationById(int notificationId) {
		Notification notif = null;
		
		String query = "SELECT * FROM notification "
				+ "WHERE NotifID = ?";
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, notificationId);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				notif = new Notification(
						rs.getInt("NotifID"), 
						rs.getInt("RecipientID"), 
						rs.getString("Message"), 
						rs.getTimestamp("CreatedAt").toLocalDateTime(), 
						rs.getBoolean("IsRead")
				);
			}
			
			if(notif == null) return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notif;
	}
	
	public void markAsRead(int notificationId) {
		String query = "UPDATE notification "
				+ "SET IsRead = TRUE "
				+ "WHERE NotifID = ?";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, notificationId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteNotification(int notificationId) {
		String query = "DELETE FROM notification "
				+ "WHERE NotifID = ?";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, notificationId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNotification(int recipientId, String message) {
		String query = "INSERT INTO notification (RecipientID, Message, CreatedAt, IsRead) "
				+ " VALUES (?, ?, ?, FALSE)";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, recipientId);
			stmt.setString(2, message);
			stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
