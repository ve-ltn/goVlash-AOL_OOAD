package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import util.MySQLConnection;

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
	
	private final Connection db = MySQLConnection.getInstance().getConnection();
	
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
	
	public List<Transaction> getAllTransaction(){
		List<Transaction> list = new ArrayList<Transaction>();
		String query = "SELECT * FROM transaction "
				+ "ORDER BY TransactionDate DESC";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs != null && rs.next()) {
				list.add(new Transaction(
						rs.getInt("TransactionID"), 
						rs.getInt("ServiceID"), 
						rs.getInt("CustomerID"), 
						rs.getInt("ReceptionistID"), 
						rs.getInt("LaundryStaffID"), 
						rs.getTimestamp("TransactionDate").toLocalDateTime(), 
						rs.getString("Status"), 
						rs.getDouble("TotalWeight"), 
						rs.getString("Notes")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Transaction> getTransactionByStatus(String status){
		List<Transaction> list = new ArrayList<Transaction>();
		
		String query = "SELECT * FROM transaction "
				+ "WHERE Status = ?";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, status);
			ResultSet rs = stmt.executeQuery();
			while(rs != null && rs.next()) {
				list.add(new Transaction(
						rs.getInt("TransactionID"), 
						rs.getInt("ServiceID"), 
						rs.getInt("CustomerID"), 
						rs.getInt("ReceptionistID"), 
						rs.getInt("LaundryStaffID"), 
						rs.getTimestamp("TransactionDate").toLocalDateTime(), 
						rs.getString("Status"), 
						rs.getDouble("TotalWeight"), 
						rs.getString("Notes")
				));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Transaction> getTransactionByCustomerId(int customerId){
		List<Transaction> list = new ArrayList<Transaction>();
		String query = "SELECT * FROM transaction "
				+ "WHERE CustomerID = ?";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, customerId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs != null && rs.next()) {
				list.add(new Transaction(
						rs.getInt("TransactionID"), 
						rs.getInt("ServiceID"), 
						rs.getInt("CustomerID"), 
						rs.getInt("ReceptionistID"), 
						rs.getInt("LaundryStaffID"), 
						rs.getTimestamp("TransactionDate").toLocalDateTime(), 
						rs.getString("Status"), 
						rs.getDouble("TotalWeight"), 
						rs.getString("Notes")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Transaction> getAssignedOrdersByLandryStaff(int laundryStaffId){
		List<Transaction> list = new ArrayList<Transaction>();
		String query = "SELECT * FROM transaction "
				+ "WHERE LaundryStaffID = ? "
				+ "ORDER BY TransactionDate DESC";
		PreparedStatement stmt;
			try {
				stmt = db.prepareStatement(query);
				stmt.setInt(1, laundryStaffId);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					list.add(new Transaction(
							rs.getInt("TransactionID"), 
							rs.getInt("ServiceID"), 
							rs.getInt("CustomerID"), 
							rs.getInt("ReceptionistID"), 
							rs.getInt("LaundryStaffID"), 
							rs.getTimestamp("TransactionDate").toLocalDateTime(), 
							rs.getString("Status"), 
							rs.getDouble("TotalWeight"), 
							rs.getString("Notes")
					));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return list;
	}
	
	public void orderLaundryService(int serviceId, int customerId, double totalWeight, String notes) {
		String query = "INSERT INTO transaction (ServiceID, CustomerID, ReceptionistID, LaundryStaffID, TransactionDate, Status, TotalWeight, Notes) "
				+ "VALUES (?, ?, NULL, NULL, NOW(), ?, ?, ?)";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, serviceId);
			stmt.setInt(2, customerId);
			stmt.setString(3, "Pending");
			stmt.setDouble(4, totalWeight);
			stmt.setString(5, notes);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTransactionStatus(int transactionId, String status) {
		String query = "UPDATE transaction "
				+ "SET Status = ? "
				+ "WHERE TransactionID = ?";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, status);
			stmt.setInt(2, transactionId);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assignOrderToLaundryStaff(int transactionId, int receptionistId, int laundryStaffId) {
		String query = "UPDATE transaction "
				+ "SET ReceptionistID = ?, LaundryStaffID = ? "
				+ "WHERE TransactionID = ? ";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, receptionistId);
			stmt.setInt(2, laundryStaffId);
			stmt.setInt(3, transactionId);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
