package assignments.assignment2;

import java.util.ArrayList;

public class Order {
    private String orderID; // Unique identifier for the order
    private String tanggalPemesanan; // Date of the order
    private int biayaOngkosKirim; // Delivery cost of the order
    private Restaurant restaurant; // Restaurant from which the order is placed
    private ArrayList<Menu> items; // List of menu items ordered
    private boolean orderFinished; // Flag indicating whether the order is finished or not

    // Constructor to initialize an Order object with provided parameters
    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items){
        this.orderID = orderId; // Initialize orderID
        this.tanggalPemesanan = tanggal; // Initialize tanggalPemesanan
        this.biayaOngkosKirim = ongkir; // Initialize biayaOngkosKirim
        this.restaurant = resto; // Initialize restaurant
        this.items = items; // Initialize items
    }

    // Getter and Setter Methods

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(String tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }

    public void setBiayaOngkosKirim(int biayaOngkosKirim) {
        this.biayaOngkosKirim = biayaOngkosKirim;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<Menu> getItems() {
        return items;
    }

    public void setItems(ArrayList<Menu> items) {
        this.items = items;
    }

    public boolean getOrderFinished() {
        return orderFinished;
    }

    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    // Method to get the status of the order
    public String statusOrder(){
        if (orderFinished){
            return "Selesai"; // If order is finished, return "Selesai"
        } else {
            return "Not Finished"; // If order is not finished, return "Not Finished"
        }
    }
}