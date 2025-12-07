package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import util.MySQLConnection;

public class Service {
	private int serviceID;
	private String serviceName;
	private String serviceDescription;
	private double servicePrice;
	private int serviceDuration;
	
	private final Connection db = MySQLConnection.getInstance().getConnection();
	
	public Service(int serviceID, String serviceName, String serviceDescription, double servicePrice, int serviceDuration) {
		this.serviceID = serviceID;
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.servicePrice = servicePrice;
		this.serviceDuration = serviceDuration;
	}

	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public int getServiceDuration() {
		return serviceDuration;
	}

	public void setServiceDuration(int serviceDuration) {
		this.serviceDuration = serviceDuration;
	}
	
	public List<Service> getAllServices(){
		List<Service> list = new ArrayList<Service>();
		String query = "SELECT * FROM service";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(new Service(
						rs.getInt("ID"), 
						rs.getString("Name"), 
						rs.getString("Description"), 
						rs.getDouble("Price"), 
						rs.getInt("Duration")
				));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void editService(int serviceID, String name, String description, double price, int duration) {
		String query = "UPDATE service SET "
				+ "Name = ?, "
				+ "Description = ?, "
				+ "Price = ?, "
				+ "Duration = ?, "
				+ "WHERE ServiceID = ?";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setDouble(3, price);
			stmt.setInt(4, duration);
			stmt.setInt(5, serviceID);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteService(int serviceID) {
		String query = "DELETE FROM service "
				+ "WHERE ServiceID = ?";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setInt(1, serviceID);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addService(String name, String description, double price, int duration) {
		String query = "INSERT INTO service (Name, Description, Price, Duration) "
				+ "VALUES (?, ?, ?, ?";
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setDouble(3, price);
			stmt.setInt(4, duration);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
