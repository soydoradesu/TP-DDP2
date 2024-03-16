package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    private String nama;
    private static ArrayList<Menu> menu;

    public Restaurant(String nama){
        this.nama = nama;
        this.menu = new ArrayList<>();
    }

    // Getter and setter methods
    public String getNama(){
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<Menu> getMenu(){
        return menu;
    }

    // Add method to add menu items
    public void addMenuItem(Menu menuItem) {
        menu.add(menuItem);
    }

    // Add method to remove menu items
    public void removeMenuItem(Menu menuItem) {
        menu.remove(menuItem);
    }
}