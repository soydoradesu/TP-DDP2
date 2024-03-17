package assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static assignments.assignment1.OrderGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args){
        boolean programRunning = true;
        while(programRunning){
            printHeader();
            startMenu();
            initUser();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();
                
                // TODO: Validasi input login
                User userLoggedIn = getUser(nama, noTelp);

                if(userLoggedIn != null){
                    boolean isLoggedIn = true;

                    System.out.format("\nSelamat datang, %s!", userLoggedIn.getNama());
                    if(userLoggedIn.getRole() == "Customer"){
                        while (isLoggedIn){
                            menuCustomer();
                            int commandCust = input.nextInt();
                            input.nextLine();
                            
                            switch(commandCust){
                                case 1 -> handleBuatPesanan();
                                case 2 -> handleCetakBill();
                                case 3 -> handleLihatMenu();
                                case 4 -> handleUpdateStatusPesanan();
                                case 5 -> isLoggedIn = false;
                                default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                            }
                        }
                    }else{
                        while (isLoggedIn){
                            menuAdmin();
                            int commandAdmin = input.nextInt();
                            input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }
                else {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                } 
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        // TODO: Implementasi method untuk mendapat user dari userList
        User temp = null;
        for(User user : userList){
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                temp = user;
            }
        }
        return temp;
    }

    public static void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("-".repeat(15) + "Buat Pesanan" + "-".repeat(15));

    }

    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    public static void handleTambahRestoran() {
    if (restoList == null) {
        restoList = new ArrayList<>();
    }

    System.out.println("-".repeat(15) + "Tambah Restoran" + "-".repeat(15));

    while (true) {
        System.out.print("Nama: ");
        String namaResto = input.nextLine();

        if (namaResto.length() < 4) {
            System.out.println("Nama restoran tidak valid!\n");
            continue;
        }

        boolean control = true;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equals(namaResto)) {
                System.out.format("Restoran dengan nama %s sudah terdaftar. Mohon masukkan nama yang berbeda!\n\n", namaResto);
                control = false;
                break;
            }

        }

        if(!control){
            continue;
        }

        // If the restaurant name is valid and not already in the list, break the loop
        Restaurant newRestaurant = new Restaurant(namaResto);
        restoList.add(newRestaurant);

        System.out.print("Jumlah Makanan: ");
        int jumlah = input.nextInt();
        input.nextLine(); // Consume newline

        boolean control3 = true;
        for (int i = 0; i < jumlah; i++) {
            String menuSubString = input.nextLine();
            String[] menuSplit = menuSubString.split(" ");
            boolean control2 = true;
            for (char c : menuSplit[menuSplit.length - 1].toCharArray()) {
                if (!Character.isDigit(c)) {
                    System.out.print("Harga menu harus bilangan bulat!\n\n");
                    control2 = false;
                    break;
                }
            }

            if(!control2){
                control3 = false;
                break;
            }
            
            String harga = menuSplit[menuSplit.length - 1];
            String namaMenu = String.join(" ", Arrays.copyOf(menuSplit, menuSplit.length - 1));
            
            newRestaurant.getMenu().add(new Menu(namaMenu, Double.parseDouble(harga)));
            }

        if(!control3){
            continue;
        }

        System.out.format("Restaurant %s Berhasil terdaftar.\n", newRestaurant.getNama());
        break;
        }
    }

    public static void handleHapusRestoran(){
        System.out.println("-".repeat(15) + "Hapus Restoran" + "-".repeat(15));

        if (restoList.isEmpty()) {
            System.out.println("Tidak ada restoran yang terdaftar!");
            return;
        }
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            boolean restoFound = false;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaResto)) {
                    restoList.remove(resto);
                    restoFound = true;
                    System.out.print("Restoran berhasil dihapus.");
                    break;
                }
            }
            if (!restoFound) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            if (restoFound){
                break;
            }
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
