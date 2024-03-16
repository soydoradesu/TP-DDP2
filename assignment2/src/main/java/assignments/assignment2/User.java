package main.java.assignments.assignment2;

public class User {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;
    private static ArrayList<Order> orderHistory;

    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini

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

    // Add method to add orders to order history
    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    
}
