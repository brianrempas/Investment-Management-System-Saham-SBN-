import java.time.LocalDate;
import java.util.Scanner;

class SBN {
    private String nama;
    private double bunga;
    private int jangkaWaktu; // TAHUN
    private LocalDate tanggalJatuhTempo;
    private int kuotaNasional;

    public SBN(String nama, double bunga, int jangkaWaktu, LocalDate tanggalJatuhTempo, int kuotaNasional) {
        this.nama = nama;
        this.bunga = bunga;
        this.jangkaWaktu = jangkaWaktu;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.kuotaNasional = kuotaNasional;
    }

    // metode getter
    public String getNama() {
        return nama;
    }

    public double getBunga() {
        return bunga;
    }

    public int getJangkaWaktu() {
        return jangkaWaktu;
    }

    public LocalDate getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public int getKuotaNasional() {
        return kuotaNasional;
    }

    // kalkulator setiap bulan dpt brapa dri bunga
    public double hitungKuponBulanan(double nominalInvestasi) {
        return (bunga / 12) * 0.90 * nominalInvestasi / 100;
    }

    public void tampilanInfo() {
        System.out.println("Nama: " + nama);
        System.out.println("Bunga: " + bunga + "%");
        System.out.println("Jangka Waktu: " + jangkaWaktu + " tahun");
        System.out.println("Tanggal Jatuh Tempo: " + tanggalJatuhTempo);
        System.out.println("Kuota Nasional: " + kuotaNasional + " unit");
    }
}

class Stock {
    public static final String BBCA = "BBCA";
    public static final String BMRI = "BMRI";

    private String kode;
    private int harga;

    public Stock(String kode, int harga) {
        this.kode = kode;
        this.harga = harga;
    }

    // Getter n setter
    public String getKode() {
        return kode;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}

class InvestmentList {
    private Stock[] stocks = new Stock[10];
    private SBN[] sbns = new SBN[10];
    private int stockCount = 0;
    private int sbnCount = 0;

    // === STOCK METHODS ===
    public void addStock(Stock stock) {
        if (stockCount >= stocks.length) {
            System.out.println("Daftar saham penuh.");
            return;
        }
        stocks[stockCount++] = stock;
        System.out.println("Saham berhasil ditambahkan.");
    }

    public void listStocks() {
        if (stockCount == 0) {
            System.out.println("Tidak ada saham tersedia.");
            return;
        }
        for (int i = 0; i < stockCount; i++) {
            System.out.printf("%d. %s - Rp%d%n", i + 1, stocks[i].getKode(), stocks[i].getHarga());
        }
    }

    public void removeStock(int index) {
        if (index < 0 || index >= stockCount) {
            System.out.println("Indeks tidak valid.");
            return;
        }
        for (int i = index; i < stockCount - 1; i++) {
            stocks[i] = stocks[i + 1];
        }
        stocks[--stockCount] = null;
        System.out.println("Saham berhasil dihapus.");
    }

    public Stock getStockByIndex(int index) {
        if (index < 0 || index >= stockCount) {
            return null;
        }
        return stocks[index];
    }

    public int getStockCount() {
        return stockCount;
    }

    // === SBN METHODS ===
    public void addSBN(SBN sbn) {
        if (sbnCount >= sbns.length) {
            System.out.println("Daftar SBN penuh.");
            return;
        }
        sbns[sbnCount++] = sbn;
        System.out.println("SBN berhasil ditambahkan.");
    }

    public void listSBN() {
        if (sbnCount == 0) {
            System.out.println("Tidak ada SBN tersedia.");
            return;
        }
        for (int i = 0; i < sbnCount; i++) {
            System.out.println((i + 1) + ". ");
            sbns[i].tampilanInfo();  // Directly call displayInfo()
            System.out.println(); // Space between SBNs
        }
    }

    public SBN getSBNByIndex(int index) {
        if (index < 0 || index >= sbnCount) {
            return null;
        }
        return sbns[index];
    }

    public int getSBNCount() {
        return sbnCount;
    }
}

class CustomerInvestment {
    private Stock[] ownedStocks = new Stock[10];
    private SBN[] ownedSBNs = new SBN[10];
    private int[] ownedStockShares = new int[10];
    private int[] ownedSBNUnits = new int[10];
    private int stockCount = 0;
    private int sbnCount = 0;

