package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();// a list of objects that listen
	// an event. Another objects subscribe in the list (using the method subscribeDataChangeListener) and 
	//receive an event
	
	@FXML
 	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService( DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {// subscribe a listener
		//in the list		
		dataChangeListeners.add(listener);
	}
	
	public void onBtSaveAction(ActionEvent event) {
		
		if( entity == null ) {
			throw new IllegalStateException("Entity was null");
		}
		
		if( service == null ) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();//notify the listener
			Utils.currentStage(event).close();
		}catch( DbException e ) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {//notify the listener
		//execute the method onDataChanged of the Interface DataChangeListener in each listener of the list
		//dataChangeListeners
		// in another words, it will emit the event for the listeners
		
		for( DataChangeListener listener: dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {// it gets the data of the textFields, instantiates a Department and return
		// the new Department
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		return obj;
	}

	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {//restrições
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

	public void updateFormData() {
		
		if( entity ==  null ) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
