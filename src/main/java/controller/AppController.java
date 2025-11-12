package controller;

import models.*;
import repository.FakeBussAPI;
import view.KonsollView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppController {

    private final Scanner sc = new Scanner(System.in);
    private final BrukerController brukerCtrl = new BrukerController();
    private final BetalingController betalingCtrl = new BetalingController();
    private Bruker innlogget = null; // null = ingen innlogget
    private final RouteController routeCtrl = new RouteController(new FakeBussAPI(), new KonsollView());

    // EN kall-funksjon som starter hele greia
    public void run() {
        try {
            while (true) { // hovedløkken
                if (innlogget == null) {
                    visHovedmeny();
                } else {
                    visBrukermeny();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    // =====================
    //       MENYER
    // =====================

    private void visHovedmeny() throws SQLException {
        System.out.println("\n--- Hovedmeny ---");
        System.out.println("1) Registrer bruker (auto-rettigheter)");
        System.out.println("2) Logg inn");
        System.out.println("0) Avslutt");
        System.out.print("Velg: ");
        String valg = sc.nextLine().trim();

        switch (valg) {
            case "1":
                registrerBrukerFlow();
                break;
            case "2":
                loggInnFlow();
                break;
            case "0":
                System.out.println("Avslutter...");
                System.exit(0);
                break;
            default:
                System.out.println("Ugyldig valg.");
        }
    }

    private void visBrukermeny() throws SQLException {
        System.out.println("\n--- Brukermeny (" + innlogget.getNavn() + " - " + innlogget.getRolle() + ") ---");

        // Menyvalg i ny rekkefølge
        if (innlogget.getRolle().equalsIgnoreCase("kunde")) {
            System.out.println("1) Kjøp billett");
            System.out.println("2) Se billetter");
        }
        System.out.println("3) Se egen profil");
        System.out.println("4) Endre egen profil");
        System.out.println("5) Endre preferanser");
        System.out.println("9) Logg ut");

        System.out.print("Velg: ");
        String valg = sc.nextLine().trim();

        switch (valg) {
            case "1":
                if (innlogget.getRolle().equalsIgnoreCase("kunde")) {
                    routeCtrl.velgReise(sc);
                } else {
                    System.out.println("Ikke tilgjengelig for din rolle.");
                }
                break;

            case "2":
                if (innlogget.getRolle().equalsIgnoreCase("kunde")) {
                    LocalDate valgtDato = null;

                    while (valgtDato == null) {
                        System.out.println("\nSe billetter kjøpt i dag — skriv 'ja'.");
                        System.out.println("Se billetter fra en annen dag — skriv dato på formatet YYYY-MM-DD.");
                        System.out.print("Ditt valg: ");
                        String svar = sc.nextLine().trim().toLowerCase();

                        if (svar.equals("ja")) {
                            valgtDato = LocalDate.now();
                        } else {
                            try {
                                valgtDato = LocalDate.parse(svar);
                            } catch (Exception e) {
                                System.out.println("Feil format. Bruk formatet YYYY-MM-DD og prøv igjen.\n");
                            }
                        }
                    }

                    System.out.println("\n--- BILLETTER KJØPT DEN " + valgtDato + " ---");
                    List<Ticket> billetter = Ticket.getTicketsByDate(valgtDato);

                    if (billetter.isEmpty()) {
                        System.out.println("Her var det tomt! Ingen billetter på denne dagen.");
                    } else {
                        for (Ticket ticket : billetter) {
                            System.out.println(ticket);
                        }
                    }



                } else {
                    System.out.println("Denne funksjonen er kun for kunder.");
                }
                break;

            case "3":
                visProfil();
                break;

            case "4":
                endreProfilFlow();
                break;

            case "5":
                if (innlogget.getRolle().equalsIgnoreCase("kunde")) {
                    System.out.println("\n--- Endre preferanser ---\n");
                    FiltreringInnstillingHandler prefHandler = new FiltreringInnstillingHandler("preferanser.properties");
                    prefHandler.endrePreferanser();
                } else {
                    System.out.println("Bare kunder kan endre preferanser.");
                }
                break;

            case "9":
                innlogget = null;
                System.out.println("Logget ut.");
                break;

            default:
                System.out.println("Ugyldig valg.");
        }
    }

    // =====================
    //   FLOW-METODER
    // =====================

    private void registrerBrukerFlow() throws SQLException {
        System.out.print("Navn: ");
        String navn = sc.nextLine();

        System.out.print("E-post: ");
        String epost = sc.nextLine();

        System.out.print("Passord: ");
        String pass = sc.nextLine();

        System.out.print("Rolle (kunde/admin/utvikler): ");
        String rolle = sc.nextLine();

        // Validerer telefonnummer (kun siffer, 8–15 tegn)
        System.out.print("Telefon (kun siffer, 8-15): ");
        String telefon = sc.nextLine();
        if (!telefon.matches("\\d{8,15}")) {
            System.out.println("Ugyldig telefon. Avbryter registrering.");
            return;
        }

        // Fødselsdato er valgfri, men må ha format YYYY-MM-DD hvis oppgitt
        System.out.print("Fødselsdato (YYYY-MM-DD, valgfri – enter for å hoppe over): ");
        String fodselsdato = sc.nextLine().trim();
        if (!fodselsdato.isBlank() && !fodselsdato.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Ugyldig datoformat. Bruk YYYY-MM-DD.");
            return;
        }

        if (rolle.equalsIgnoreCase("kunde")) {
            System.out.println("--- Sett dine preferanser ---");
            FiltreringInnstillingHandler prefHandler = new FiltreringInnstillingHandler("preferanser.properties");
            prefHandler.endrePreferanser();
        }

        Bruker ny = brukerCtrl.registrerBruker(navn, epost, pass, rolle, telefon, fodselsdato);

        System.out.println("Opprettet: " + ny.getNavn() + " (" + ny.getRolle() + ")");
        System.out.println("Tlf: " + ny.getTelefon() + ", Født: " + (ny.getFodselsdato() == null ? "-" : ny.getFodselsdato()));
        System.out.println("Rettigheter: se=" + ny.getKanSe() + ", endre=" + ny.getKanEndre() + ", slette=" + ny.getKanSlette());
    }

    private void loggInnFlow() throws SQLException {
        System.out.print("E-post: ");
        String e = sc.nextLine();

        System.out.print("Passord: ");
        String p = sc.nextLine();

        Bruker b = brukerCtrl.hentBruker(e);

        if (b == null) {
            System.out.println("Fant ikke bruker.");
        } else if (!Hashing.checkPassword(p, b.getPassordHash())) {
            System.out.println("Feil passord.");
        } else {
            innlogget = b;
            System.out.println("Innlogging OK. Velkommen, " + innlogget.getNavn() + " (" + innlogget.getRolle() + ")");
        }
    }

    private void visProfil() {
        System.out.println("ID: " + innlogget.getBrukerId());
        System.out.println("Navn: " + innlogget.getNavn());
        System.out.println("Epost: " + innlogget.getEpost());
        System.out.println("Rolle: " + innlogget.getRolle());
        System.out.println("Telefon: " + innlogget.getTelefon());
        System.out.println("Fødselsdato: " + (innlogget.getFodselsdato() == null ? "-" : innlogget.getFodselsdato()));
        System.out.println("Kan se=" + innlogget.getKanSe() + ", kan endre=" + innlogget.getKanEndre() + ", kan slette=" + innlogget.getKanSlette());
    }

    private void endreProfilFlow() throws SQLException {
        System.out.print("Nytt navn (enter = uendret): ");
        String nn = sc.nextLine();

        System.out.print("Ny epost (enter = uendret): ");
        String ne = sc.nextLine();

        System.out.print("Nytt passord (enter = uendret): ");
        String np = sc.nextLine();

        System.out.print("Ny telefon (enter = uendret): ");
        String nt = sc.nextLine();
        if (!nt.isBlank() && !nt.matches("\\d{8,15}")) {
            System.out.println("Ugyldig telefon. Avbryter.");
            return;
        }

        System.out.print("Ny fødselsdato (YYYY-MM-DD, enter = uendret): ");
        String nfd = sc.nextLine().trim();
        if (!nfd.isBlank() && !nfd.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Ugyldig datoformat. Avbryter.");
            return;
        }

        boolean ok = brukerCtrl.oppdaterBruker(
                innlogget,
                innlogget.getBrukerId(),
                nn.isBlank() ? null : nn,
                ne.isBlank() ? null : ne,
                np.isBlank() ? null : np,
                nt.isBlank() ? null : nt,
                nfd.isBlank() ? null : nfd
        );

        if (ok) {
            innlogget = brukerCtrl.hentBrukerById(innlogget.getBrukerId());
            System.out.println("Profil oppdatert.");
        } else {
            System.out.println("Kunne ikke oppdatere profil.");
        }

        System.out.print("Vil du endre dine preferanser også? (ja/nei): ");
        String svar = sc.nextLine().trim().toLowerCase();

        if (svar.equals("ja")) {
            FiltreringInnstillingHandler prefHandler = new FiltreringInnstillingHandler("preferanser.properties");
            prefHandler.endrePreferanser();
        } else {
            System.out.println("Preferansene ble ikke endret.\n");
        }
    }
}
