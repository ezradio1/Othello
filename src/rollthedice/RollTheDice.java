package rollthedice;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import rollthedice.Game;

public class RollTheDice {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Map<Integer, Integer> mode = new HashMap<Integer, Integer>();
        Game[] game = new Game[12];

        String inputString = "";
        int menu = 0;
        int[] totalPerRoll = new int[12];

        do {
            try {
                System.out.println("\n ~ PERMAINAN LEMPAR DADU ~ ");
                System.out.println("[1] LEMPAR DADU");
                System.out.println("[2] KELUAR");
                System.out.print("Pilihan >>>> ");
                inputString = sc.next();
                menu = 0;
                menu = Integer.parseInt(inputString);

                switch (menu) {
                    case 1:
                        // Inisialisasi value
                        inisialisasi(menu, totalPerRoll, mode, game);

                        // Proses game
                        prosesGame(game, totalPerRoll, mode);

                        if (game[0] == null) {
                            System.out.println("Tidak ada data!");
                        } else {
                            // Daftar poin disetiap lemparan
                            getTabelDaftarPoin(game);

                            // Menampilkan detail hasil lemparan
                            getDetailHasil(mode, game);

                            // show grafik total point setiap lemparan
                            tampilGrafikTotalPoin(totalPerRoll);

                            // show grafik jumlah keluar setiap mata dadu
                            tampilGrafikMataDadu(mode);
                        }
                        break;

                    case 2:
                        System.out.println("Terimakasih!");
                        break;

                    default:
                        System.out.println("Menu hanya 1 dan 2!");
                        menu = 0;
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error : Inputan harus angka");
            } catch (Exception e) {
                menu = 0;
                System.out.println("Error : " + e);
            }

        } while (menu != 2);
        sc.close();
    }

    public static void inisialisasi(int menu, int[] totalPerRoll, Map<Integer, Integer> mode, Game[] game) {
        menu = 0;

        for (int i = 0; i < 12; i++) {
            totalPerRoll[i] = 0;
            game[i] = null;
        }

        for (int i = 1; i <= 6; i++) {
            mode.put(i, 0);
        }
    }

    public static void prosesGame(Game[] game, int[] totalPerRoll, Map<Integer, Integer> mode) {
        Scanner sc = new Scanner(System.in);
        char confirm = '-';
        int idx, value;

        for (int i = 0; i < 12; i++) {
            System.out.println("\nRONDE " + (i + 1));
            do {
                try {
                    System.out.print("Lempar dadu [Y/N] ? >>> ");
                    confirm = sc.next().charAt(0);
                    if (confirm != 'Y' && confirm != 'y' && confirm != 'N' && confirm != 'n') {
                        throw new IllegalArgumentException("Pilihan hanya Y dan N!");
                    }
                } catch (Exception e) {
                    confirm = '-';
                    System.out.println("Error : " + e.getMessage());
                }
            } while (confirm == '-');
            if (confirm == 'Y' || confirm == 'y') {
                game[i] = new Game();
                game[i].play();
                game[i].showResultPerGame();

                totalPerRoll[i] = game[i].getTotalValue();

                // proses menghitung modus
                idx = game[i].getResultFirstDice();
                value = mode.get(idx) + 1;
                mode.replace(idx, value);

                idx = game[i].getResultSecondDice();
                value = mode.get(idx) + 1;
                mode.replace(idx, value);

            } else {
                break;
            }
        }
    }

    public static int getTotalPoint(Game[] game) {
        int temp = 0;
        for (int i = 0; i < game.length; i++) {
            if (game[i] != null) {
                temp += game[i].getTotalValue();
            }
        }
        return temp;

    }

    public static int getTotalGame(Game[] game) {
        int temp = 0;
        for (int i = 0; i < game.length; i++) {
            if (game[i] != null) {
                temp++;
            }
        }
        return temp;

    }

    public static int getTotalLemparan(int[] total) {
        int temp = 0;
        for (int i = 0; i < total.length; i++) {
            if (total[i] != 0) {
                temp++;
            }
        }
        return temp;

    }

    public static void getTabelDaftarPoin(Game[] game) {
        System.out.println();
        System.out.println("\n====================================================\n");
        System.out.println("DAFTAR POIN");
        System.out.printf("-------------------------------------------%n");
        System.out.printf("%-10s %-10s %-10s %-10s%n", "Lemparan", "Dadu-1", "Dadu-2", "Total");
        System.out.printf("-------------------------------------------%n");
        for (int i = 0; i < game.length; i++) {
            if (game[i] != null) {
                System.out.printf("%-10d %-10d %-10d %-10d%n", (i + 1), game[i].getResultFirstDice(),
                        game[i].getResultSecondDice(), game[i].getTotalValue());
            }
        }
        System.out.printf("-------------------------------------------%n");
        System.out.println();
    }

