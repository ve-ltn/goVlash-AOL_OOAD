package controller;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AlertMessage;
import model.Employee;
import model.User;
import view.ViewAddNewEmployeePage;

public class UserController extends AlertMessage{
	private User model;
	private ViewAddNewEmployeePage view;
	
	public void addUser(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role) {
		String valid = validateAddCustomer(name, email, password, confirmPassword, gender, dob, role);
		
		if(!valid.equals("Valid")) {
			showAlert(Alert.AlertType.WARNING, "!!!", valid);
			return;
		}
		
		model.addUser(name, email, password, gender, dob, role);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "User addedd successfully");
	}
	
	public User login(String email, String password) {
		if(email.isEmpty() || password.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Email and password cannot be empty");
			return null;
		}
		
		User user = model.login(email, password);
		
		if(user == null) {
			showAlert(Alert.AlertType.ERROR, "!!!", "Invalid email or password");
			return null;
		}
		
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Login successfully");
		return user;
	}
	
	public void addEmployee(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role) {
		String valid = validateAddEmployee(name, email, password, confirmPassword, gender, dob, role);
		
		if(!valid.equals("Valid")) {
			showAlert(Alert.AlertType.WARNING, "!!!", valid);
			return;
		}
		
		model.addEmployee(name, email, confirmPassword, gender, dob, role);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Employee addedd successfully");
	}
	
	public List<User> getAllEmployees() {
		List<User> list = model.getAllEmployees();
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No employee found");

		return list;
	}
	
	public List<User> getUsersByRole(String role) {
		List<User> list = model.getUsersByRole(role);
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No user found for this role");
		
		return list;
	}
	
	public String validateAddEmployee(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role) {
		if(name.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || dob == null || role.isEmpty())
			return "Fields cannot be empty";
		if(!email.endsWith("@govlash.com"))
			return "Email must ends with @govlash.com";
		if(password.length() < 6)
			return "Password must be at least 6 characters long";
		if(!confirmPassword.equals(password))
			return "Confirm Password must be equal to password";
		if(!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))
			return "Gender must be Male or Female";
		LocalDate minDate = LocalDate.now().minusYears(17);
		if(dob.isAfter(minDate))
			return "Must be at least 17 years old";
		if(!role.equals("Admin") && !role.equals("Laundry Staff") && !role.equals("Receptionist"))
			return "Role must be either Admin, Laundry Staff, or Receptionist";
		
		return "Valid";
	}
	
	public String validateAddCustomer(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role) {
		if(name.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || dob == null || role.isEmpty())
			return "Fields cannot be empty";
		if(!email.endsWith("@email.com"))
			return "Email must ends with @email.com";
		if(password.length() < 6)
			return "Password must be at least 6 characters long";
		if(!confirmPassword.equals(password))
			return "Confirm Password must be equal to password";
		if(!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))
			return "Gender must be Male or Female";
		LocalDate minDate = LocalDate.now().minusYears(12);
		if(dob.isAfter(minDate))
			return "Must be at least 12 years old";
		if(!role.equals("Customer"))
			return "Role must be Customer";
		
		return "Valid";
	}
	
	public User getUserByEmail(String email) {
		User user = model.getUserByEmail(email);
		
		if(user == null)
			showAlert(Alert.AlertType.INFORMATION, "!!!", "User not found");
		
		return user;
	}
	
	public User getUserByName(String name) {
		User user = model.getUserByName(name);
		
		if(user == null)
			showAlert(Alert.AlertType.INFORMATION, "!!!", "User not found");
		
		return user;
	}
}
