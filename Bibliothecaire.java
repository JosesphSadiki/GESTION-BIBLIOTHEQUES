package org.example;

public class Bibliothecaire extends Utilisateur {

    public Bibliothecaire(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

    @Override
    public void seConnecter() {
        System.out.println(nom + " (Bibliothécaire) s'est connecté.");
    }

    // Méthodes spécifiques au bibliothécaire
    public void ajouterLivre(Livre livre) {
        System.out.println("Le livre '" + livre.getTitre() + "' a été ajouté.");
    }

    public void supprimerLivre(Livre livre) {
        System.out.println("Le livre '" + livre.getTitre() + "' a été supprimé.");
    }
}
