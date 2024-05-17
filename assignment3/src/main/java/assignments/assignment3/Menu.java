package assignments.assignment3;

public class Menu {

    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga) {
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    public double getHarga() {
        return harga;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    @Override
    public String toString() {
        return namaMakanan + " - Rp " + harga;
    }
}
