package assignments.assignment2;

import java.util.ArrayList;

public class User {
    private String nama; // User's name
    private String nomorTelepon; // User's phone number
    private String email; // User's email address
    private String lokasi; // User's location
    private String role; // User's role (Customer or Admin)
    private ArrayList<Order> orderHistory; // User's order history

    // Constructor to initialize a User object with provided details
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama; // Initialize nama
        this.nomorTelepon = nomorTelepon; // Initialize nomorTelepon
        this.email = email; // Initialize email
        this.lokasi = lokasi; // Initialize lokasi
        this.role = role; // Initialize role
        this.orderHistory = new ArrayList<>(); // Initialize orderHistory ArrayList
    }

    // Getter method for nama
    public String getNama(){
        return nama;
    }

    // Setter method for nama
    public void setNama(String nama){
        this.nama = nama;
    }

    // Getter method for nomorTelepon
    public String getNomorTelepon(){
        return nomorTelepon;
    }

    // Setter method for nomorTelepon
    public void setNomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }

    // Getter method for email
    public String getEmail(){
        return email;
    }

    // Setter method for email
    public void setEmail(String email){
        this.email = email;
    }

    // Getter method for lokasi
    public String getLokasi(){
        return lokasi;
    }

    // Setter method for lokasi
    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    // Getter method for role
    public String getRole(){
        return role;
    }

    // Setter method for role
    public void setRole(String role){
        this.role = role;
    }

    // Getter method for orderHistory
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

}