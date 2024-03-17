package assignments.assignment2;

public class Menu {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){
        // TODO: buat constructor untuk class ini
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getNamaMakanan(){
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public double getHargaBefore(){
        return harga;
    }

    public String getHarga() {
        if (harga == (int) harga) {
            return String.valueOf((int) harga);
        } else {
            return String.valueOf(harga);
        }
    }

    public void setHarga(double harga){
        this.harga = harga;
    }

}
