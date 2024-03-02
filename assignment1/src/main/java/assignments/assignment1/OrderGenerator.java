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
    }

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        if (namaRestoran.length() < 4) {
            return "Nama Restoran tidak valid!";
        }
    
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
    


    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        return "Bill";
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
    }

    
}
