package model;

import java.time.LocalDate;

public class Customer {
	private int customerID;
	private String customerName;
	private String customerEmail;
	private String customerPassword;
	private String customerGender;
    private LocalDate customerDOB;
    private String customerRole;
    
	public Customer(int customerID, String customerName, String customerEmail, String customerPassword, String customerGender, LocalDate customerDOB, String customerRole) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPassword = customerPassword;
		this.customerGender = customerGender;
		this.customerDOB = customerDOB;
		this.customerRole = customerRole;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	public String getCustomerGender() {
		return customerGender;
	}
	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}
	public LocalDate getCustomerDOB() {
		return customerDOB;
	}
	public void setCustomerDOB(LocalDate customerDOB) {
		this.customerDOB = customerDOB;
	}
	public String getCustomerRole() {
		return customerRole;
	}
	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}
    
    
}
