package models;

import java.util.Scanner;

public class passord {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //prøver dette først
        System.out.print("Lag et models.passord: ");
        String lagretPassord = input.nextLine();

        System.out.println("Passordet ditt er lagret!\n");

        boolean kjører = true;
        while (kjører) {
            System.out.println("=== MENY ===");
            System.out.println("1. Glemt models.passord");
            System.out.println("2. Avslutt");
            System.out.print("Velg et tall: ");
            int valg = input.nextInt();
            input.nextLine(); // tømme buffer

            switch (valg) {
                case 1:
                    System.out.println("=== GLEMT PASSORD ===");
                    System.out.print("Lag nytt models.passord: ");
                    lagretPassord = input.nextLine();
                    System.out.println("Nytt models.passord er lagret!\n");
                    break;


                case 2:
                    System.out.println("Avslutter programmet...");
                    kjører = false;
                    break;

                default:
                    System.out.println("Ugyldig valg, prøv igjen.\n");
            }
        }

        input.close();
    }
}
