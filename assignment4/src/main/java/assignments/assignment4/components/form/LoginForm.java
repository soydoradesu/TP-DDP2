package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    // Creates the login form scene
    private Scene createLoginForm() {
        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Create and add the title label
        Label titleLabel = new Label("Welcome to DepeFood");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        grid.add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(titleLabel, VPos.CENTER);

        // Create and add labels and text fields for name and phone
        Label nameLabel = new Label("Name:");
        nameInput = new TextField();
        Label phoneLabel = new Label("Phone Number:");
        phoneInput = new TextField();

        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneInput, 1, 2);

        // Create and add the login button
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(75);
        grid.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.RIGHT);

        // Set button action to handle login
        loginButton.setOnAction(e -> handleLogin());

        // Set grid alignment
        grid.setAlignment(Pos.CENTER);

        return new Scene(grid, 400, 600);
    }

    // Handles the login input
    private void handleLogin() {
        // Get input values
        String name = nameInput.getText().trim();
        String phone = phoneInput.getText().trim();

        // Validate inputs
        if (name.isEmpty() || phone.isEmpty()) {
            showAlert("Login Failed", "User not found!", "Please check your credentials.");
            return;
        }

        User user = DepeFood.getUser(name, phone);

        if (user != null) {
            if (user.role.equals("Admin")) {
                stage.setScene(new AdminMenu(stage, mainApp, user).getScene());
                stage.show();
            } else {
                stage.setScene(new CustomerMenu(stage, mainApp, user).getScene());
                stage.show();
            }
        } else {
            showAlert("Login Error", "User not found!", "Please check your credentials.");
        }
    }
        

    // Shows alert
    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getter method for scene
    public Scene getScene() {
        return this.createLoginForm();
    }
}