    public void buyStock(Stock stock, int amount) {
        if (amount < stock.getHarga()) {
            System.out.println("Nominal terlalu kecil untuk membeli saham ini.");
            return;
        }

        int shares = amount / stock.getHarga();
        int index = findStockIndex(stock);

        if (index == -1) {
            if (stockCount >= ownedStocks.length) {
                System.out.println("Portofolio penuh, tidak bisa beli saham baru.");
                return;
            }
            ownedStocks[stockCount] = stock;
            ownedStockShares[stockCount] = shares;
            stockCount++;
        } else {
            ownedStockShares[index] += shares;
        }

        System.out.printf("Berhasil membeli %d lembar saham %s.%n", shares, stock.getKode());
    }

    public void sellStock(Stock stock, int sharesToSell) {
        int index = findStockIndex(stock);

        if (index == -1) {
            System.out.println("Kamu tidak memiliki saham ini.");
            return;
        }

        if (sharesToSell > ownedStockShares[index]) {
            System.out.println("Jumlah saham yang ingin dijual melebihi kepemilikan.");
            return;
        }

        ownedStockShares[index] -= sharesToSell;
        double revenue = sharesToSell * stock.getHarga();

        System.out.printf("Berhasil menjual %d lembar saham %s. Pendapatan: Rp%.2f%n", sharesToSell, stock.getKode(), revenue);

        // If no shares left, remove stock from portfolio
        if (ownedStockShares[index] == 0) {
            for (int i = index; i < stockCount - 1; i++) {
                ownedStocks[i] = ownedStocks[i + 1];
                ownedStockShares[i] = ownedStockShares[i + 1];
            }
            ownedStocks[--stockCount] = null;
            ownedStockShares[stockCount] = 0;
        }
    }

    public void buySBN(SBN sbn, int units) {
        if (units > sbn.getKuotaNasional()) {
            System.out.println("Jumlah unit SBN yang diminta melebihi kuota nasional.");
            return;
        }

        int index = findSBNIndex(sbn);

        if (index == -1) {
            if (sbnCount >= ownedSBNs.length) {
                System.out.println("Portofolio penuh, tidak bisa beli SBN baru.");
                return;
            }
            ownedSBNs[sbnCount] = sbn;
            ownedSBNUnits[sbnCount] = units;
            sbnCount++;
        } else {
            ownedSBNUnits[index] += units;
        }

        System.out.printf("Berhasil membeli %d unit SBN %s.%n", units, sbn.getNama());
    }

    public void sellSBN(SBN sbn, int unitsToSell) {
        int index = findSBNIndex(sbn);

        if (index == -1) {
            System.out.println("Kamu tidak memiliki SBN ini.");
            return;
        }

        if (unitsToSell > ownedSBNUnits[index]) {
            System.out.println("Jumlah unit SBN yang ingin dijual melebihi kepemilikan.");
            return;
        }

        ownedSBNUnits[index] -= unitsToSell;
        double revenue = unitsToSell * sbn.getBunga();  // Assuming the revenue is calculated using the interest as a return on each unit

        System.out.printf("Berhasil menjual %d unit SBN %s. Pendapatan: Rp%.2f%n", unitsToSell, sbn.getNama(), revenue);

        // Jika unit sbn sampai 0, hilangkan sbn dari portfolio
        if (ownedSBNUnits[index] == 0) {
            for (int i = index; i < sbnCount - 1; i++) {
                ownedSBNs[i] = ownedSBNs[i + 1];
                ownedSBNUnits[i] = ownedSBNUnits[i + 1];
            }
            ownedSBNs[--sbnCount] = null;
            ownedSBNUnits[sbnCount] = 0;
        }
    }

