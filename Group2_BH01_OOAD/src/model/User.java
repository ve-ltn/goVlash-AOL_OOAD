package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import util.MySQLConnection;

public class User {
	private int userID;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userGender;
    private LocalDate userDOB;
    private String userRole;
    
	private final Connection db = MySQLConnection.getInstance().getConnection();
    
	public User(int userID, String userName, String userEmail, String userPassword, String userGender,
			LocalDate userDOB, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public LocalDate getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(LocalDate userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
    
	public void addUser(String name, String email, String password, String gender, LocalDate dob, String role) {
		String query = "INSERT INTO user (Name, Email, Password, Gender, DOB, Role) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);
			stmt.setString(4, gender);
			stmt.setDate(5, java.sql.Date.valueOf(dob));
			stmt.setString(6, role);
		
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addEmployee(String name, String email, String password, String gender, LocalDate dob, String role) {
		String query = "INSERT INTO user (Name, Email, Password, Gender, DOB, Role) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt;
	
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);
			stmt.setString(4, gender);
			stmt.setDate(5, java.sql.Date.valueOf(dob));
			stmt.setString(6, role);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<User> getUsersByRole(String role) {
		List<User> list = new ArrayList<User>();
		String query = "SELECT * FROM user "
				+ "WHERE Role = ? "
				+ "ORDER BY ID DESC";
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, role);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				User user = mapUser(rs);
				if(user != null)
					list.add(user);
			}	
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public User getUserByEmail(String email) {
		String query = "SELECT * FROM user "
				+ "WHERE Email = ? ";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return mapUser(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByName(String name) {
		String query = "SELECT * FROM user "
				+ "WHERE Name = ? "
				+ "ORDER BY ID DESC";
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, name);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return mapUser(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<User> getAllEmployees() {
		List<User> list = new ArrayList<User>();
		String query = "SELECT * FROM user "
				+ "WHERE Role != 'Customer'";
		PreparedStatement stmt;
		
		try {
			stmt = db.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				User user = mapUser(rs);
				if(user != null)
					list.add(user);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public User login(String email, String password) {
		String query = "SELECT * FROM user "
				+ "WHERE Email = ? AND Password = ?";
		
		PreparedStatement stmt;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
				return mapUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private User mapUser(ResultSet rs) {
		try {
			String role = rs.getString("Role");
			if(role.equals("Customer")) {
				return new Customer(
						rs.getInt("ID"), 
						rs.getString("Name"), 
						rs.getString("Email"), 
						rs.getString("Password"), 
						rs.getString("Gender"), 
						rs.getDate("DOB").toLocalDate(),
						rs.getString("Role")
						);
			}
			else if(role.equals("Admin")) {
				return new Admin(
						rs.getInt("ID"), 
						rs.getString("Name"), 
						rs.getString("Email"), 
						rs.getString("Password"), 
						rs.getString("Gender"), 
						rs.getDate("DOB").toLocalDate(),
						rs.getString("Role")
						);
			}
			else if(role.equals("Laundry Staff")) {
				return new LaundryStaff(
						rs.getInt("ID"), 
						rs.getString("Name"), 
						rs.getString("Email"), 
						rs.getString("Password"), 
						rs.getString("Gender"), 
						rs.getDate("DOB").toLocalDate(),
						rs.getString("Role")
						);
			}
			else if(role.equals("Receptionist")) {
				return new Receptionist(
						rs.getInt("ID"), 
						rs.getString("Name"), 
						rs.getString("Email"), 
						rs.getString("Password"), 
						rs.getString("Gender"), 
						rs.getDate("DOB").toLocalDate(),
						rs.getString("Role")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
