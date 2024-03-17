package assignments.assignment2;

import java.util.ArrayList;

public class User {
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private ArrayList<Order> orderHistory;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.orderHistory = new ArrayList<>(); // Initialize orderHistory ArrayList

    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNomorTelepon(){
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon){
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getLokasi(){
        return lokasi;
    }

    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

}
