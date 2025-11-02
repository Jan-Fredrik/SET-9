package controller;

import repository.FakeBussAPI;

import java.util.ArrayList;
import java.util.List;

public class RuteFiltrering {

    // Viser avganger i konsollen basert på preferansan
    //

    public void FiltrerAvgangerEtterPreferanser(List<FakeBussAPI> avganger) {
        System.out.println("\n Filtrerte avganger:");
        System.out.printf("%-8s | %-18s | %-20s%n", "Avgang", "Hund", "Rullestol");
        System.out.println("-----------------------------------------------");
        for (FakeBussAPI avgang : avganger) {
            System.out.printf(
                    "%-8s | %-18s | %-20s%n",
                    avgang.getAvgang(),
                    avgang.isHundevennligString(),
                    avgang.isRullestolvennligString()
            );
        }
    }


    public void visBareFiltrerteAvganger(List<FakeBussAPI> avganger, boolean hundePref, boolean rullestolpref) {
        System.out.println("\n Filtrerte avganger som nøyaktig passer dine preferanser\n");
        System.out.println("Avganger");
        System.out.println("-----------------------------------------------");

        List<FakeBussAPI> bareFiltreteAvganger = new ArrayList<>();

        for (FakeBussAPI avgang : avganger) {
            if (hundePref == avgang.isHundevennlig() && rullestolpref == avgang.isRullestolvennlig() ) {

                System.out.println(avgang.getAvgang());
            }



        }

    }



}