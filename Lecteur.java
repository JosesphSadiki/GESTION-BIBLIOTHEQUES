package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lecteur extends Utilisateur {

    private List<Emprunt> emprunts = new ArrayList<>(); // Liste des livres empruntés

    public Lecteur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }

    @Override
    public void seConnecter() {
        System.out.println(nom + " (Lecteur) s'est connecté.");
    }

    // Méthode pour emprunter un livre
    public void emprunterLivre(Livre livre) {
        if (emprunts.size() >= 3) {
            System.out.println("Vous ne pouvez pas emprunter plus de 3 livres.");
        } else {
            Emprunt emprunt = new Emprunt(livre, this, new Date(), null);
            emprunts.add(emprunt);
            System.out.println(nom + " a emprunté le livre : " + livre.getTitre());
        }
    }

    // Méthode pour retourner un livre
    public void retournerLivre(Livre livre) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getLivre().equals(livre)) {
                emprunts.remove(emprunt);
                System.out.println(nom + " a retourné le livre : " + livre.getTitre());
                return;
            }
        }
        System.out.println("Vous n'avez pas emprunté ce livre.");
    }

    // Méthode pour lister les livres empruntés
    public void listerEmprunts() {
        if (emprunts.isEmpty()) {
            System.out.println(nom + " n'a pas encore emprunté de livres.");
            return;
        }
        System.out.println("\n===== LIVRES EMPRUNTÉS PAR " + nom + " =====");
        for (Emprunt emprunt : emprunts) {
            System.out.println("- " + emprunt.getLivre().getTitre());
        }
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }
}
