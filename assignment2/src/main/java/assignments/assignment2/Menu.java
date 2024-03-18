package assignments.assignment2;

public class Menu {
    // Attributes needed for this class
    private String namaMakanan; // Name of the food item
    private double harga; // Price of the food item

    // Constructor for the Menu class
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan; // Initialize namaMakanan attribute
        this.harga = harga; // Initialize harga attribute
    }

    // Getter method for retrieving the name of the food item
    public String getNamaMakanan(){
        return namaMakanan;
    }

    // Setter method for setting the name of the food item
    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    // Getter method for retrieving the price of the food item (before formatting)
    public double getHargaBefore(){
        return harga;
    }

    // Getter method for retrieving the formatted price of the food item
    public String getHarga() {
        if (harga == (int) harga) { // Check if the price is a whole number
            return String.valueOf((int) harga); // Return the price as an integer
        } else {
            return String.valueOf(harga); // Return the price as a double
        }
    }

    // Setter method for setting the price of the food item
    public void setHarga(double harga){
        this.harga = harga;
    }
}