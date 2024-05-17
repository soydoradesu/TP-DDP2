package assignments.assignment3;

import java.util.ArrayList;

public class Order {

    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant restaurant;
    private boolean orderFinished;
    private ArrayList<Menu> items;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, ArrayList<Menu> items) {
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.restaurant = resto;
        this.orderFinished = false;
        this.items = items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean getOrderFinished() {
        return this.orderFinished;
    }

    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getOngkir() {
        return ongkir;
    }

    public ArrayList<Menu> getItems() {
        return items;
    }

    // public ArrayList<Menu> getSortedMenu() {
    //     ArrayList<Menu> menuArr = new ArrayList<Menu>();
    //     for (Menu menu : getItems()) {
    //         menuArr.add(menu);
    //     }
    //     int n = menuArr.size();
    //     for (int i = 0; i < n - 1; i++) {
    //         for (int j = 0; j < n - i - 1; j++) {
    //             if (menuArr.inde.getHarga() > menuArr[j + 1].getHarga()) {

    //                 Menu temp = menuArr[j];
    //                 menuArr[j] = menuArr[j + 1];
    //                 menuArr[j + 1] = temp;
    //             }
    //         }
    //     }
    //     return menuArr;
    // }

    public double getTotalHarga() {
        double sum = 0;
        for (Menu menu : getItems()) {
            sum += menu.getHarga();
        }
        return sum += getOngkir();
    }
}
