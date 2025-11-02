package repository;

import java.util.ArrayList;
import java.util.List;

public class FakeBussData {

    private final List<FakeBussAPI> avgangerHalden = new ArrayList<>();
    private final List<FakeBussAPI> avgangerFredrikstad = new ArrayList<>();
    private final List<FakeBussAPI> avgangerMoss = new ArrayList<>();
    private final List<FakeBussAPI> avgangerOslo = new ArrayList<>();
    private final List<FakeBussAPI> avgangerTønsberg = new ArrayList<>();
    private final List<FakeBussAPI> avgangerSarpsborg = new ArrayList<>();
    private final List<FakeBussAPI> avgangerAskim = new ArrayList<>();


    // /////////////////////////////////////////////////////////////////
    //                  BUSS TILGJENGELIGHET REPO
    //         Repo for å holde på alle tilgjengelighetene for
    //         bussene som kjører på ruten.

    public FakeBussData() {

        // Halden
        avgangerHalden.add(new FakeBussAPI("06:00", false, false));
        avgangerHalden.add(new FakeBussAPI("06:30", true, false));
        avgangerHalden.add(new FakeBussAPI("07:00", false, true));
        avgangerHalden.add(new FakeBussAPI("07:30", true, true));
        avgangerHalden.add(new FakeBussAPI("08:00", true, false));
        avgangerHalden.add(new FakeBussAPI("08:30", true, true));
        avgangerHalden.add(new FakeBussAPI("09:00", false, false));
        avgangerHalden.add(new FakeBussAPI("09:30", true, true));
        avgangerHalden.add(new FakeBussAPI("10:00", true, false));
        avgangerHalden.add(new FakeBussAPI("10:30", false, true));
        avgangerHalden.add(new FakeBussAPI("11:00", true, true));
        avgangerHalden.add(new FakeBussAPI("11:30", true, false));
        avgangerHalden.add(new FakeBussAPI("12:00", false, true));
        avgangerHalden.add(new FakeBussAPI("12:30", true, true));
        avgangerHalden.add(new FakeBussAPI("13:00", false, false));
        avgangerHalden.add(new FakeBussAPI("13:30", true, true));
        avgangerHalden.add(new FakeBussAPI("14:00", true, false));
        avgangerHalden.add(new FakeBussAPI("14:30", false, true));
        avgangerHalden.add(new FakeBussAPI("15:00", true, true));
        avgangerHalden.add(new FakeBussAPI("15:30", true, true));
        avgangerHalden.add(new FakeBussAPI("16:00", true, false));
        avgangerHalden.add(new FakeBussAPI("16:30", false, true));
        avgangerHalden.add(new FakeBussAPI("17:00", true, true));
        avgangerHalden.add(new FakeBussAPI("17:30", true, false));
        avgangerHalden.add(new FakeBussAPI("18:00", true, true));
        avgangerHalden.add(new FakeBussAPI("19:00", false, true));
        avgangerHalden.add(new FakeBussAPI("20:00", true, true));
        avgangerHalden.add(new FakeBussAPI("21:00", true, false));
        avgangerHalden.add(new FakeBussAPI("22:00", true, true));
        avgangerHalden.add(new FakeBussAPI("23:00", false, false));


        // Sarpsborg
        avgangerSarpsborg.add(new FakeBussAPI("06:00", true, false));
        avgangerSarpsborg.add(new FakeBussAPI("06:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("07:00", false, true));
        avgangerSarpsborg.add(new FakeBussAPI("07:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("08:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("08:30", false, true));
        avgangerSarpsborg.add(new FakeBussAPI("09:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("09:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("10:00", false, false));
        avgangerSarpsborg.add(new FakeBussAPI("10:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("11:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("11:30", true, false));
        avgangerSarpsborg.add(new FakeBussAPI("12:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("12:30", false, true));
        avgangerSarpsborg.add(new FakeBussAPI("13:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("13:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("14:00", true, false));
        avgangerSarpsborg.add(new FakeBussAPI("14:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("15:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("15:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("16:00", false, true));
        avgangerSarpsborg.add(new FakeBussAPI("16:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("17:00", true, false));
        avgangerSarpsborg.add(new FakeBussAPI("17:30", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("18:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("19:00", true, false));
        avgangerSarpsborg.add(new FakeBussAPI("20:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("21:00", false, true));
        avgangerSarpsborg.add(new FakeBussAPI("22:00", true, true));
        avgangerSarpsborg.add(new FakeBussAPI("23:00", true, true));


        // Moss
        avgangerMoss.add(new FakeBussAPI("06:00", true, true));
        avgangerMoss.add(new FakeBussAPI("06:30", true, true));
        avgangerMoss.add(new FakeBussAPI("07:00", true, false));
        avgangerMoss.add(new FakeBussAPI("07:30", true, true));
        avgangerMoss.add(new FakeBussAPI("08:00", true, true));
        avgangerMoss.add(new FakeBussAPI("08:30", true, true));
        avgangerMoss.add(new FakeBussAPI("09:00", true, false));
        avgangerMoss.add(new FakeBussAPI("09:30", true, true));
        avgangerMoss.add(new FakeBussAPI("10:00", true, true));
        avgangerMoss.add(new FakeBussAPI("10:30", true, true));
        avgangerMoss.add(new FakeBussAPI("11:00", true, true));
        avgangerMoss.add(new FakeBussAPI("11:30", true, false));
        avgangerMoss.add(new FakeBussAPI("12:00", true, true));
        avgangerMoss.add(new FakeBussAPI("12:30", true, true));
        avgangerMoss.add(new FakeBussAPI("13:00", true, false));
        avgangerMoss.add(new FakeBussAPI("13:30", true, true));
        avgangerMoss.add(new FakeBussAPI("14:00", true, true));
        avgangerMoss.add(new FakeBussAPI("14:30", true, true));
        avgangerMoss.add(new FakeBussAPI("15:00", true, true));
        avgangerMoss.add(new FakeBussAPI("15:30", true, false));
        avgangerMoss.add(new FakeBussAPI("16:00", true, true));
        avgangerMoss.add(new FakeBussAPI("16:30", true, true));
        avgangerMoss.add(new FakeBussAPI("17:00", true, true));
        avgangerMoss.add(new FakeBussAPI("17:30", true, true));
        avgangerMoss.add(new FakeBussAPI("18:00", true, true));
        avgangerMoss.add(new FakeBussAPI("19:00", true, false));
        avgangerMoss.add(new FakeBussAPI("20:00", true, true));
        avgangerMoss.add(new FakeBussAPI("21:00", true, true));
        avgangerMoss.add(new FakeBussAPI("22:00", true, true));
        avgangerMoss.add(new FakeBussAPI("23:00", true, true));

        // Oslo
        avgangerOslo.add(new FakeBussAPI("06:00", true, true));
        avgangerOslo.add(new FakeBussAPI("06:30", true, true));
        avgangerOslo.add(new FakeBussAPI("07:00", true, true));
        avgangerOslo.add(new FakeBussAPI("07:30", true, true));
        avgangerOslo.add(new FakeBussAPI("08:00", true, true));
        avgangerOslo.add(new FakeBussAPI("08:30", true, true));
        avgangerOslo.add(new FakeBussAPI("09:00", true, true));
        avgangerOslo.add(new FakeBussAPI("09:30", true, true));
        avgangerOslo.add(new FakeBussAPI("10:00", true, true));
        avgangerOslo.add(new FakeBussAPI("10:30", true, true));
        avgangerOslo.add(new FakeBussAPI("11:00", true, true));
        avgangerOslo.add(new FakeBussAPI("11:30", true, true));
        avgangerOslo.add(new FakeBussAPI("12:00", true, true));
        avgangerOslo.add(new FakeBussAPI("12:30", true, true));
        avgangerOslo.add(new FakeBussAPI("13:00", true, true));
        avgangerOslo.add(new FakeBussAPI("13:30", true, true));
        avgangerOslo.add(new FakeBussAPI("14:00", true, true));
        avgangerOslo.add(new FakeBussAPI("14:30", true, true));
        avgangerOslo.add(new FakeBussAPI("15:00", true, true));
        avgangerOslo.add(new FakeBussAPI("15:30", true, true));
        avgangerOslo.add(new FakeBussAPI("16:00", true, true));
        avgangerOslo.add(new FakeBussAPI("16:30", true, true));
        avgangerOslo.add(new FakeBussAPI("17:00", true, true));
        avgangerOslo.add(new FakeBussAPI("17:30", true, true));
        avgangerOslo.add(new FakeBussAPI("18:00", true, true));
        avgangerOslo.add(new FakeBussAPI("19:00", true, true));
        avgangerOslo.add(new FakeBussAPI("20:00", true, true));
        avgangerOslo.add(new FakeBussAPI("21:00", true, true));
        avgangerOslo.add(new FakeBussAPI("22:00", true, true));
        avgangerOslo.add(new FakeBussAPI("23:00", true, true));

        // Askim
        avgangerAskim.add(new FakeBussAPI("06:00", false, false));
        avgangerAskim.add(new FakeBussAPI("06:30", true, false));
        avgangerAskim.add(new FakeBussAPI("07:00", false, false));
        avgangerAskim.add(new FakeBussAPI("07:30", true, false));
        avgangerAskim.add(new FakeBussAPI("08:00", true, true));
        avgangerAskim.add(new FakeBussAPI("08:30", false, false));
        avgangerAskim.add(new FakeBussAPI("09:00", true, false));
        avgangerAskim.add(new FakeBussAPI("09:30", true, false));
        avgangerAskim.add(new FakeBussAPI("10:00", false, false));
        avgangerAskim.add(new FakeBussAPI("10:30", true, false));
        avgangerAskim.add(new FakeBussAPI("11:00", false, false));
        avgangerAskim.add(new FakeBussAPI("11:30", true, false));
        avgangerAskim.add(new FakeBussAPI("12:00", true, true));
        avgangerAskim.add(new FakeBussAPI("12:30", false, false));
        avgangerAskim.add(new FakeBussAPI("13:00", true, false));
        avgangerAskim.add(new FakeBussAPI("13:30", false, false));
        avgangerAskim.add(new FakeBussAPI("14:00", true, false));
        avgangerAskim.add(new FakeBussAPI("14:30", false, false));
        avgangerAskim.add(new FakeBussAPI("15:00", true, false));
        avgangerAskim.add(new FakeBussAPI("15:30", true, false));
        avgangerAskim.add(new FakeBussAPI("16:00", false, false));
        avgangerAskim.add(new FakeBussAPI("16:30", true, false));
        avgangerAskim.add(new FakeBussAPI("17:00", true, false));
        avgangerAskim.add(new FakeBussAPI("17:30", false, false));
        avgangerAskim.add(new FakeBussAPI("18:00", true, false));
        avgangerAskim.add(new FakeBussAPI("19:00", true, true));
        avgangerAskim.add(new FakeBussAPI("20:00", true, false));
        avgangerAskim.add(new FakeBussAPI("21:00", false, false));
        avgangerAskim.add(new FakeBussAPI("22:00", true, false));
        avgangerAskim.add(new FakeBussAPI("23:00", false, false));


        // Tønsberg
        avgangerTønsberg.add(new FakeBussAPI("06:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("06:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("07:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("07:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("08:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("08:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("09:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("09:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("10:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("10:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("11:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("11:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("12:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("12:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("13:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("13:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("14:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("14:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("15:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("15:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("16:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("16:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("17:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("17:30", true, true));
        avgangerTønsberg.add(new FakeBussAPI("18:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("19:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("20:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("21:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("22:00", true, true));
        avgangerTønsberg.add(new FakeBussAPI("23:00", true, true));


        // Fredrikstad
        avgangerFredrikstad.add(new FakeBussAPI("06:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("06:30", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("07:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("07:30", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("08:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("08:30", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("09:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("09:30", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("10:00", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("10:30", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("11:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("11:30", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("12:00", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("12:30", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("13:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("13:30", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("14:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("14:30", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("15:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("15:30", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("16:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("16:30", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("17:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("17:30", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("18:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("19:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("20:00", true, false));
        avgangerFredrikstad.add(new FakeBussAPI("21:00", true, true));
        avgangerFredrikstad.add(new FakeBussAPI("22:00", false, true));
        avgangerFredrikstad.add(new FakeBussAPI("23:00", true, true));


    }




    public List<FakeBussAPI> hentAvganger(String byNavn) {
        switch (byNavn.toLowerCase()) {
            case "halden": return avgangerHalden;
            case "fredrikstad": return avgangerFredrikstad;
            case "moss": return avgangerMoss;
            case "oslo": return avgangerOslo;
            case "sarpsborg": return avgangerSarpsborg;
            case "tønsberg": return avgangerTønsberg;
            case "askim": return avgangerAskim;

            default:
                System.out.println("Fant ikke byen " + byNavn);
                return new ArrayList<>(); // tom liste
        }
    }




}