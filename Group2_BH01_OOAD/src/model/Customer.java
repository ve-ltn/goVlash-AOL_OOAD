package model;

import java.time.LocalDate;

public class Customer extends User{

	public Customer(int userID, String userName, String userEmail, String userPassword, String userGender,
			LocalDate userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, userRole);
	}
	
}
