package model;

import java.time.LocalDateTime;

public class Transaction {
	private int transactionID;
	private int serviceID;
	private int customerID;
	private int receptionistID;
	private int laundryStaffID;
	private LocalDateTime transactionDate;
	private String status;
	private double totalWeight;
	private String notes;
	
	public Transaction(int transactionID, int serviceID, int customerID, int receptionistID, int laundryStaffID,
			LocalDateTime transactionDate, String status, double totalWeight, String notes) {
		this.transactionID = transactionID;
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.receptionistID = receptionistID;
		this.laundryStaffID = laundryStaffID;
		this.transactionDate = transactionDate;
		this.status = status;
		this.totalWeight = totalWeight;
		this.notes = notes;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getReceptionistID() {
		return receptionistID;
	}
	public void setReceptionistID(int receptionistID) {
		this.receptionistID = receptionistID;
	}
	public int getLaundryStaffID() {
		return laundryStaffID;
	}
	public void setLaundryStaffID(int laundryStaffID) {
		this.laundryStaffID = laundryStaffID;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
