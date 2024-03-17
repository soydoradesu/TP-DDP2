package assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

                    System.out.format("Selamat datang, %s!", userLoggedIn.getNama());
                    if(userLoggedIn.getRole() == "Customer"){
                        while (isLoggedIn){
                            menuCustomer();
                            int commandCust = input.nextInt();
                            input.nextLine();
                            
                            switch(commandCust){
                                case 1 -> handleBuatPesanan(userLoggedIn);
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

    public static void handleBuatPesanan(User userLoggedIn) {
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("-".repeat(15) + "Buat Pesanan" + "-".repeat(15));
    
        while (true) {
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();
    
            Restaurant selectedRestaurant = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equals(namaResto)) {
                    selectedRestaurant = resto;
                    break;
                }
            }
    
            if (selectedRestaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
    
            ArrayList<Menu> menuResto = selectedRestaurant.getMenu();
    
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalOrder = input.nextLine();
            if (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Tanggal Pemesanan harus dalam format (DD/MM/YYYY) !\n");
                continue; // Break the loop if input is invalid
            }
    
            System.out.print("Jumlah Pesanan: ");
            int jumlah = input.nextInt();
            input.nextLine(); // Consume newline
    
            String orderID = generateOrderID(selectedRestaurant.getNama(), tanggalOrder, userLoggedIn.getNomorTelepon());
            int ongkir = deliveryCost(userLoggedIn.getLokasi());
            ArrayList<Menu> items = new ArrayList<>();
            ArrayList<String> temp = new ArrayList<>();
    
            Order order = new Order(orderID, tanggalOrder, ongkir, selectedRestaurant, items);
            
            System.out.println("Order:");
            boolean canceled = false;
            for (int i = 0; i < jumlah; i++) {
                String menunya = input.nextLine();
                temp.add(menunya);
            }
            for (String menuInput : temp) {
                boolean found = false;
                for (Menu menu : menuResto) {
                    if (menu.getNamaMakanan().equals(menuInput)) {
                        order.getItems().add(menu);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                    canceled = true;
                    break;
                }
            }
            if (canceled) {
                continue;
            }
            System.out.format("Pesanan dengan ID %s diterima!", orderID);
            userLoggedIn.getOrderHistory().add(order);
            break; // Break the loop if the order is successful
        }
    }    

    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("-".repeat(15) + "Cetak Bill" + "-".repeat(15));
        while(true){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            boolean found = false;
            for(User user : userList){
                for(Order order : user.getOrderHistory()){
                    if(order.getOrderID().equals(orderID)){
                        System.out.println("Bill:");
                        System.out.println("Order ID: " + order.getOrderID());
                        System.out.println("Tanggal Pemesanan: " + order.getTanggalPemesanan());
                        System.out.println("Restaurant: " + order.getRestaurant().getNama());
                        System.out.println("Lokasi Pengiriman: " + user.getLokasi());
                        System.out.println("Status Pengiriman: " + order.statusOrder());
                        System.out.println("Pesanan: ");
                        int total = 0;
                        for(Menu menu : order.getItems()){
                            System.out.println("- " + menu.getNamaMakanan() + " " + menu.getHarga());
                            total += menu.getHargaBefore();
                        }
                        System.out.println("Biaya Ongkos Kirim: Rp " + order.getBiayaOngkosKirim());
                        total += order.getBiayaOngkosKirim();
                        System.out.println("Total Biaya: Rp " + total);
                        found = true;
                        break;
                    }
                }
            }
            if(!found){
                System.out.println("Order ID tidak ditemukan!\n");
                continue;
            }
            if(found){
                break;
            }
        }
    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("-".repeat(15) + "Lihat Menu" + "-".repeat(15));
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            boolean restoFound = false;
            for (Restaurant resto : restoList){
                if (resto.getNama().equals(namaResto)){
                    restoFound = true;
                    System.out.println("Menu:");
                    int cnt = 1;
                    for (Menu menu : resto.getMenu()){
                        System.out.print(cnt + ". " + menu.getNamaMakanan() + " " + menu.getHarga());
                        cnt += 1;
                    }
                    break;
                }
            }
            if (!restoFound){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            if (restoFound){
                break;
            }
        }
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        System.out.println("-".repeat(15) + "Update Status Pesanan" + "-".repeat(15));
        while(true){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            boolean found = false;
            for(User user : userList){
                for(Order order : user.getOrderHistory()){
                    if(order.getOrderID().equals(orderID)){
                        System.out.print("Status: ");
                        String status = input.nextLine();
                        if (status.equals("Selesai")){
                            boolean boolStatus = true;
                            order.setOrderFinished(boolStatus);
                        }
                        else if (status.equals("Not Finished")){
                            boolean boolStatus = false;
                            order.setOrderFinished(boolStatus);
                        }
                        System.out.format("Status pesanan dengan ID %s berhasil diupdate!", orderID );
                        found = true;
                        break;
                    }
                }
            }
            if(!found){
                System.out.println("Order ID tidak ditemukan!\n");
                continue;
            }
            if(found){
                break;
            }
        }
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

        System.out.print("Jumlah Makanan: ");
        int jumlah = input.nextInt();
        input.nextLine(); // Consume newline

        ArrayList<String> tempMenu = new ArrayList<>();

        for (int i = 0; i < jumlah; i++) {
            String menuSubString = input.nextLine();
            tempMenu.add(menuSubString);
        }
        boolean canceled = false;
        for (String subString : tempMenu){
            boolean failed = false;
            String[] menuSplit = subString.split(" ");
            for (char c : menuSplit[menuSplit.length - 1].toCharArray()) {
                if (!Character.isDigit(c)) {
                    failed = true;
                    break;
                }
            }
            if (failed) {
                System.out.println("Harga menu harus bilangan bulat!\n");
                canceled = true;
                break;
            }
            
            String harga = menuSplit[menuSplit.length - 1];
            String namaMenu = String.join(" ", Arrays.copyOf(menuSplit, menuSplit.length - 1));
        
            newRestaurant.getMenu().add(new Menu(namaMenu, Double.parseDouble(harga)));
        }

        if (canceled) {
            continue;
        }

        restoList.add(newRestaurant);

        System.out.format("Restaurant %s Berhasil terdaftar.", newRestaurant.getNama());
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

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String restoranID = namaRestoran.substring(0, 4).toUpperCase(); // Makes the  first four characters uppercase
    
        String date = tanggalOrder.replaceAll("/", ""); // Replace the / with ""
    
        int sum = 0; // Counts the remainder of the sum of noTelepon
        for (int i = 0; i < noTelepon.length(); i++) {
            char charNoTelepon = noTelepon.charAt(i);
            sum += charNoTelepon - '0';
        }
        String phoneSuffix = String.format("%02d", sum % 100);
    
        String beforeChecksum = restoranID + date + phoneSuffix; // adds all of the before checksum order ID
    
        // Calculating checksums
        String code39 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int checkSum1 = 0;
        int checkSum2 = 0;
        int i = 0;

        while (i < beforeChecksum.length()) {
            if (i % 2 == 0) {
                checkSum1 += code39.indexOf(beforeChecksum.charAt(i));
            } else {
                checkSum2 += code39.indexOf(beforeChecksum.charAt(i));
            }
            i++;
        }

        char oCheckSum1 = code39.charAt(checkSum1 % 36);
        char oCheckSum2 = code39.charAt(checkSum2 % 36);

        return beforeChecksum + oCheckSum1 + oCheckSum2; // The final order ID
    }

    public static int deliveryCost(String lokasi){;
        switch (lokasi){
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 30000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return 0; // Default value if location is not recognized
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
