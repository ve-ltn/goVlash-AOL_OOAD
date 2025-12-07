package controller;

import java.util.List;

import javafx.scene.control.Alert;
import model.AlertMessage;
import model.Notification;
import model.User;
import view.ViewCustomerHomePage;

public class NotificationController extends AlertMessage{
	private Notification model;
	private ViewCustomerHomePage view;
	
	public NotificationController(Notification model, ViewCustomerHomePage view) {
		super();
		this.model = model;
		this.view = view;
	}

	public void sendNotification(int recipientId, String message) {
		if(recipientId <= 0) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Recipient ID incorrect");
            return;
		}
		else if(message == null || message.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Message cannot be empty");
			return;
		}
				
		model.sendNotification(recipientId, message);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Notification sent successfully");
	}
	
	public List<Notification> getNotificationsByRecipientId(int recipientId) {
		List<Notification> list = model.getNotificationsByRecipientId(recipientId);
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.WARNING, "!!!", "No notification found");
		
//		DISPLAY SEMUA NOTIF
//		view.diplayNotifications(list);
		
		return list;
	}
	
	public Notification getNotificationById(int notificationId) {
		Notification notif = model.getNotificationById(notificationId);
		
		if(notif == null) {
			showAlert(Alert.AlertType.WARNING, "!!!", "Notification not found");
			return null;
		}
		
		markAsRead(notif);
		
//		DISPLAY DETAIL DARI NOTIFNYA
//		view.displayNotificationDetail(notif);
		
		return notif;
	}
	
	public void deleteNotification(int notificationId) {
		Notification notif = model.getNotificationById(notificationId);
		if(notif == null) {
			showAlert(Alert.AlertType.WARNING, "!!!", "Notification not found");
			return;
		}
		model.deleteNotification(notificationId);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Notification deleted successfully");

	}
	
	public void markAsRead(Notification notification) {
		if(!validateIsRead(notification)) return;
		
		model.markAsRead(notification.getNotifID());
		notification.setRead(true);
	}
	
	public boolean validateIsRead(Notification notification) {
		if(notification.isRead()) return false;
		return true;
	}
	
}
