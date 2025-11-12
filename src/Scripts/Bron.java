package Scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bron {
    private int id = 1;
    private int atk;
    private String nazwa;
    private ArrayList<String> bronie = new ArrayList<>(List.of(
            "Młot Zguby Thora",
            "Kij od stołu Odyna",
            "Sztylet Chrzczony w Barszczu",
            "Topór Przypadkowego Bohatera",
            "Włócznia Śmierdzącego Trolla",
            "Korbacz Bez Litości",
            "Maczuga Cioci Cerbera",
            "Tasak Porannej Agresji",
            "Pałka z Przypadkowej Stodoły",
            "Miecz, Którym Nikt Nie Trafia",
            "Widelec Bitewny",
            "Łom Niesprawiedliwości",
            "Berło Zgrzytającej Stali",
            "Cep Rozpaczy",
            "Toporek Pana Mietka",
            "Rękawica Odwróconej Grawitacji",
            "Kordzik Zawstydzenia",
            "Szabla Zdzirańska",
            "Buława Zepsutego Paladyna",
            "Kosa z Jotunheimu"
    ));

    public Bron() {
    }

    public void losujBron(){
        Random rand = new Random();
        int nrn = rand.nextInt(20);
        nazwa = bronie.get(nrn);
        atk = rand.nextInt(100)+1;
    }
}