    public static void getDetailHasil(Map<Integer, Integer> mode, Game[] game) {
        int counter = 0;
        int total = getTotalPoint(game);
        int totalGame = getTotalGame(game);

        int max = Collections.max(mode.values());
        Map<Integer, Integer> modusTemp = mode.entrySet().stream().filter(x -> x.getValue() == max)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("DETAIL HASIL PELEMPARAN\n");
        System.out.println("Jumlah Total Point      = " + total);
        System.out.printf("Rata - rata             = %-5.2f%n", (float) total / totalGame);

        // menampilkan modus jika lebih dari 1
        System.out.printf("Modus                   = ");
        for (Entry<Integer, Integer> item : modusTemp.entrySet()) {
            counter++;
            if (counter == modusTemp.size() && counter != 1) {
                System.out.printf("dan ");
            }
            System.out.printf("Mata Dadu %d", item.getKey());
            if (counter < modusTemp.size()) {
                System.out.printf(", ");
            }
        }
        System.out.println("\nJumlah muncul           = " + max + " kali");
        System.out.println();
    }

    public static void tampilGrafikTotalPoin(int[] totalPerRoll) {
        int maxColumn = Arrays.stream(totalPerRoll).max().getAsInt();
        int totalLemparan = getTotalLemparan(totalPerRoll);

        System.out.println();
        System.out.println("GRAFIK TOTAL POINT SETIAP LEMPARAN\n");

        for (int i = maxColumn; i >= -1; i--) {
            if (i == -1) {
                System.out.printf("%-5s", "");
            } else if (i == 0) {
                // untuk mencetak garis di horizontal axis
                System.out.printf("%-4s", "");
            } else {
                // untuk mencetak vertical axis
                if (i % 2 == 0) {
                    System.out.printf("%-3d|%-2s", i, "");
                } else {
                    System.out.printf("%-3s|%-2s", "", "");
                }
            }

            for (int j = 1; j <= totalLemparan; j++) {
                if (i == -1) {
                    // untuk mencetak keterangan horizontal axis
                    System.out.printf("Roll-%-4d", j);
                } else if (i == 0) {
                    // untuk mencetak garis di horizontal axis
                    System.out.printf("---------", "");
                } else if (i > 0) {
                    if (totalPerRoll[j - 1] == i) {
                        System.out.printf("%-9s", "|-|");
                        totalPerRoll[j - 1]--;
                    } else {
                        System.out.printf("%-9s", " ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println();
    }

    public static void tampilGrafikMataDadu(Map<Integer, Integer> mode) {
        Map<Integer, Integer> tempModus = new HashMap<Integer, Integer>(mode);
        System.out.println();
        System.out.println("GRAFIK TOTAL JUMLAH KELUAR SETIAP MATA DADU\n");
        int maxColumn = Collections.max(mode.values());
        for (int i = maxColumn; i >= -1; i--) {
            if (i == -1) {
                System.out.printf("%-5s", "");
            } else if (i == 0) {
                // untuk mencetak spasi di horizontal axis
                System.out.printf("%-3s", "");
            } else {
                // untuk mencetak vertical axis
                if (maxColumn == 1) {
                    System.out.printf("%-3d|%-2s", i, "");
                } else if (i % 2 == 0) {
                    System.out.printf("%-3d|%-2s", i, "");
                } else {
                    System.out.printf("%-3s|%-2s", "", "");
                }
            }
            for (Entry<Integer, Integer> item : mode.entrySet()) {
                if (tempModus.get(item.getKey()) != 0) {
                    if (i == -1) {
                        // untuk mencetak keterangan horizontal axis
                        System.out.printf("Dadu-%-4d", item.getKey());
                    } else if (i == 0) {
                        // untuk mencetak garis di horizontal axis
                        System.out.printf("---------", "");
                    } else if (i > 0) {
                        if (item.getValue() == i) {
                            System.out.printf("%-9s", "|-|");
                            mode.replace(item.getKey(), item.getValue() - 1);
                        } else {
                            System.out.printf("%-9s", " ");
                        }
                    }
                }
            }
            System.out.println("");
        }
        System.out.println();
    }
}
