package view;

import java.util.Scanner;
import controller.*;
import models.*;

public class KonsollView {
    public static void main(String[] args) {
        // Scanner for å lese input fra brukeren
        Scanner sc = new Scanner(System.in);

        // Oppretter kontrollere for bruker- og betalingsfunksjoner
        BrukerController brukerCtrl = new BrukerController();
        BetalingController betalingCtrl = new BetalingController();

        // Variabel for å holde styr på innlogget bruker (null = ingen innlogget)
        Bruker innlogget = null;

        try {
            while (true) { // hovedløkken som kjører til bruker avslutter
                if (innlogget == null) {
                    // ----- HOVEDMENY (når ingen er logget inn) -----
                    System.out.println("\n--- Hovedmeny ---");
                    System.out.println("1) Registrer bruker (auto-rettigheter)");
                    System.out.println("2) Logg inn");
                    System.out.println("0) Avslutt");
                    System.out.print("Velg: ");
                    String valg = sc.nextLine().trim();

                    // --- Valg 1: Registrer ny bruker ---
                    if (valg.equals("1")) {
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
                            continue; // hopper tilbake til menyen
                        }

                        // Fødselsdato er valgfri, men må ha format YYYY-MM-DD hvis oppgitt
                        System.out.print("Fødselsdato (YYYY-MM-DD, valgfri – enter for å hoppe over): ");
                        String fodselsdato = sc.nextLine().trim();
                        if (!fodselsdato.isBlank() && !fodselsdato.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            System.out.println("Ugyldig datoformat. Bruk YYYY-MM-DD.");
                            continue;
                        }

                        // Registrerer ny bruker gjennom kontrolleren
                        Bruker ny = brukerCtrl.registrerBruker(navn, epost, pass, rolle, telefon, fodselsdato);

                        // Viser bekreftelse og rettigheter
                        System.out.println("Opprettet: " + ny.getNavn() + " (" + ny.getRolle() + ")");
                        System.out.println("Tlf: " + ny.getTelefon() + ", Født: " + (ny.getFodselsdato()==null ? "-" : ny.getFodselsdato()));
                        System.out.println("Rettigheter: se=" + ny.getKanSe() + ", endre=" + ny.getKanEndre() + ", slette=" + ny.getKanSlette());

                        // --- Valg 2: Logg inn eksisterende bruker ---
                    } else if (valg.equals("2")) {
                        System.out.print("E-post: ");
                        String e = sc.nextLine();

                        System.out.print("Passord: ");
                        String p = sc.nextLine();

                        // Henter bruker basert på e-post
                        Bruker b = brukerCtrl.hentBruker(e);

                        if (b == null) {
                            System.out.println("Fant ikke bruker.");
                        } else if (!Hashing.checkPassword(p, b.getPassordHash())) {
                            System.out.println("Feil passord.");
                        } else {
                            // Hvis alt stemmer, logg inn
                            innlogget = b;
                            System.out.println("Innlogging OK. Velkommen, " + innlogget.getNavn() + " (" + innlogget.getRolle() + ")");
                        }

                        // --- Valg 0: Avslutt programmet ---
                    } else if (valg.equals("0")) {
                        break; // stopper while-løkken
                    }

                } else {
                    // ----- BRUKERMENY (når en bruker er innlogget) -----
                    System.out.println("\n--- Brukermeny (" + innlogget.getNavn() + " - " + innlogget.getRolle() + ") ---");
                    System.out.println("1) Se egen profil");
                    System.out.println("2) Endre egen profil");
                    if (innlogget.getRolle().equalsIgnoreCase("kunde"))
                        System.out.println("3) Kjøp billett (simulert)");
                    System.out.println("9) Logg ut");
                    System.out.print("Velg: ");
                    String v = sc.nextLine().trim();

                    // Behandler valg i brukermeny
                    switch (v) {
                        // --- Valg 1: Se profil ---
                        case "1":
                            System.out.println("ID: " + innlogget.getBrukerId());
                            System.out.println("Navn: " + innlogget.getNavn());
                            System.out.println("Epost: " + innlogget.getEpost());
                            System.out.println("Rolle: " + innlogget.getRolle());
                            System.out.println("Telefon: " + innlogget.getTelefon());
                            System.out.println("Fødselsdato: " + (innlogget.getFodselsdato()==null ? "-" : innlogget.getFodselsdato()));
                            System.out.println("Kan se=" + innlogget.getKanSe() + ", kan endre=" + innlogget.getKanEndre() + ", kan slette=" + innlogget.getKanSlette());
                            break;

                        // --- Valg 2: Endre profil ---
                        case "2":
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
                                break;
                            }

                            System.out.print("Ny fødselsdato (YYYY-MM-DD, enter = uendret): ");
                            String nfd = sc.nextLine().trim();
                            if (!nfd.isBlank() && !nfd.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                System.out.println("Ugyldig datoformat. Avbryter.");
                                break;
                            }

                            // Oppdaterer brukerens informasjon gjennom kontrolleren
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
                                // Henter oppdatert informasjon fra databasen
                                innlogget = brukerCtrl.hentBrukerById(innlogget.getBrukerId());
                                System.out.println("Profil oppdatert.");
                            } else {
                                System.out.println("Kunne ikke oppdatere profil.");
                            }
                            break;

                        // --- Valg 3: Kjøp billett (bare for kunder) ---
                        case "3":
                            if (innlogget.getRolle().equalsIgnoreCase("kunde")) {
                                betalingCtrl.gjennomforKjop(innlogget);
                            } else {
                                System.out.println("Ikke tilgjengelig for din rolle.");
                            }
                            break;

                        // --- Valg 9: Logg ut ---
                        case "9":
                            innlogget = null;
                            System.out.println("Logget ut.");
                            break;

                        // --- Feilvalg ---
                        default:
                            System.out.println("Ugyldig valg.");
                    }
                }
            }
        } catch (Exception e) {
            // Hvis noe uventet skjer, skriv ut feilmelding
            e.printStackTrace();
        } finally {
            // Lukker scanneren til slutt
            sc.close();
        }
    }
}
