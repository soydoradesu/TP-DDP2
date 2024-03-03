package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
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
        System.out.println("---------------------------------");
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String restoranID = namaRestoran.substring(0, 4).toUpperCase();
    
        String date = tanggalOrder.replaceAll("/", "");
    
        int sum = 0;
        for (int i = 0; i < noTelepon.length(); i++) {
            char charNoTelepon = noTelepon.charAt(i);
            sum += charNoTelepon - '0';
        }
        String phoneSuffix = String.format("%02d", sum % 100);
    
        String beforeChecksum = restoranID + date + phoneSuffix;
    
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < beforeChecksum.length(); i++) {
            int value = (int) beforeChecksum.charAt(i);
            if (i % 2 == 0) {
                sum1 += value;
            } else {
                sum2 += value;
            }
        }
    
        char checksum1;
        char checksum2;
    
        if (sum1 % 36 < 10) {
            checksum1 = (char) ('0' + sum1 % 36);
        } else {
            checksum1 = (char) ('A' + (sum1 % 36 - 10));
        }
    
        if (sum2 % 36 < 10) {
            checksum2 = (char) ('0' + sum2 % 36);
        } else {
            checksum2 = (char) ('A' + (sum2 % 36 - 10));
        }

        return beforeChecksum + checksum1 + checksum2;
    }
    
    public static String generateBill(String OrderID, String lokasi){
        int deliveryCost;
        switch (lokasi){
            case "P":
                deliveryCost = 10000;
                break;
            case "U":
                deliveryCost = 20000;
                break;
            case "T":
                deliveryCost = 30000;
                break;
            case "S":
                deliveryCost = 40000;
                break;
            case "B":
                deliveryCost = 50000;
                break;
            default :
                return  "Harap masukkan lokasi pengiriman yang ada pada jangkauan!";
        }
        String bill = "Bill:\n";
        bill += "Order ID: "+ OrderID +"\n";
        String theDate = OrderID.substring(4,6) + "/" +OrderID.substring(6,8) +"/" + OrderID.substring(8,12);
        bill += "Tanggal Pemesanan: "+ theDate +"\n";
        bill += "Lokasi Pengiriman: "+ lokasi +"\n";
        bill += "Biaya Ongkos Kirim: "+ deliveryCost +"\n";

        return bill;
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        showMenu();
        System.out.println("Pilihan menu: ");
        int menu = input.nextInt();
        input.nextLine();
        switch (menu){
            case 1:
            System.out.println("Nama restoran: ");
            String namaRestoran = input.nextLine();
            System.out.println("Tanggal Pemesanan: ");
            String tanggalOrder = input.nextLine();
            System.out.println("No. Telpon: ");
            String noTelepon = input.nextLine();

        }
    }   
}