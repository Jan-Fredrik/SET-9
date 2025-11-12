package models;

import java.time.LocalDate;

public class Bruker {
    private int brukerId;
    private String navn;
    private String epost;
    private String passordHash;
    private String rolle;
    private String telefon;
    private LocalDate fodselsdato;
    private boolean kanSe;
    private boolean kanEndre;
    private boolean kanSlette;

    public Bruker(int brukerId, String navn, String epost, String passordHash, String rolle,
                  String telefon, LocalDate fodselsdato,
                  boolean kanSe, boolean kanEndre, boolean kanSlette) {
        this.brukerId = brukerId;
        this.navn = navn;
        this.epost = epost;
        this.passordHash = passordHash;
        this.rolle = rolle;
        this.telefon = telefon;
        this.fodselsdato = fodselsdato;
        this.kanSe = kanSe;
        this.kanEndre = kanEndre;
        this.kanSlette = kanSlette;
    }

    public int getBrukerId() { return brukerId; }
    public String getNavn() { return navn; }
    public String getEpost() { return epost; }
    public String getPassordHash() { return passordHash; }
    public String getRolle() { return rolle; }
    public String getTelefon() { return telefon; }
    public LocalDate getFodselsdato() { return fodselsdato; }
    public boolean getKanSe() { return kanSe; }
    public boolean getKanEndre() { return kanEndre; }
    public boolean getKanSlette() { return kanSlette; }
}
