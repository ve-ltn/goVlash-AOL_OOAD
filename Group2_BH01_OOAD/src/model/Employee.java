package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import util.MySQLConnection;

public class Employee extends User {
	public Employee(int userID, String userName, String userEmail, String userPassword, String userGender,
			LocalDate userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, userRole);
		// TODO Auto-generated constructor stub
	}
	
}
