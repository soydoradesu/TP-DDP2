package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String nama; // Name of the restaurant
    private ArrayList<Menu> menu; // List of menu items offered by the restaurant

    // Constructor to initialize a Restaurant object with provided name
    public Restaurant(String nama){
        this.nama = nama; // Initialize nama
        this.menu = new ArrayList<>(); // Initialize menu ArrayList
    }

    // Getter method for nama
    public String getNama(){
        return nama;
    }

    // Setter method for nama
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter method for menu
    public ArrayList<Menu> getMenu(){
        return menu;
    }
}