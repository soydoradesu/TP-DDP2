package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    // Create the BillPrinterForm scene
    private Scene createBillPrinterForm() {
        // Create the BillPrinterForm layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
    
        Label titleLabel = new Label("Print Bill");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        Label orderIdLabel = new Label("Order ID:");
        TextField orderIdField = new TextField();
    
        // Set event handler
        Button printButton = new Button("Print Bill");
        printButton.setOnAction(e -> printBill(orderIdField.getText()));
    
        gridPane.add(orderIdLabel, 0, 0);
        gridPane.add(orderIdField, 1, 0);
        gridPane.add(printButton, 1, 1);
    
        layout.getChildren().addAll(titleLabel, gridPane);
    
        return new Scene(layout, 400, 200);
    }

    // Prints bill
    public void printBill(String orderId) {
        Order order = DepeFood.getOrderOrNull(orderId);
        if (order != null) {
            String billOutput = outputBillPesanan(order, user);
            showBillDialog(billOutput);
        } else {
            showAlert("Error", "Invalid Order ID", "Please enter a valid order ID.", Alert.AlertType.ERROR);
        }
    }

    // Shows bill dialog
    private void showBillDialog(String billOutput) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bill");
        alert.setHeaderText(null);
        alert.setContentText(billOutput);
    
        TextArea textArea = new TextArea(billOutput);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
    
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    // For the bill output message
    public static String outputBillPesanan(Order order, User user) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                         "Order ID: %s%n" +
                         "Tanggal Pemesanan: %s%n" +
                         "Lokasi Pengiriman: %s%n" +
                         "Status Pengiriman: %s%n"+
                         "Pesanan:%n%s%n"+
                         "Biaya Ongkos Kirim: Rp %s%n"+
                         "Total Biaya: Rp %s%n",
                         order.getOrderId(),
                         order.getTanggal(),
                         user.getLokasi(),
                         !order.getOrderFinished()? "Not Finished":"Finished",
                         getMenuPesananOutput(order),
                         decimalFormat.format(order.getOngkir()),
                         decimalFormat.format(order.getTotalHarga())
                         );
    }

    // Gets the menu pesanan
    public static String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    // Alert method
    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Getter method for scene
    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
