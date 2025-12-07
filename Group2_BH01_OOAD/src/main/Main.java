package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Customer;
import model.Employee;
import model.Notification;
import model.Service;
import model.Transaction;
import util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.*;

public class Main extends Application{
	
	// table Employee
	private TableView<Employee> employeeTable = new TableView<>();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private TextField employeeIDField = new TextField();
    private TextField employeeNameField = new TextField();
    private TextField employeeEmailField = new TextField();
    private PasswordField employeePasswordField = new PasswordField();
    private PasswordField employeeConfirmPasswordField = new PasswordField();
    private ComboBox<String> employeeGenderComboBox = new ComboBox<>();
    private ComboBox<String> employeeRoleComboBox = new ComboBox<>();
    private DatePicker employeeDOBDatePicker = new DatePicker();
    // table Service
    private TableView<Service> serviceTable = new TableView<>();
    private ObservableList<Service> services = FXCollections.observableArrayList();
    private TextField serviceIDField = new TextField();
    private TextField serviceNameField = new TextField();
    private TextField serviceDescriptionField = new TextField();
    private TextField servicePriceField = new TextField();
    private TextField serviceDurationField = new TextField();
    // table customer
    private TableView<Customer> customerTable = new TableView<>();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private TextField customerIDField = new TextField();
    private TextField customerNameField = new TextField();
    private TextField customerEmailField = new TextField();
    private PasswordField customerPasswordField = new PasswordField();
    private PasswordField customerConfirmPasswordField = new PasswordField();
    private ComboBox<String> customerGenderComboBox = new ComboBox<>();
    private ComboBox<String> customerRoleComboBox = new ComboBox<>();
    private DatePicker customerDOBDatePicker = new DatePicker();
    // table notification
    private TableView<Notification> notificationTable = new TableView<>();
    private ObservableList<Notification> notifications = FXCollections.observableArrayList();
    private TextField notifIDField = new TextField();
    private TextField recipientIDField = new TextField();
    private TextField messageField = new TextField();
    private DatePicker createdAtField = new DatePicker();
    private CheckBox isReadCheckBox = new CheckBox();
    // table transaction
    private TableView<Transaction> transactionTable = new TableView<>();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private TextField transactionIDField = new TextField();                       
    private ComboBox<Integer> transactionServiceIDField = new ComboBox<>();     
    private TextField transactionCustomerIDField = new TextField();               
    private TextField transactionReceptionistIDField = new TextField();          
    private ComboBox<Integer> transactionLaundryStaffIDField = new ComboBox<>();  
    private DatePicker transactionDateField = new DatePicker();                  
    private ComboBox<String> statusField = new ComboBox<>();                      
    private Spinner<Double> totalWeightField = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 50.0, 1.0, 0.5));         
    private TextArea notesField = new TextArea();             
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
	}
	
	private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	private void clearServiceForm() {
		serviceIDField.clear();
		serviceNameField.clear();
		serviceDescriptionField.clear();
		servicePriceField.clear();
		serviceDurationField.clear();
	}
	private void loadServiceData() {
		services.clear();
		
//		 ADA DI CLASS MODEL SERVICE getAllServices()
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Service")) {

        	while (rs.next()) {
        		services.add(new Service(
        	            rs.getInt("ID"),
        	            rs.getString("Name"),
        	            rs.getString("Description"),
        	            rs.getDouble("Price"),
        	            rs.getInt("Duration")
        	    ));
        	}
        	serviceTable.setItems(services);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	private void insertService() {
		int serviceID = Integer.parseInt(serviceIDField.getText());
		String serviceName = serviceNameField.getText();
		String serviceDescription = serviceDescriptionField.getText();
		double servicePrice = Double.parseDouble(servicePriceField.getText());
		int serviceDuration = Integer.parseInt(serviceDurationField.getText());

		if (serviceName.isEmpty() || serviceDescription.isEmpty() || servicePriceField.getText().isEmpty() || serviceDurationField.getText().isEmpty()) {
			showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields!");
			return;
		}
		
//		ADA DI CLASS MODEL SERVICE addService(name, description, price, duration)
        try (Connection conn = MySQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Service (Name, Description, Price, Duration) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, serviceName);
            stmt.setString(2, serviceDescription);
            stmt.setDouble(3, servicePrice);   
            stmt.setInt(4, serviceDuration);    
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "New service added!");
            clearServiceForm();
            loadServiceData();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add service!");
        }
	}
	
	private void clearTransactionForm() {
		transactionIDField.clear();                            
	    transactionServiceIDField.getSelectionModel().clearSelection(); 
	    transactionCustomerIDField.clear();                     
	    transactionReceptionistIDField.clear();                  
	    transactionLaundryStaffIDField.getSelectionModel().clearSelection(); 
	    transactionDateField.setValue(null);                     
	    statusField.getSelectionModel().clearSelection();          
	    totalWeightField.getValueFactory().setValue(2.0);
	    notesField.clear();        
	}
	
	
	private void loadTransactionData() {
		transactions.clear();
		
//		ADA DI CLASS MODEL TRANSACTION getAllTransaction()
	    try (Connection conn = MySQLConnection.getInstance().getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM Transaction")) {
	        while (rs.next()) {
	            transactions.add(new Transaction(
	                rs.getInt("ID"),
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
	        transactionTable.setItems(transactions);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	private void insertTransaction() {
		Integer serviceID = transactionServiceIDField.getValue();
	    String customerID = transactionCustomerIDField.getText();
	    String receptionistID = transactionReceptionistIDField.getText();
	    Integer laundryStaffID = transactionLaundryStaffIDField.getValue();
	    LocalDate transactionDateOnly = transactionDateField.getValue();
	    statusField.getItems().addAll("Pending", "Finished");
	    Double totalWeight = totalWeightField.getValue();
	    String notes = notesField.getText();
	    if (serviceID == null || customerID.isEmpty() || receptionistID.isEmpty() ||
	        laundryStaffID == null || transactionDateOnly == null ||
	        totalWeight == null) {

	        showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields!");
	        return;
	    }
	    int customerIDInt, receptionistIDInt;

	    try {
	        customerIDInt = Integer.parseInt(customerID);
	        receptionistIDInt = Integer.parseInt(receptionistID);
	    } catch (NumberFormatException e) {
	        showAlert(Alert.AlertType.WARNING, "Input Error", "Customer ID and Receptionist ID must be numbers!");
	        return;
	    }
	    LocalDateTime transactionDate = transactionDateOnly.atStartOfDay();
	    
//	    ADA DI CLASS MODEL TRANSACTION orderLaundryService(serviceId, customerId, totalWeight, notes)
	    try (Connection conn = MySQLConnection.getInstance().getConnection()) {
	        String query = "INSERT INTO Transaction " +
	                "(ServiceID, CustomerID, ReceptionistID, LaundryStaffID, TransactionDate, Status, TotalWeight, Notes) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setInt(1, serviceID);
	        stmt.setInt(2, customerIDInt);
	        stmt.setInt(3, receptionistIDInt);
	        stmt.setInt(4, laundryStaffID);
	        stmt.setTimestamp(5, Timestamp.valueOf(transactionDate));
	        stmt.setString(6, status);
	        stmt.setDouble(7, totalWeight);
	        stmt.setString(8, notes);
	        stmt.executeUpdate();
	        showAlert(Alert.AlertType.INFORMATION, "Success", "New transaction added!");
	        clearTransactionForm();
	        loadTransactionData();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add transaction!");
	    }
	}
	
	private void clearCustomerForm() {
	    customerIDField.clear();
	    customerNameField.clear();
	    customerEmailField.clear();
	    customerPasswordField.clear();
	    customerConfirmPasswordField.clear();
	    customerGenderComboBox.getSelectionModel().clearSelection(); 
	    customerDOBDatePicker.setValue(null);                       
	    customerRoleComboBox.getSelectionModel().clearSelection(); 
	}
	private void loadCustomerData() {
		customers.clear();
		
//		ADA DI CLASS MODEL USER bisa pake getUserByRole(role)
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

        	while (rs.next()) {
        	    customers.add(new Customer(
        	            rs.getInt("ID"),
        	            rs.getString("Name"),
        	            rs.getString("Email"),
        	            rs.getString("Password"),
        	            rs.getString("Gender"),
        	            rs.getDate("DOB").toLocalDate(),
        	            rs.getString("Role")
        	    ));
        	}
        	customerTable.setItems(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		
	private void insertCustomer() {
		int customerID = Integer.parseInt(employeeIDField.getText());
		String customerName = customerNameField.getText();
		String customerEmail = customerEmailField.getText();
		String customerPassword = customerPasswordField.getText();
		String confirmPassword = customerConfirmPasswordField.getText();
		String customerGender = customerGenderComboBox.getValue();
		LocalDate customerDOB = customerDOBDatePicker.getValue();
		String customerRole = customerRoleComboBox.getValue();

        if (customerName.isEmpty() || customerEmail.isEmpty() || customerPassword.isEmpty() || customerGender.isEmpty() || customerDOB == null || customerRole.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields!");
            return;
        }
        if (!customerEmail.endsWith("@govlash.com")) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Email must end with '@email.com'!");
            return;
        }
        if (customerPassword.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Password must be at least 6 characters long!");
            return;
        }
        if (!confirmPassword.equals(customerPassword)) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Confirm Password must match the Password!");
            return;
        }

        if (!(customerGender.equalsIgnoreCase("Male") || customerGender.equalsIgnoreCase("Female"))) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Gender must be either 'Male' or 'Female'!");
            return;
        }
        LocalDate minDate = LocalDate.now().minusYears(12);
        if (customerDOB.isAfter(minDate)) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Employee must be at least 12 years old!");
            return;
        }
        if (!(customerRole.equalsIgnoreCase("Customer"))) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Role must be: Customer!");
            return;
        }
        
//        ADA DI CLASS MODEL USER addUser(name, email, password, gender, dob, role)
        try (Connection conn = MySQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO CUSTOMER (Name, Email, Password, Gender, DOB, Role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, customerName);
            stmt.setString(2, customerEmail);
            stmt.setString(3, customerPassword);
            stmt.setString(4, customerGender);
            stmt.setDate(5, java.sql.Date.valueOf(customerDOB));
            stmt.setString(6, customerRole);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "New customer added!");
            clearCustomerForm();
            loadCustomerData();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add customer!");
        }
    }
	
	private void clearNotifForm() {
	    notifIDField.clear();
	    recipientIDField.clear();
	    messageField.clear();
	    createdAtField.setValue(null);
	    isReadCheckBox.setSelected(false);
	}
	
//	GADA KUMASUKIN KE MODEL SOALNYA GATAU KEPAKE ATO GK (GK ADA DI DIAGRAM)
	private void loadNotifData() {
		notifications.clear();
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Notification")) {

        	while (rs.next()) {
        		notifications.add(new Notification(
        	            rs.getInt("Notification ID"),
        	            rs.getInt("Recipient ID"),
        	            rs.getString("Notification Message"),
        	            rs.getTimestamp("Created At").toLocalDateTime(),
        	            rs.getBoolean("Is Read")
        	    ));
        	}
        	notificationTable.setItems(notifications);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void insertNotification() {
		int notifID = Integer.parseInt(notifIDField.getText());
		int recipientID = Integer.parseInt(recipientIDField.getText());
		String message = messageField.getText();
		LocalDateTime createdAt = createdAtField.getValue().atTime(LocalTime.now());
		boolean isRead = isReadCheckBox.isSelected();
		
        if (notifIDField.getText().isEmpty() || recipientIDField.getText().isEmpty() || message.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields!");
            return;
        }
        
//        ADA DI CLASS MODEL NOTIFICATION sendNotification(recipientId, message)
        try (Connection conn = MySQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Notification (`Notification ID`, `Recipient ID`, `Notification Message`, `Created At`, `Is Read`)\r\n" + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            PreparedStatement checkRecipient = conn.prepareStatement(
            	    "SELECT * FROM Customer WHERE CustomerID = ?"
            	);
            	checkRecipient.setInt(1, recipientID);
            	ResultSet recRS = checkRecipient.executeQuery();

            	if (!recRS.next()) {
            	    showAlert(Alert.AlertType.ERROR, "Validation Error", 
            	              "Recipient ID does not exist in Customer table.");
            	    return;
            	}
            stmt.setInt(1, notifID);
            stmt.setInt(2, recipientID);
            stmt.setString(3, message);
            stmt.setTimestamp(4, Timestamp.valueOf(createdAt));
            stmt.setBoolean(5, isRead);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "New notification added!");
            clearNotifForm();
            loadNotifData();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add notification!");
        }
    }
	
	private void clearEmployeeForm() {
	    employeeIDField.clear();
	    employeeNameField.clear();
	    employeeEmailField.clear();
	    employeePasswordField.clear();
	    employeeConfirmPasswordField.clear();
	    employeeGenderComboBox.getSelectionModel().clearSelection(); 
	    employeeDOBDatePicker.setValue(null);                       
	    employeeRoleComboBox.getSelectionModel().clearSelection(); 
	}
	private void loadEmployeeData() {
		employees.clear();
		
//		ADA DI CLASS MODEL USER getAllEmployees()
        try (Connection conn = MySQLConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {

        	while (rs.next()) {
        	    employees.add(new Employee(
        	            rs.getInt("ID"),
        	            rs.getString("Name"),
        	            rs.getString("Email"),
        	            rs.getString("Password"),
        	            rs.getString("Gender"),
        	            rs.getDate("DOB").toLocalDate(),
        	            rs.getString("Role")
        	    ));
        	}
        	employeeTable.setItems(employees);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void insertEmployee() {
		int employeeID = Integer.parseInt(employeeIDField.getText());
		String employeeName = employeeNameField.getText();
		String employeeEmail = employeeEmailField.getText();
		String employeePassword = employeePasswordField.getText();
		String confirmPassword = employeeConfirmPasswordField.getText();
		String employeeGender = employeeGenderComboBox.getValue();
		LocalDate employeeDOB = employeeDOBDatePicker.getValue();
		String employeeRole = employeeRoleComboBox.getValue();

        if (employeeName.isEmpty() || employeeEmail.isEmpty() || employeePassword.isEmpty() || employeeGender.isEmpty() || employeeDOB == null || employeeRole.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields!");
            return;
        }
        if (!employeeEmail.endsWith("@govlash.com")) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Email must end with '@govlash.com'!");
            return;
        }
        if (employeePassword.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Password must be at least 6 characters long!");
            return;
        }
        if (!confirmPassword.equals(employeePassword)) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Confirm Password must match the Password!");
            return;
        }

        if (!(employeeGender.equalsIgnoreCase("Male") || employeeGender.equalsIgnoreCase("Female"))) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Gender must be either 'Male' or 'Female'!");
            return;
        }
        LocalDate minDate = LocalDate.now().minusYears(17);
        if (employeeDOB.isAfter(minDate)) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Employee must be at least 17 years old!");
            return;
        }
        if (!(employeeRole.equalsIgnoreCase("Admin") || employeeRole.equalsIgnoreCase("Laundry Staff") || employeeRole.equalsIgnoreCase("Receptionist"))) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Role must be either: Admin, Laundry Staff, or Receptionist!");
            return;
        }
        
//      ADA DI CLASS MODEL USER addEmployee(name, email, password, gender, dob, role)
        try (Connection conn = MySQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Employee (Name, Email, Password, Gender, DOB, Role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, employeeName);
            stmt.setString(2, employeeEmail);
            stmt.setString(3, employeePassword);
            stmt.setString(4, employeeGender);
            stmt.setDate(5, java.sql.Date.valueOf(employeeDOB));
            stmt.setString(6, employeeRole);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "New employee added!");
            clearEmployeeForm();
            loadEmployeeData();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add employee!");
        }
    }
}