    public void showPortfolio() {
        if (stockCount == 0 && sbnCount == 0) {
            System.out.println("Belum ada investasi.");
            return;
        }

        double totalValue = 0;
        System.out.println("=== PORTOFOLIO SAHAM ===");

        for (int i = 0; i < stockCount; i++) {
            String kode = ownedStocks[i].getKode();
            int shares = ownedStockShares[i];
            int harga = ownedStocks[i].getHarga();
            double value = shares * harga;

            totalValue += value;

            System.out.printf("- %s: %d lembar x Rp%d = Rp%.2f%n", kode, shares, harga, value);
        }

        System.out.println("=== PORTOFOLIO SBN ===");

        for (int i = 0; i < sbnCount; i++) {
            String name = ownedSBNs[i].getNama();
            int units = ownedSBNUnits[i];
            double interest = ownedSBNs[i].getBunga();
            double value = units * interest;

            totalValue += value;

            System.out.printf("- %s: %d unit x Bunga %.2f%% = Rp%.2f%n", name, units, interest, value);
        }

        System.out.printf("Total nilai pasar: Rp%.2f%n", totalValue);
    }

    private int findStockIndex(Stock stock) {
        for (int i = 0; i < stockCount; i++) {
            if (ownedStocks[i].getKode().equals(stock.getKode())) {
                return i;
            }
        }
        return -1;
    }

    private int findSBNIndex(SBN sbn) {
        for (int i = 0; i < sbnCount; i++) {
            if (ownedSBNs[i].getNama().equals(sbn.getNama())) {
                return i;
            }
        }
        return -1;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        InvestmentList investmentList = new InvestmentList();
        CustomerInvestment investment = new CustomerInvestment();

        // Hardcoded login
        final String ADMIN_USER = "admin", ADMIN_PASS = "admin123";
        final String CUSTOMER_USER = "cust", CUSTOMER_PASS = "cust123";

        // Default stocks
        investmentList.addStock(new Stock(Stock.BBCA, 8000));
        investmentList.addStock(new Stock(Stock.BMRI, 4000));

        int status = 0;
        int statusRestart = 0;
        while (status == 0) {
            if (statusRestart != 0) {
                System.out.print("Login (ketik y) atau Selesai (ketik apapun): ");
                String exit = input.nextLine();
                if (exit.equalsIgnoreCase("y")) {
                } else {
                    System.out.println("Sampai ketemu lagi!");
                    break;
                }
            }

            System.out.println("\n--- LOGIN ---");
            System.out.print("Username: ");
            String username = input.nextLine();
            System.out.print("Password: ");
            String password = input.nextLine();

            if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
                adminMenu(input, investmentList);
                input.nextLine();
            } else if (username.equals(CUSTOMER_USER) && password.equals(CUSTOMER_PASS)) {
                customerMenu(input, investmentList, investment);
                input.nextLine();
            } else {
                System.out.println("Login gagal. Username atau password salah.");
            }

            statusRestart = 1;
        }

    }

