package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;

	@FXML
	private MenuItem menuItemDepartment;

	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {// Action � o evento padr�o do controller
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction() {// Action � o evento padr�o do controller
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) ->{
			controller.setDepartmentService( new DepartmentService() );
			controller.updateTableView();;
		});
	}

	@FXML
	public void onMenuItemAboutAction() {// Action � o evento padr�o do controller
		loadView("/gui/About.fxml", x -> {} );
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	public synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {// synchronized garante q o processamento do 
		// m�todo n�o ser� interrompido durante o multi-threading
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene =  Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);// main menu recebe o primeiro filho do mainVbox
			// q � o menu q fica na parte superior da janela principal
			mainVBox.getChildren().clear();
			
			//agora vamos criar a nova tela nas linhas abaixo
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			//manipulamos a cena principal incluindo nela o main menu e os filhos da janela q estamos abrindo
			
			T controller = loader.getController();
			initializingAction.accept(controller);//aqui realiza a fun��o accept da interface Consumer de nome
			// initializingAction,o m�todo accept do Consumer � void
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
		
}
