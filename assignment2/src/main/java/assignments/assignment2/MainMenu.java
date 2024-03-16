package assignments.assignment2;

import java.util.ArrayList;
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
                }else {
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

    public static void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        restoList = new ArrayList<Restaurant>();
        String namaResto;
        System.out.println("-".repeat(15)+"Tambah Restoran"+"-".repeat(15));
        do {
        System.out.print("Nama: ");
        namaResto = input.nextLine();
        if(namaResto.length()<4){
            System.out.print("Nama restoran tidak valid!");
            continue;
            }
        boolean restoranExists = false;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equals(namaResto)) {
                restoranExists = true;
                break;
                }
             }
        if (restoranExists) {
            System.out.format("\nRestoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!", namaResto);
            continue;
            } 
        } while(namaResto.length()<4);

        restoList.add(new Restaurant(namaResto));
        Restaurant newRestaurant = new Restaurant(namaResto);

        System.out.print("Jumlah Makanan: ");
        int jumlah = input.nextInt();
        input.nextLine();
        
        for(int i = 0; i < jumlah; i++){
            String menuSubString = input.nextLine();
            String[] menuSplit = menuSubString.split(" ");
            for (char c : menuSplit[menuSplit.length - 1].toCharArray()) {
                if (!Character.isDigit(c)) {
                    System.out.print("Harga makanan tidak valid!");
                    break;
                    }
                }
            String harga = menuSplit[menuSplit.length - 1];
            String namaMenu = "";
            for (int j = 0; j < menuSplit.length; j++) {
                namaMenu += menuSplit[j];
                namaMenu += " ";
                }
            namaMenu = namaMenu.substring(0, namaMenu.length() - 1);
            newRestaurant.getMenu().add(new Menu(namaMenu, Double.parseDouble(harga)));
        }
        System.out.format("\nRestaurant %s Berhasil terdaftar.", namaResto);   
    }

    public static void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
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
