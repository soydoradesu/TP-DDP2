package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox;
    private ComboBox<String> viewRestaurantComboBox;

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome, " + this.user.getNama() + "!");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        Button addRestaurantButton = new Button("Add Restaurant");
        Button addMenuButton = new Button("Add Menu to Restaurant");
        Button viewRestaurantButton = new Button("Restaurant List");
        Button logOutButton = new Button("Log Out");

        addRestaurantButton.setOnAction(e -> stage.setScene(addRestaurantScene));
        addMenuButton.setOnAction(e -> stage.setScene(addMenuScene));
        viewRestaurantButton.setOnAction(e -> {
            refresh();
            stage.setScene(viewRestaurantsScene);
        });
        logOutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(titleLabel, addRestaurantButton, addMenuButton, viewRestaurantButton, logOutButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Restaurant");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Restaurant Name:");
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> handleTambahRestoran(restaurantNameField.getText()));
        backButton.setOnAction(e -> stage.setScene(scene));

        layout.getChildren().addAll(titleLabel, restaurantNameField, submitButton, backButton);
        return new Scene(layout, 400, 600);
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Menu Item");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label restaurantLabel = new Label("Choose a restaurant");
        restaurantComboBox = new ComboBox<>(FXCollections.observableArrayList(
                restoList.stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));

        Label itemNameLabel = new Label("Menu Item Name");
        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");

        Label itemPriceLabel = new Label("Price");
        TextField itemPriceField = new TextField();
        itemPriceField.setPromptText("Price");

        Button submitButton = new Button("Add Menu");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String selectedRestaurantName = restaurantComboBox.getValue();
            String itemName = itemNameField.getText();
            String priceText = itemPriceField.getText();

            if (selectedRestaurantName == null || itemName.isEmpty() || priceText.isEmpty()) {
                showAlert("Error", "Invalid Menu!", "Please fill all the required information!", Alert.AlertType.ERROR);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                Restaurant selectedRestaurant = findRestaurantByName(selectedRestaurantName);

                if (selectedRestaurant != null) {
                    handleTambahMenuRestoran(selectedRestaurant, itemName, price);
                } else {
                    showAlert("Error", "Restaurant not found!", "Please select a valid restaurant!", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid input!", "Price must be a number!", Alert.AlertType.ERROR);
            }
        });
        backButton.setOnAction(e -> stage.setScene(scene));

        layout.getChildren().addAll(titleLabel, restaurantLabel, restaurantComboBox, itemNameLabel, itemNameField,
                itemPriceLabel, itemPriceField, submitButton, backButton);
        return new Scene(layout, 400, 600);
    }

    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("View Menu");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        Label chooseMenu = new Label("Choose the Restaurant");
        viewRestaurantComboBox = new ComboBox<>(FXCollections.observableArrayList(
                restoList.stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));
        Label menuLabel = new Label("Menu:");
        ListView<String> restaurantMenuListView = new ListView<>();
        Button backButton = new Button("Back");

        viewRestaurantComboBox.setOnAction(e -> {
            String selectedRestaurantName = viewRestaurantComboBox.getValue();
            Restaurant selectedRestaurant = findRestaurantByName(selectedRestaurantName);
            if (selectedRestaurant != null) {
                restaurantMenuListView.setItems(FXCollections.observableArrayList(
                        selectedRestaurant.getMenu().stream()
                                .map(menuItem -> menuItem.getNamaMakanan() + " - Rp" + (int)menuItem.getHarga() + ".0")
                                .collect(Collectors.toList())
                ));
            }
        });

        backButton.setOnAction(e -> stage.setScene(scene));

        layout.getChildren().addAll(titleLabel, chooseMenu, viewRestaurantComboBox, menuLabel, restaurantMenuListView, backButton);
        return new Scene(layout, 400, 600);
    }

    public Scene getScene() {
        // Supaya scene Admin bisa ditampilkan
        return scene;
    }

    private void handleTambahRestoran(String nama) {
        String validName = DepeFood.getValidRestaurantName(nama);
        if (validName != null) {
            Restaurant newRestaurant = new Restaurant(validName);
            restoList.add(newRestaurant);
            showAlert("Information", "Success!", "Restaurant successfully registered!", Alert.AlertType.INFORMATION);
            refresh();
            stage.setScene(scene);
        } else {
            showAlert("Error", "Name not Valid", "Please enter a valid name for the restaurant.", Alert.AlertType.ERROR);
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        if (restaurant != null && !itemName.isEmpty() && price > 0) {
            restaurant.addMenu(new Menu(itemName, price));
            showAlert("Success", null, "Menu item added successfully.", Alert.AlertType.INFORMATION);
            refresh();
            stage.setScene(scene);
        } else {
            showAlert("Error", null, "Invalid input for menu item.", Alert.AlertType.ERROR);
        }
    }

    @Override
    protected void refresh() {
        super.refresh();
        restaurantComboBox.setItems(FXCollections.observableArrayList(
                restoList.stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));
        if (viewRestaurantComboBox != null) {
            viewRestaurantComboBox.setItems(FXCollections.observableArrayList(
                    restoList.stream().map(Restaurant::getNama).collect(Collectors.toList())
            ));
        }
    }

    private Restaurant findRestaurantByName(String name) {
        for (Restaurant restaurant : restoList) {
            if (restaurant.getNama().equals(name)) {
                return restaurant;
            }
        }
        return null;
    }
}
