package repository;

import java.util.*;

public class FakeBussAPI {
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

}
class Demo {
    public static void main(String[] args) {
        FakeBussAPI api = new FakeBussAPI();

        // én scanner for hele programmet
        try (Scanner sc = new Scanner(System.in)) {

            // 1) Velg by
            List<String> byer = new ArrayList<>(api.hentByer());
            Collections.sort(byer);
            int byIndex = velgFraListe("Velg by", byer, sc);
            String valgtBy = byer.get(byIndex);

            // 2) Velg stoppested
            List<String> stoppesteder = new ArrayList<>(api.hentStederIBy(valgtBy));
            Collections.sort(stoppesteder);
            int stoppIndex = velgFraListe("Velg stoppested i " + valgtBy, stoppesteder, sc);
            String valgtStopp = stoppesteder.get(stoppIndex);

            System.out.println("\nDu valgte: " + valgtBy + " -> " + valgtStopp);
        }
    }

    // ⚠️ Ikke lukk Scanner her – den eies av main()
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
}