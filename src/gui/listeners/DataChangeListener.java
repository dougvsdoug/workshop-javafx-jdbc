package gui.listeners;

public interface DataChangeListener {// this interface allows a object to listen an event of another object
	// when a object(subject -> in this program is the DepartmentFormController) change the data it emits
	//an event and another object(observer - listener) perform
	// an action when the event is received
	
	void onDataChanged();//the event works when data is changed 
}
