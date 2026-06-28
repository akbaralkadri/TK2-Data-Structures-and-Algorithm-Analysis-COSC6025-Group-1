/*
 * Tugas Kelompok 1 - Data Structures and Algorithm Analysis (COSC6025)
 * Group 1 : 
 * DANISCH ASWANDA MAULANA SUBKHAN (2902772113)
 * JUSTIN DERYL FERDINAND (2902786781)
 * MUHAMMAD AKBAR ALKADRI (2902768192)
 * REHO KURNIA (2902763260)
 
 */
import java.util.Scanner;

public class PlaylistArray {
    
    static Lagu[] playlist = new Lagu[10];
    static int jumlahLagu = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan = 0;

        do {
            System.out.println("\n=== MENU PLAYLIST MUSIK ===");
            System.out.println("1. Tampilkan semua lagu");
            System.out.println("2. Tambah lagu baru");
            System.out.println("3. Hapus lagu berdasarkan judul");
            System.out.println("4. Cari lagu berdasarkan judul");
            System.out.println("5. Urutkan lagu (Insertion Sort)");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            
            try {
                pilihan = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                pilihan = -1;
            }

            if (pilihan == 1) {
                tampilkanSemuaLagu();
            } else if (pilihan == 2) {
                tambahLagu(input);
            } else if (pilihan == 3) {
                hapusLagu(input);
            } else if (pilihan == 4) {
                cariLagu(input);
            } else if (pilihan == 5) {
                urutkanLagu(input);
            } else if (pilihan == 6) {
                System.out.println("Program selesai.");
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 6);
        
        input.close();
    }

    // Tampilkan semua lagu
    static void tampilkanSemuaLagu() {
        System.out.println("\nDaftar lagu saat ini:");
        if (jumlahLagu == 0) {
            System.out.println("Playlist masih kosong.");
            return;
        }
        for (int i = 0; i < jumlahLagu; i++) {
            System.out.println((i + 1) + ". " + playlist[i].getJudul() + " - " + playlist[i].getArtis() + " (" + playlist[i].getDurasi() +  " menit)");
        }
    }

    // Tambah lagu baru
    static void tambahLagu(Scanner input) {
        if (jumlahLagu >= 10) {
            System.out.println("Playlist penuh! Maksimal hanya dapat menampung 10 lagu.");
            return;
        }
        
        System.out.print("Masukkan judul lagu : ");
        String judul = input.nextLine();
        System.out.print("Masukkan artis      : ");
        String artis = input.nextLine();
        System.out.print("Masukkan durasi (menit): ");
        double durasi = Double.parseDouble(input.nextLine());

        playlist[jumlahLagu] = new Lagu(judul, artis, durasi);
        jumlahLagu++;
        
        System.out.println("Lagu berhasil ditambahkan!");
    }

    // Hapus lagu berdasarkan judul
    static void hapusLagu(Scanner input) {
        System.out.print("Masukkan judul lagu yang akan dihapus: ");
        String judul = input.nextLine();
        boolean ketemu = false;

        for (int i = 0; i < jumlahLagu; i++) {
            if (playlist[i].getJudul().equalsIgnoreCase(judul)) {
                ketemu = true;
                
                for (int j = i; j < jumlahLagu - 1; j++) {
                    playlist[j] = playlist[j + 1];
                }
                
                playlist[jumlahLagu - 1] = null; 
                jumlahLagu--; 
                
                System.out.println("Lagu '" + judul + "' berhasil dihapus.");
                break;
            }
        }
        
        if (!ketemu) {
            System.out.println("Lagu dengan judul '" + judul + "' tidak ditemukan.");
        }
    }

    // Cari lagu berdasarkan judul
    static void cariLagu(Scanner input) {
        System.out.print("Masukkan judul lagu yang dicari: ");
        String judul = input.nextLine();
        boolean ketemu = false;

        for (int i = 0; i < jumlahLagu; i++) {
            if (playlist[i].getJudul().equalsIgnoreCase(judul)) {
                System.out.println("\n--- Detail Lagu Ditemukan ---");
                playlist[i].tampilkanInfo(); 
                ketemu = true;
                break; 
            }
        }
        
        if (!ketemu) {
            System.out.println("Lagu dengan judul '" + judul + "' tidak ditemukan.");
        }
    }

    // Urutkan lagu menggunakan Insertion Sort
    static void urutkanLagu(Scanner input) {
        if (jumlahLagu == 0) {
            System.out.println("Playlist masih kosong, tidak ada yang diurutkan.");
            return;
        }

        System.out.println("\n=== URUTKAN LAGU ===");
        System.out.println("Urutkan berdasarkan:");
        System.out.println("1. Judul");
        System.out.println("2. Artis");
        System.out.println("3. Durasi");
        System.out.print("Pilih kriteria pengurutan: ");
        
        int kriteria = Integer.parseInt(input.nextLine());
        
        System.out.println("Urutan:");
        System.out.println("1. Ascending (A-Z / 0-9)");
        System.out.println("2. Descending (Z-A / 9-0)");
        System.out.print("Pilih urutan: ");
        
        int urutan = Integer.parseInt(input.nextLine());
        boolean ascending = (urutan == 1);
        
        if (kriteria == 1) {
            insertionSortByJudul(ascending);
            System.out.println("Lagu berhasil diurutkan berdasarkan JUDUL!");
        } else if (kriteria == 2) {
            insertionSortByArtis(ascending);
            System.out.println("Lagu berhasil diurutkan berdasarkan ARTIS!");
        } else if (kriteria == 3) {
            insertionSortByDurasi(ascending);
            System.out.println("Lagu berhasil diurutkan berdasarkan DURASI!");
        } else {
            System.out.println("Pilihan tidak valid.");
            return;
        }
        
        System.out.println("\nHasil pengurutan:");
        tampilkanSemuaLagu();
    }

    // Insertion Sort berdasarkan Judul
    static void insertionSortByJudul(boolean ascending) {
        for (int i = 1; i < jumlahLagu; i++) {
            Lagu key = playlist[i];
            int j = i - 1;
            
            while (j >= 0) {
                int compare = playlist[j].getJudul().compareToIgnoreCase(key.getJudul());
                
                if ((ascending && compare > 0) || (!ascending && compare < 0)) {
                    playlist[j + 1] = playlist[j];
                    j--;
                } else {
                    break;
                }
            }
            
            playlist[j + 1] = key;
        }
    }

    // Insertion Sort berdasarkan Artis
    static void insertionSortByArtis(boolean ascending) {
        for (int i = 1; i < jumlahLagu; i++) {
            Lagu key = playlist[i];
            int j = i - 1;
            
            while (j >= 0) {
                int compare = playlist[j].getArtis().compareToIgnoreCase(key.getArtis());
                
                if ((ascending && compare > 0) || (!ascending && compare < 0)) {
                    playlist[j + 1] = playlist[j];
                    j--;
                } else {
                    break;
                }
            }
            
            playlist[j + 1] = key;
        }
    }

    // Insertion Sort berdasarkan Durasi
    static void insertionSortByDurasi(boolean ascending) {
        for (int i = 1; i < jumlahLagu; i++) {
            Lagu key = playlist[i];
            int j = i - 1;
            
            while (j >= 0) {
                double compare = playlist[j].getDurasi() - key.getDurasi();
                
                if ((ascending && compare > 0) || (!ascending && compare < 0)) {
                    playlist[j + 1] = playlist[j];
                    j--;
                } else {
                    break;
                }
            }
            
            playlist[j + 1] = key;
        }
    }
}
