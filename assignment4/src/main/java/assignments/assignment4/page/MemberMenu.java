package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class MemberMenu {
    private Scene scene;

    abstract protected Scene createBaseMenu();

    // Shows alert
    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Gets Scene
    public Scene getScene(){
        return this.scene;
    }

    // Refresh
    protected void refresh(){

    }

}