    private static void adminMenu(Scanner input, InvestmentList investmentList) {
        int choice;
        do {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Saham");
            System.out.println("2. SBN");
            System.out.println("3. Logout");
            System.out.print("Pilihan: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    adminStockMenu(input, investmentList);
                    break;
                case 2:
                    adminSBNMenu(input, investmentList);
                    break;
                case 3:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 3);
    }

    private static void adminStockMenu(Scanner input, InvestmentList investmentList) {
        int choice;
        do {
            System.out.println("\n--- MENU ADMIN > SAHAM ---");
            System.out.println("1. Tambah Saham");
            System.out.println("2. Ubah Harga Saham");
            System.out.println("3. Kembali");
            System.out.print("Pilihan: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    input.nextLine();
                    System.out.print("Kode saham: ");
                    String kode = input.nextLine();
                    System.out.print("Harga saham: ");
                    int harga = input.nextInt();
                    investmentList.addStock(new Stock(kode, harga));
                    break;
                case 2:
                    investmentList.listStocks();
                    System.out.print("Pilih nomor saham yang ingin diubah harganya: ");
                    int index = input.nextInt() - 1;
                    System.out.print("Masukkan harga baru: ");
                    int newHarga = input.nextInt();
                    Stock stock = investmentList.getStockByIndex(index);
                    if (stock != null) {
                        stock.setHarga(newHarga);
                        System.out.println("Harga saham berhasil diubah.");
                    } else {
                        System.out.println("Saham tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.println("Kembali ke menu admin.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 3);
    }

    private static void adminSBNMenu(Scanner input, InvestmentList investmentList) {
        int choice;
        do {
            System.out.println("\n--- MENU ADMIN > SBN ---");
            System.out.println("1. Tambah SBN");
            System.out.println("2. Kembali");
            System.out.print("Pilihan: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    input.nextLine();
                    System.out.print("Nama SBN: ");
                    String namaSBN = input.nextLine();
                    System.out.print("Bunga (dalam persen): ");
                    double bunga = input.nextDouble();
                    System.out.print("Jangka Waktu (tahun): ");
                    int jangkaWaktu = input.nextInt();
                    System.out.print("Tanggal Jatuh Tempo (YYYY-MM-DD): ");
                    String dateString = input.next();
                    LocalDate tanggalJatuhTempo = LocalDate.parse(dateString);
                    System.out.print("Kuota Nasional: ");
                    int kuotaNasional = input.nextInt();
                    SBN sbn = new SBN(namaSBN, bunga, jangkaWaktu, tanggalJatuhTempo, kuotaNasional);
                    investmentList.addSBN(sbn);
                    break;
                case 2:
                    System.out.println("Kembali ke menu admin.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 2);
    }

    private static void customerMenu(Scanner input, InvestmentList investmentList, CustomerInvestment investment) {
        int choice;
        do {
            System.out.println("\n--- MENU CUSTOMER ---");
            System.out.println("1. Beli Saham");
            System.out.println("2. Jual Saham");
            System.out.println("3. Beli SBN");
            System.out.println("4. Simulasi SBN");
            System.out.println("5. Lihat Portofolio");
            System.out.println("6. Logout");
            System.out.print("Pilihan: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    investInStock(input, investmentList, investment);
                    break;
                case 2:
                    sellStock(input, investmentList, investment);
                    break;
                case 3:
                    investInSBN(input, investmentList, investment);
                    break;
                case 4:
                    simulateSBN(input, investmentList);
                    break;
                case 5:
                    investment.showPortfolio();
                    break;
                case 6:
                    System.out.println("Logout berhasil.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 6);
    }

    private static void investInStock(Scanner input, InvestmentList investmentList, CustomerInvestment investment) {
        investmentList.listStocks();
        System.out.print("Pilih nomor saham: ");
        int index = input.nextInt() - 1;
        Stock stock = investmentList.getStockByIndex(index);

        if (stock == null) {
            System.out.println("Saham tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan nominal investasi: ");
        int amount = input.nextInt();
        investment.buyStock(stock, amount);
    }

    private static void sellStock(Scanner input, InvestmentList investmentList, CustomerInvestment investment) {
        investmentList.listStocks();
        System.out.print("Pilih nomor saham yang ingin dijual: ");
        int index = input.nextInt() - 1;
        Stock stock = investmentList.getStockByIndex(index);

        if (stock == null) {
            System.out.println("Saham tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan jumlah saham yang ingin dijual: ");
        int sharesToSell = input.nextInt();
        investment.sellStock(stock, sharesToSell);
    }

    private static void investInSBN(Scanner input, InvestmentList investmentList, CustomerInvestment investment) {
        investmentList.listSBN();
        System.out.print("Pilih nomor SBN: ");
        int index = input.nextInt() - 1;
        SBN sbn = investmentList.getSBNByIndex(index);

        if (sbn == null) {
            System.out.println("SBN tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan jumlah unit SBN yang ingin dibeli: ");
        int units = input.nextInt();
        investment.buySBN(sbn, units);
    }

    private static void simulateSBN(Scanner input, InvestmentList investmentList) {
        System.out.print("Masukkan nominal investasi: ");
        double nominalInvestasi = input.nextDouble();

        investmentList.listSBN();
        System.out.print("Pilih nomor SBN untuk simulasi: ");
        int index = input.nextInt() - 1;
        SBN sbn = investmentList.getSBNByIndex(index);

        if (sbn == null) {
            System.out.println("SBN tidak ditemukan.");
            return;
        }

        double monthlyCoupon = sbn.hitungKuponBulanan(nominalInvestasi);
        System.out.printf("Simulasi hasil SBN %s: Rp%.2f per bulan%n", sbn.getNama(), monthlyCoupon);
    }
}
