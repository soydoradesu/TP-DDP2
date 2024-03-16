package main.java.assignments.assignment2;

public class Restaurant {
    private String nama;
    private ArrayList<Menu> menu; // Corrected ArrayList declaration

    public Restaurant(String nama){
        this.nama = nama;
    }

    // Getter and setter methods
    public String getNama(){
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
