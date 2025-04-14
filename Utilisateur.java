package org.example;

public class Utilisateur {
    protected String nom;
    protected String email;
    protected String motDePasse; // Ajout du mot de passe

    public Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public void seConnecter() {
        System.out.println(nom + " s'est connecté.");
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    void chargerUtilisateurs() // On charge les comptes depuis le JSON
    {

    }

    void chargerDonnees() {

    }

    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }
}
