package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {// nesse caso ActionEvent � o evento que o bot�o recebeu
		return (Stage) ((Node) event.getSource()).getScene().getWindow() ;//retorna o palco atual
	}
}
