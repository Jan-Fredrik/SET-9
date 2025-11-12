package models;

public class Bestilling {
    private int bestillingId;
    private int brukerId;
    private String rute;
    private String destinasjon;
    private double pris;
    private String status;

    public Bestilling(int bestillingId, int brukerId, String rute, String destinasjon, double pris, String status) {
        this.bestillingId = bestillingId;
        this.brukerId = brukerId;
        this.rute = rute;
        this.destinasjon = destinasjon;
        this.pris = pris;
        this.status = status;
    }

    public int getBestillingId() { return bestillingId; }
    public int getBrukerId() { return brukerId; }
    public String getRute() { return rute; }
    public String getDestinasjon() { return destinasjon; }
    public double getPris() { return pris; }
    public String getStatus() { return status; }
}
