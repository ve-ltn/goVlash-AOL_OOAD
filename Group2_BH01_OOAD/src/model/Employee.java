package model;

import java.time.LocalDate;

public class Employee {
	private int employeeID;
    private String employeeName;
    private String employeeEmail;
    private String employeePassword;
    private String employeeGender;
    private LocalDate employeeDOB;
    private String employeeRole;

    public Employee(int employeeID, String employeeName, String employeeEmail, String employeePassword, String employeeGender, LocalDate employeeDOB, String employeeRole) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePassword = employeePassword;
        this.employeeGender = employeeGender;
        this.employeeDOB = employeeDOB;
        this.employeeRole = employeeRole;
    }
    
	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public String getEmployeeGender() {
		return employeeGender;
	}

	public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
	}

	public LocalDate getEmployeeDOB() {
		return employeeDOB;
	}

	public void setEmployeeDOB (LocalDate employeeDOB) {
		this.employeeDOB = employeeDOB;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}
}
