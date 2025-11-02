package repository;


import java.util.*;

public class FakeBussAPI {


    private int id = 100;
    private String avgang;
    private boolean hundevennlig;
    private boolean rullestolvennlig;

    // ///////////////////////////////////////


    // ////////////////////////////////////////////////////////////////////

    //      Map og Metoder for FRA sted og stopp, og TIL Sted og stopp

    // ////////////////////////////////////////////////////////////////////

    // /////////////////////////////
    // By og Stopp-liste som hentes ut i reiseVelger()
    //
    private final Map<String, List<String>> data = Map.of(
            "Halden", List.of(
                    "Halden stasjon",
                    "Rød Herregård",
                    "Tistedal",
                    "Torget",
                    "Busterudparken"),

            "Fredrikstad", List.of(
                    "Fredrikstad stasjon",
                    "Gamlebyen",
                    "Værste",
                    "Kråkerøy",
                    "Biblioteket"),

            "Oslo", List.of(
                    "Oslo S",
                    "Aker Brygge",
                    "Karl Johans gate",
                    "Majorstuen",
                    "Tøyen"),

            "Moss", List.of(
                    "Moss stasjon",
                    "Varnaveien",
                    "Melløs stadion",
                    "Nøkkeland skole",
                    "Jeløya kirke"),

            "Askim",  List.of(
                    "Askim stasjon",
                    "Askim torv",
                    "Grønli skole",
                    "Slitu",
                    "Grøtvedt"),

            "Tønsberg",   List.of(
                    "Tønsberg jernbanestasjon",
                    "Farmandstredet",
                    "Kilen",
                    "Eik",
                    "Presterød ungdomsskole",
                    "Barkåker"),

            "Sarpsborg",  List.of(
                    "Sarpsborg bussterminal",
                    "Valaskjold bru",
                    "Tune kirke",
                    "Kalnes sykehus",
                    "Sandbakken")
    );



    public List<String> hentByer() {
        return new ArrayList<>(data.keySet());
    }

    public List<String> hentStederIBy(String city) {
        return data.getOrDefault(city, List.of());
    }


    private static int velgFraListe(String tittel, List<String> alternativer, Scanner sc) {
        while (true) {
            System.out.println("\n" + tittel + ":");
            for (int i = 0; i < alternativer.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, alternativer.get(i));
            }
            System.out.print("Skriv tallet til valget ditt: ");

            if (!sc.hasNextLine()) {
                throw new IllegalStateException("Ingen input tilgjengelig på System.in");
            }
            String input = sc.nextLine().trim();
            try {
                int valg = Integer.parseInt(input);
                if (valg >= 1 && valg <= alternativer.size()) return valg - 1;
            } catch (NumberFormatException ignored) {}
            System.out.println("Ugyldig valg – prøv igjen.");
        }
    }

    // ////////////////////////////////////////////////////////////////////

    //               BussDataRepo / RUTE-TABELL
    //
    //          Inneholder hvilke "tilgjengeligheter" bussene har
    //          til en hver tid/avgang. Det er dette som skal filtreres.
    //          Hver rute/objekt får en rute-ID, bare for simuleringens skyld.
    //
    // ////////////////////////////////////////////////////////////////////







    // ///////////////////////////////////////////////////
    //
    //             KONSTRUKTØRER

    public FakeBussAPI(String avgang, boolean hundevennlig, boolean rullestolvennlig) {
        this.id = id++;
        this.avgang = avgang;
        this.hundevennlig = hundevennlig;
        this.rullestolvennlig = rullestolvennlig;
    }


    public FakeBussAPI() {
    }


    /*@Override
    public String toString() {
        return ""
                *//*String.format("ID: %02d | Avgang: %s | Hund: %s | Rullestol: %s",
                id, avgang,
                hundevennlig ? "Ja" : "Nei",
                rullestolvennlig ? "Ja" : "Nei");*//*
    }
*/


    // ///////////////////////////////////////////////////
    //
    //             GETTERE OG SETTERE

    public String getAvgang() {
        return avgang;
    }

    public void setAvgang(String avgang) {
        this.avgang = avgang;
    }

    public boolean isHundevennlig() {
        return hundevennlig;
    }

    public String isHundevennligString() {
        String hundevennlig;
        if (this.hundevennlig == true) {
            hundevennlig = "Hundevennlig";
            return hundevennlig;
        }
        else {
            hundevennlig = "Ikke hundevennlig";
            return hundevennlig;
        }
    }

    public void setHundevennlig(boolean hundevennlig) {
        this.hundevennlig = hundevennlig;
    }

    public boolean isRullestolvennlig() {
        return rullestolvennlig;
    }

    public String isRullestolvennligString() {
        String rullestolvennlig;
        if (this.rullestolvennlig == true) {
            rullestolvennlig = "Rullestolvennlig";
            return rullestolvennlig;
        }
        else {
            rullestolvennlig = "Ikke rullestolvennlig";
            return rullestolvennlig;
        }
    }

    public void setRullestolvennlig(boolean rullestolvennlig) {
        this.rullestolvennlig = rullestolvennlig;
    }





}