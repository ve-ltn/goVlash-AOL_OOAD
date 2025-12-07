package controller;

import java.util.List;

import javafx.scene.control.Alert;
import model.AlertMessage;
import model.Service;

public class ServiceController extends AlertMessage{
	private Service model;
	
	public void addService(String name, String description, double price, int duration) {
		String valid = validateAddService(name, description, price, duration);
		
		if(!valid.equals("Valid")) {
			showAlert(Alert.AlertType.ERROR, "!!!", valid);
			return;
		}
		
		addService(name, description, price, duration);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Service addedd successfully");
	}
	
	public void editService(int serviceID, String name, String description, double price, int duration) {
		String valid = validateEditService(name, description, price, duration);
		
		if(!valid.equals("Valid")) {
			showAlert(Alert.AlertType.ERROR, "!!!", valid);
			return;
		}
		
		model.editService(serviceID, name, description, price, duration);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "successfully edited the service");
	}
	
	public void deleteService(int serviceID) {
		model.deleteService(serviceID);
		showAlert(Alert.AlertType.INFORMATION, "!!!", "Successfully deleted the service");
	}
	
//	AMBIL SEMUA SERVICE
	public List<Service> getAllServices(){
		List<Service> list = model.getAllServices();
		
		if(list.isEmpty())
			showAlert(Alert.AlertType.INFORMATION, "!!!", "No services found");
		
		return list;
	}
	
	public String validateAddService(String name, String description, double price, int duration) {
		if(name.isEmpty() || description.isEmpty())
			return "Field must not be empty";
		if(name.length() > 50)
			return "Name length must be les than or equal to 50 characters";
		if(description.length() > 250)
			return "Description length must be les than or equal to 250 characters";
		if(price <= 0)
			return "Price must be greater than 0";
		if(duration < 1 || duration > 30)
			return "Duration must be between 1 and 30 days";
		
		return "Valid";
	}
	
	public String validateEditService(String name, String description, double price, int duration) {
		if(name.isEmpty() || description.isEmpty())
			return "Field must not be empty";
		if(name.length() > 50)
			return "Name length must be les than or equal to 50 characters";
		if(description.length() > 250)
			return "Description length must be les than or equal to 250 characters";
		if(price <= 0)
			return "Price must be greater than 0";
		if(duration < 1 || duration > 30)
			return "Duration must be between 1 and 30 days";
		
		return "Valid";
	}
}
