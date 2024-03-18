package assignments.assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    // Main method 
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

                    System.out.format("Selamat datang, %s!\n", userLoggedIn.getNama());
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

     // Method to get user object based on name and phone number
    public static User getUser(String nama, String nomorTelepon){
        User temp = null;
        for(User user : userList){
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                temp = user;
            }
        }
        return temp;
    }

    // Method to handle order creation by customer
    public static void handleBuatPesanan(User userLoggedIn) {
        System.out.println("-".repeat(15) + "Buat Pesanan" + "-".repeat(15));
    
        while (true) {
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();
            
            // Search the selected restaurant
            Restaurant selectedRestaurant = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equals(namaResto)) {
                    selectedRestaurant = resto;
                    break;
                }
            }
            
            // Check if restaurant is found
            if (selectedRestaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            
            // Get restaurant menu
            ArrayList<Menu> menuResto = selectedRestaurant.getMenu();
    
            // Get order date
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalOrder = input.nextLine();
            if (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Tanggal Pemesanan harus dalam format (DD/MM/YYYY) !\n");
                continue; 
            }
    
            System.out.print("Jumlah Pesanan: ");
            int jumlah = input.nextInt();
            input.nextLine(); // Consume newline
    
            // Generate order ID, calculate delivery cost, and initialize order
            String orderID = generateOrderID(selectedRestaurant.getNama(), tanggalOrder, userLoggedIn.getNomorTelepon());
            int ongkir = deliveryCost(userLoggedIn.getLokasi());
            ArrayList<Menu> items = new ArrayList<>();
            ArrayList<String> temp = new ArrayList<>();
    
            Order order = new Order(orderID, tanggalOrder, ongkir, selectedRestaurant, items);
            
            // Get user's order items
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
                        order.getItems().add(menu); // Add the menu item to the order
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
            System.out.format("Pesanan dengan ID %s diterima!\n", orderID);
            userLoggedIn.getOrderHistory().add(order); // Add the order to the user's order history
            break; // Break the loop if the order is successful
        }
    }    

    // Method to handle printing of bill
    public static void handleCetakBill(){
        System.out.println("-".repeat(15) + "Cetak Bill" + "-".repeat(15));
        while(true){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

             // Search for the order in the user list
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

    // Method to handle viewing the menu
    public static void handleLihatMenu(){
        System.out.println("-".repeat(15) + "Lihat Menu" + "-".repeat(15));
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            // Search for the restaurant in the restaurant list
            boolean restoFound = false;
            for (Restaurant resto : restoList){
                if (resto.getNama().equals(namaResto)){
                    restoFound = true;
                    System.out.println("Menu:");
                    ArrayList<Menu> sortedMenu = sortMenu(resto.getMenu()); // Sort the menu items
                    int cnt = 1;
                    for (Menu menu : sortedMenu){
                        System.out.println(cnt + ". " + menu.getNamaMakanan() + " " + menu.getHarga());
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

    // Method to handle updating the order status
    public static void handleUpdateStatusPesanan(){
        System.out.println("-".repeat(15) + "Update Status Pesanan" + "-".repeat(15));
        while(true){
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            // Search for the order in the user list
            boolean found = false;
            for(User user : userList){
                for(Order order : user.getOrderHistory()){
                    if(order.getOrderID().equals(orderID)){
                        // Get status input from the user and update the order status
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
                        System.out.format("Status pesanan dengan ID %s berhasil diupdate!\n", orderID );
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

    // Method to handle adding a new restaurant
    public static void handleTambahRestoran() {
    // Adding a new restaurant process
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

        // Check if the restaurant name already exists
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

        // Get menu items and their prices from the user
        System.out.print("Jumlah Makanan: ");
        int jumlah = input.nextInt();
        input.nextLine(); 

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
            
            // Create a new menu object and add it to the restaurant's menu
            String harga = menuSplit[menuSplit.length - 1];
            String namaMenu = String.join(" ", Arrays.copyOf(menuSplit, menuSplit.length - 1));
        
            newRestaurant.getMenu().add(new Menu(namaMenu, Double.parseDouble(harga)));
        }

        if (canceled) {
            continue;
        }

        // Add the new restaurant to the list
        restoList.add(newRestaurant);

        System.out.format("Restaurant %s Berhasil terdaftar.\n", newRestaurant.getNama());
        break;
        }
    }

    // Method to handle removing a restaurant
    public static void handleHapusRestoran(){
        System.out.println("-".repeat(15) + "Hapus Restoran" + "-".repeat(15));

        if (restoList.isEmpty()) {
            System.out.println("Tidak ada restoran yang terdaftar!");
            return;
        }
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            // Search for the restaurant in the restaurant list
            boolean restoFound = false;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(namaResto)) {
                    restoList.remove(resto);
                    restoFound = true;
                    System.out.print("Restoran berhasil dihapus.\n");
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

    // Method to sort menu items by price and then alphabetically
    public static ArrayList<Menu> sortMenu(ArrayList<Menu> menuList) {
        // Bubble sort algorithm
        for (int i = 0; i < menuList.size() - 1; i++) {
            for (int j = 0; j < menuList.size() - i - 1; j++) {
                Menu menu1 = menuList.get(j);
                Menu menu2 = menuList.get(j + 1);

                // Compare by price
                if (menu1.getHargaBefore() > menu2.getHargaBefore()) {
                    // Swap if menu1's price is greater than menu2's price
                    Menu temp = menuList.get(j);
                    menuList.set(j, menuList.get(j + 1));
                    menuList.set(j + 1, temp);
                } else if (menu1.getHargaBefore() == menu2.getHargaBefore()) {
                    // If prices are equal, compare alphabetically by name
                    String name1 = menu1.getNamaMakanan();
                    String name2 = menu2.getNamaMakanan();

                    // Compare alphabetically character by character
                    int minLength = Math.min(name1.length(), name2.length());
                    int k = 0;
                    while (k < minLength) {
                        char c1 = name1.charAt(k);
                        char c2 = name2.charAt(k);
                        if (c1 != c2) {
                            // Swap if characters are not equal
                            if (c1 > c2) {
                                Menu temp = menuList.get(j);
                                menuList.set(j, menuList.get(j + 1));
                                menuList.set(j + 1, temp);
                            }
                            break; // Break the loop if characters are not equal
                        }
                        k++;
                    }
                    // If all characters are equal until the end of the shortest string
                    // Sort based on the length of the strings
                    if (k == minLength && name1.length() > name2.length()) {
                        Menu temp = menuList.get(j);
                        menuList.set(j, menuList.get(j + 1));
                        menuList.set(j + 1, temp);
                    }
                }
            }
        }
        return menuList;
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
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("--------------------------------------------");
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
