// Nama     : Valentino Kim Fernando
// NPM      : 2306275771
// Kelas    : C
// Asdos    : EDB

package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    // Method to display the menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
        System.out.println("-----------------------------------------------");
    }

    // Method to generate Order ID
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
    
    // Method to generate bill
    public static String generateBill(String OrderID, String lokasi){
        // Initializing delivery cost
        String deliveryCost;
        lokasi = lokasi.toUpperCase();
        switch (lokasi){
            case "P":
                deliveryCost = "Rp 10.000";
                break;
            case "U":
                deliveryCost = "Rp 20.000";
                break;
            case "T":
                deliveryCost = "Rp 30.000";
                break;
            case "S":
                deliveryCost = "Rp 40.000";
                break;
            case "B":
                deliveryCost = "Rp 50.000";
                break;
            default:
                deliveryCost = ""; // Default value if location is not recognized
                break;
        }
        // Generating bill
        System.out.println("");
        String bill = "Bill:\n";
        bill += "Order ID: "+ OrderID +"\n";
        String theDate = OrderID.substring(4,6) + "/" +OrderID.substring(6,8) +"/" + OrderID.substring(8,12);
        bill += "Tanggal Pemesanan: "+ theDate +"\n";
        bill += "Lokasi Pengiriman: "+ lokasi +"\n";
        bill += "Biaya Ongkos Kirim: "+ deliveryCost +"\n";

        return bill;
    }

    // Method to validate Order ID
    public static boolean isValidOrderID(String orderID) {
        String code39 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int checkSum1 = 0;
        int checkSum2 = 0;
        int i = 0;
    
        // Calculating checksums
        while (i < orderID.length() - 2) {
            if (i % 2 == 0) {
                checkSum1 += code39.indexOf(orderID.charAt(i));
            } else {
                checkSum2 += code39.indexOf(orderID.charAt(i));
            }
            i++;
        }
    
        char oCheckSum1 = code39.charAt(checkSum1 % 36);
        char oCheckSum2 = code39.charAt(checkSum2 % 36);
    
        // Checking if checksums are valid
        return orderID.charAt(orderID.length() - 2) == oCheckSum1 && orderID.charAt(orderID.length() - 1) == oCheckSum2;
    }    

    // Main method
    public static void main(String[] args) {
        while (true){
            showMenu();
            System.out.print("Pilihan menu: ");
            int menu = input.nextInt();
            input.nextLine();
            switch (menu){
                case 1: // Menu 1
                    // Input namaRestoran
                    String namaRestoran;
                    do {
                        System.out.print("Nama restoran: ");
                        namaRestoran = input.nextLine();
                        if (namaRestoran.length() < 4){
                            System.out.println("Nama Restoran tidak valid!");
                        }
                    } while (namaRestoran.length() < 4);
                    
                    // Input tanggalOrder
                    String tanggalOrder;
                    do {
                        System.out.print("Tanggal Pemesanan: ");
                        tanggalOrder = input.nextLine();
                        if (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}")){
                            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                        }
                    } while (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}"));
                    
                    // Input noTelepon
                    String noTelepon;
                    do {
                        System.out.print("No. Telpon: ");
                        noTelepon = input.next();
                        if (!noTelepon.matches("\\d*[1-9]\\d*")){
                            System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                        } 
                    } while (!noTelepon.matches("\\d*[1-9]\\d*"));
                    
                    // Generate Order ID
                    String orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                    System.out.println("Order ID: "+ orderID +" diterima!");
    
                    System.out.println("-----------------------------------------------");
                    break;
    
                case 2: // Menu 2
                    // Generate Bill
                    do {
                        System.out.print("Order ID: ");
                        String idOrder =  input.nextLine();
                        if (idOrder.length() < 16){ // Error handling
                            System.out.println("Order ID minimal 16 karakter");
                            continue;
                        }

                        if (isValidOrderID(idOrder)) {
                            String lokasiPengiriman = "PUTSB"; // If not PUTSB then false
                            String lokasi;
                            do{
                                System.out.print("Lokasi Pengiriman: ");
                                lokasi = input.nextLine().toUpperCase();
                                if (!lokasiPengiriman.contains(lokasi))
                                    System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                            } while (!lokasiPengiriman.contains(lokasi));
                            String bill = generateBill(idOrder, lokasi);
                            System.out.println(bill);
                            break;
                        } else {
                            System.out.println("Silahkan masukkan Order ID yang valid!");
                        }
                    } while (true);
                    System.out.println("-----------------------------------------------");
                    break;

                case 3: // Menu 3 EXIT
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    return;
            }
        }  
    }    
} // sudah :) yay