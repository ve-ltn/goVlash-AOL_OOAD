package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import model.AlertMessage;
import model.Transaction;
import view.ViewTransactionPage;

public class TransactionController extends AlertMessage{
	private Transaction model;
	
//	AMBIL SEMUA TRANSACTION
	public List<Transaction> getAllTransaction(){
		List<Transaction> list = model.getAllTransaction();
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No transaction found");
		
		return list;
	}
	
//	AMBIL TRANSACTION SESUAI STATUS
	public List<Transaction> getTransactionByStatus(String status){
		if (!status.equals("Pending") && !status.equals("Finished")) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Invalid transaction status");
			return null;
		}
		
		List<Transaction> list = model.getTransactionByStatus(status);
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No transaction found");
		
		return list;
	}
	
	public void updateTransactionStatus(int transactionId, String status) {
		if(!status.equals("Finished")) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Status can only be changed to Finished");
			return;
		}
		
		model.updateTransactionStatus(transactionId, status);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Transaction has been marked as Finished");
	}
	
//	AMBIL TRANSACTION BERDASARKAN ID LAUNDRY STAFF
	public List<Transaction> getAssignedOrdersByLandryStaff(int laundryStaffId){
		if (laundryStaffId <= 0) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Invalid Laundry Staff ID");
			return null;
		}
		
		List<Transaction> list = model.getAssignedOrdersByLandryStaff(laundryStaffId);
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No orders assigned to you");
		
		return list;
	}
	
	public void assignOrderToLaundryStaff(int transactionId, int receptionistId, int laundryStaffId) {
		if(transactionId <= 0 || receptionistId <= 0 || laundryStaffId <= 0) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Invalid ID");
			return;
		}
		
		model.assignOrderToLaundryStaff(transactionId, receptionistId, laundryStaffId);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "SOrder assigned successfully");
	}
	
	public void orderLaundryService(int serviceId, int customerId, double totalWeight, String notes) {
		if(serviceId <= 0 || customerId <= 0) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Invalid ID");
			return;
		}
		
		String valid = validateOrder(customerId, notes);
		
		if(!valid.equals("Valid")) {
			showAlert(Alert.AlertType.ERROR, "!!!", valid);
			return;
		}
		
		model.orderLaundryService(serviceId, customerId, totalWeight, notes);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Order successful");
	}
	
	public List<Transaction> getTransactionByCustomerId(int customerId){		
		List<Transaction> list = model.getTransactionByCustomerId(customerId);
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No transaction found");
		
		return list;
	}
	
	public String validateOrder(double totalWeight, String notes) {
		if(totalWeight < 2 || totalWeight > 50)
			return "Total weight must be between 2kg and 50kg";
		if(notes.length() > 250)
			return "Notes must be less than or equal to 250";
		
		return "Valid";
	}
}
