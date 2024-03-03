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
        System.out.println("-----------------------------------------------");
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

        return beforeChecksum + oCheckSum1 + oCheckSum2;
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
        while (true){
            showMenu();
            System.out.print("Pilihan menu: ");
            int menu = input.nextInt();
            input.nextLine();
            switch (menu){
                case 1:
                    String namaRestoran;
                    do {
                        System.out.print("Nama restoran: ");
                        namaRestoran = input.nextLine();
                        if (namaRestoran.length() < 4){
                            System.out.println("Nama Restoran tidak valid!");
                        }
                    } while (namaRestoran.length() < 4);
                    
                    String tanggalOrder;
                    do {
                        System.out.print("Tanggal Pemesanan: ");
                        tanggalOrder = input.nextLine();
                        if (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}")){
                            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                        }
                    } while (!tanggalOrder.matches("\\d{2}/\\d{2}/\\d{4}"));
                    
                    String noTelepon;
                    do {
                        System.out.print("No. Telpon: ");
                        noTelepon = input.next();
                        if (!noTelepon.matches("\\d*[1-9]\\d*")){
                            System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                        } 
                    } while (!noTelepon.matches("\\d*[1-9]\\d*"));
                    
                    String orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                    System.out.println("Order ID: "+ orderID +" diterima!");
    
                    System.out.println("-----------------------------------------------");
                    break;
    
                case 2:
                    // System.out.print("Order ID: ");
                    // idOrder =  input.nextLine()
                case 3:
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    return;
            }
        }  
    }    
}