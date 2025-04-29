# Program Investasi Saham dan SBN

Program ini adalah sistem sederhana berbasis Java untuk mengelola **investasi Saham** dan **SBN (Surat Berharga Negara)**, dengan dua jenis pengguna: **Admin** dan **Customer**.

---

## Struktur Kelas

### `SBN`
Merepresentasikan Surat Berharga Negara.
- **Attributes**: nama, bunga (%), jangka waktu (tahun), tanggal jatuh tempo, kuota nasional.
- **Methods**:
  - Getter untuk semua atribut.
  - `hitungKuponBulanan(nominalInvestasi)`: menghitung kupon bulanan yang diterima.
  - `tampilanInfo()`: menampilkan detail informasi SBN.

---

### `Stock`
Merepresentasikan saham.
- **Attributes**: kode saham (`String`), harga (`int`).
- **Methods**:
  - Getter dan setter untuk kode dan harga saham.

---

### `InvestmentList`
Menyimpan daftar saham dan SBN yang tersedia.
- **Attributes**:
  - Array `Stock[] stocks`
  - Array `SBN[] sbns`
- **Methods**:
  - Tambah, tampilkan, hapus, dan ambil data `Stock` atau `SBN`.

---

### `CustomerInvestment`
Menyimpan portofolio investasi customer.
- **Attributes**:
  - `Stock[] ownedStocks`, `SBN[] ownedSBNs`
  - `int[] ownedStockShares`, `int[] ownedSBNUnits`
- **Methods**:
  - Membeli dan menjual saham/SBN.
  - Menampilkan portofolio saat ini.

---

## Alur Program

### Login
User memilih untuk login sebagai:
- `admin / admin123` ➔ Masuk ke menu Admin
- `cust / cust123` ➔ Masuk ke menu Customer

Jika login gagal, program meminta input ulang atau keluar.

---

### Menu Admin
#### 1. Kelola Saham
- **Tambah Saham**: input kode dan harga saham.
- **Ubah Harga Saham**: pilih saham lalu masukkan harga baru.

#### 2. Kelola SBN
- **Tambah SBN**: input nama, bunga, jangka waktu, tanggal jatuh tempo, dan kuota nasional.

#### 3. Logout
Kembali ke menu login.

---

### Menu Customer
#### 1. Lihat Daftar Saham
Menampilkan semua saham yang tersedia.

#### 2. Lihat Daftar SBN
Menampilkan semua SBN yang tersedia.

#### 3. Beli Saham
- Input nomor saham.
- Input nominal uang.
- Sistem menghitung berapa lembar saham yang terbeli.

#### 4. Jual Saham
- Input saham dan jumlah lembar yang ingin dijual.

#### 5. Beli SBN
- Input nomor SBN.
- Input jumlah unit yang ingin dibeli.

#### 6. Jual SBN
- Input SBN dan jumlah unit yang ingin dijual.

#### 7. Lihat Portofolio
Menampilkan investasi saham dan SBN yang dimiliki, serta total nilainya.

#### 8. Logout
Kembali ke menu login.

---

## Catatan Tambahan

- Harga saham bisa berubah saat diubah admin.
- Pendapatan jual SBN disimulasikan dengan menggunakan bunga sebagai nilai satuan (untuk kesederhanaan).
- Setiap pembelian SBN mengecek apakah melebihi kuota nasional.
- Sistem login berbasis teks biasa (belum terenkripsi).
- Array investasi dibatasi (`max 10`) untuk saham dan SBN per user.

---

## Contoh Output
```bash
--- LOGIN ---
Username: admin
Password: admin123

--- MENU ADMIN ---
1. Saham
2. SBN
3. Logout
Pilihan: 1

--- MENU ADMIN > SAHAM ---
1. Tambah Saham
2. Ubah Harga Saham
3. Kembali
Pilihan: 1
Kode saham: BBCA
Harga saham: 8000
Saham berhasil ditambahkan.
