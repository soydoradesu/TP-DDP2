package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment1.OrderGenerator;
import assignments.assignment3.Order;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private MainApp mainApp;
    private User user;
    private ComboBox<String> restaurantComboBox;
    private Label balanceLabel;

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome, " + this.user.getNama() + "!");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        Button addOrderButton = new Button("Add Order");
        Button printBillButton = new Button("Print Bill");
        Button payBillButton = new Button("Pay Bill");
        Button cekSaldoButton = new Button("Check Balance");
        Button logOutButton = new Button("Log Out");

        addOrderButton.setOnAction(e -> stage.setScene(addOrderScene));
        printBillButton.setOnAction(e -> stage.setScene(printBillScene));
        payBillButton.setOnAction(e -> stage.setScene(payBillScene));
        cekSaldoButton.setOnAction(e -> stage.setScene(createCekSaldoScene()));
        logOutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(titleLabel, addOrderButton, printBillButton, payBillButton, cekSaldoButton, logOutButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createTambahPesananForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Add Order");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Restaurant ComboBox
        Label restaurantLabel = new Label("Select Restaurant:");
        restaurantComboBox = new ComboBox<>(FXCollections.observableArrayList(
                DepeFood.getRestoList().stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));

        TextField dateField = new TextField();
        dateField.setPromptText("Enter Date (DD/MM/YYYY)");

        // Menu Items ListView
        ListView<Menu> menuItemsListView = new ListView<>();
        ObservableList<Menu> selectedMenuItems = FXCollections.observableArrayList();

        restaurantComboBox.setOnAction(e -> {
            String selectedRestaurantName = restaurantComboBox.getValue();
            Restaurant selectedRestaurant = DepeFood.findRestaurant(selectedRestaurantName);
            if (selectedRestaurant != null) {
                menuItemsListView.setItems(FXCollections.observableArrayList(selectedRestaurant.getMenu()));
            }
        });

        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button submitButton = new Button("Submit Order");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String restaurantName = restaurantComboBox.getValue();
            String orderDate = dateField.getText();
            selectedMenuItems.clear(); // Clear the previous selection
            selectedMenuItems.addAll(menuItemsListView.getSelectionModel().getSelectedItems());
            handleBuatPesanan(restaurantName, orderDate, new ArrayList<>(selectedMenuItems));
        });

        backButton.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(titleLabel, restaurantLabel, restaurantComboBox, dateField, menuItemsListView, submitButton, backButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBillPrinter() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
    
        Label titleLabel = new Label("Print Bill");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
    
        TextField orderIDField = new TextField();
        orderIDField.setPromptText("Order ID");
    
        Button submitButton = new Button("Print Bill");
        Button backButton = new Button("Back");
    
        submitButton.setOnAction(e -> billPrinter.printBill(orderIDField.getText()));
        backButton.setOnAction(e -> stage.setScene(scene));
    
        menuLayout.getChildren().addAll(titleLabel, orderIDField, submitButton, backButton);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBayarBillForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Pay Bill");

        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Order ID");

        Label paymentMethodLabel = new Label("Payment Method");
        ComboBox<String> paymentMethodComboBox = new ComboBox<>(FXCollections.observableArrayList("Credit Card", "Debit"));

        Button submitButton = new Button("Pay Bill");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String orderId = orderIdField.getText();
            int paymentMethod = paymentMethodComboBox.getSelectionModel().getSelectedIndex();
            handleBayarBill(orderId, paymentMethod);
        });
        backButton.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(titleLabel, orderIdField, paymentMethodLabel, paymentMethodComboBox, submitButton, backButton);

        return new Scene(menuLayout, 400, 600);
    }

    private Scene createCekSaldoScene() {
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
    
        Label titleLabel = new Label("Check Balance");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
    
        Label nameLabel = new Label("Name: " + user.getNama());
        Label balanceLabel = new Label(getUserBalance());
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(scene));
    
        menuLayout.getChildren().addAll(titleLabel, nameLabel, balanceLabel, backButton);
        return new Scene(menuLayout, 400, 600);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, ArrayList<Menu> menuItems) {
        try {
            Restaurant restaurant = DepeFood.findRestaurant(namaRestoran);
            if (restaurant == null) {
                showAlert("Error", "Restaurant not found!", "Please enter a valid restaurant name.", AlertType.ERROR);
                return;
            }
            if (tanggalPemesanan.isEmpty() || menuItems.isEmpty()) {
                showAlert("Error", "Invalid Order!", "Please fill all the required information.", AlertType.ERROR);
                return;
            }
            if (!OrderGenerator.validateDate(tanggalPemesanan)) {
                showAlert("Error", "Invalid Date!", "Please fill the date on such format (DD/MM/YYYY)", AlertType.ERROR);
                return;
            }

            Order order = new Order(OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon()),
                    tanggalPemesanan, OrderGenerator.calculateDeliveryCost(user.getLokasi()), restaurant, menuItems);
            user.addOrderHistory(order);
            showAlert("Success", "Order Placed!", "Order " + OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon()) +
                    " is successfully placed", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Exception", "An error occurred: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        try {
            Order order = DepeFood.getOrderOrNull(orderID);
            if (order == null) {
                showAlert("Error", "Payment Failed!", "Wrong order ID!", AlertType.ERROR);
                return;
            }
    
            DepeFoodPaymentSystem paymentSystem;
            long totalPrice;
            long amountToPay;
    
            switch (pilihanPembayaran) {
                case 0:
                    user.setPaymentSystem("Credit Card");
                    paymentSystem = user.getPaymentSystem();
                    totalPrice = (long) order.getTotalHarga();
                    amountToPay = paymentSystem.processPayment(user.getSaldo(), totalPrice);
                    showAlert("Success", "Payment Success!", "You have successfully paid Rp " + (int) totalPrice +
                            " with a transaction fee Rp " + (amountToPay - totalPrice), AlertType.INFORMATION);
                    break;
                case 1:
                    user.setPaymentSystem("Debit");
                    paymentSystem = user.getPaymentSystem();
                    totalPrice = (long) order.getTotalHarga();
                    amountToPay = paymentSystem.processPayment(user.getSaldo(), totalPrice);
                    user.setSaldo(user.getSaldo() - amountToPay);
                    updateBalanceLabel(); // Update the balance label
                    showAlert("Success", "Payment Success!", "You have successfully paid Rp " + (int) totalPrice +
                            "\n" + getUserBalance(), AlertType.INFORMATION);
                    break;
                default:
                    showAlert("Error", "Not a valid payment method", "Please choose the available payment method!", AlertType.ERROR);
                    return;
            }
            order.setOrderFinished(true);
        } catch (Exception e) {
            showAlert("Error", "Exception", "An error occurred: " + e.getMessage(), AlertType.ERROR);
        } finally {
            Order order = DepeFood.getOrderOrNull(orderID);
            if (order != null) {
                order.setOrderFinished(true);
            }
        }
    }

    private void updateBalanceLabel() {
        if (balanceLabel != null) {
            balanceLabel.setText(getUserBalance());
    }
}

    private String getUserBalance() {
        return "Balance: " + user.getSaldo();
    }

    public Scene getScene() {
        // Supaya scene Admin bisa ditampilkan
        return scene;
    }

    @Override
    protected void refresh() {
        super.refresh();
        restaurantComboBox.setItems(FXCollections.observableArrayList(
                DepeFood.getRestoList().stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));
    }

}
