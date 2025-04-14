package org.example;

public class Livre {
    private String titre;
    private String auteur;
    private String ISBN;
    private int anneePub;

    public Livre(String titre, String auteur, String ISBN, int anneePub) {
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.anneePub = anneePub;
    }

    public void afficherInfos() {
        System.out.println("Titre: " + titre + ", Auteur: " + auteur + ", ISBN: " + ISBN + ", Année: " + anneePub);
    }
    public String getTitre() {

        return titre;
    }
}
