package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    private static List<Livre> bibliotheque = new ArrayList<>();
    private static List<Utilisateur> utilisateurs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Utilisateur utilisateurActuel = null;
    private static final String FICHIER_UTILISATEURS = "utilisateurs.json";

    public static void main(String[] args) {
        initialiserUtilisateurs();  // Ajouter des utilisateurs par défaut
        chargerDonnees();           // Charger les livres et emprunts depuis les fichiers JSON
        int choix;
        do {
            if (utilisateurActuel == null) {
                afficherMenuConnexion();
                choix = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choix) {
                    case 1:
                        seConnecter();
                        break;
                    case 2:
                        creerCompte();
                        break;
                    case 3:
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } else {
                afficherMenuUtilisateur();
                choix = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choix) {
                    case 1:
                        listerLivres();
                        break;
                    case 2:
                        if (utilisateurActuel instanceof Bibliothecaire) {
                            ajouterLivre();
                        } else if (utilisateurActuel instanceof Lecteur) {
                            emprunterLivre();
                        }
                        break;
                    case 3:
                        if (utilisateurActuel instanceof Bibliothecaire) {
                            supprimerLivre();
                        } else if (utilisateurActuel instanceof Lecteur) {
                            retournerLivre();
                        }
                        break;
                    case 4:
                        if (utilisateurActuel instanceof Lecteur) {
                            ((Lecteur) utilisateurActuel).listerEmprunts(); // Liste les livres empruntés
                        }
                        break;
                    case 5:
                        utilisateurActuel = null;
                        System.out.println("Vous vous êtes déconnecté.");
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            }

            sauvegarderDonnees();  // Sauvegarder les livres et emprunts après chaque action
        } while (choix != 3);
    }

    private static void afficherMenuConnexion() {
        System.out.println("\n===== MENU CONNEXION =====");
        System.out.println("1. Se connecter");
        System.out.println("2. Créer un compte");
        System.out.println("3. Quitter");
    }

    private static void afficherMenuUtilisateur() {
        System.out.println("\n===== MENU UTILISATEUR =====");
        System.out.println("1. Lister les livres");
        System.out.println("2. Emprunter un livre / Ajouter un livre");
        System.out.println("3. Retourner un livre / Supprimer un livre");
        System.out.println("4. Lister les livres empruntés");
        System.out.println("5. Se déconnecter");
    }

    private static void seConnecter() {
        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String motDePasse = scanner.nextLine();

        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equals(email) && u.verifierMotDePasse(motDePasse)) {
                utilisateurActuel = u;
                utilisateurActuel.seConnecter();
                if (motDePasse.startsWith("biblio")) {
                    afficherMenuBibliothecaire();
                } else if (motDePasse.startsWith("lect")) {
                    afficherMenuLecteur();
                }
                return;
            }
        }

        System.out.println("Email ou mot de passe incorrect.");
    }


    private static void afficherMenuLecteur() {
        int choix;
        do {
            System.out.println("\n===== MENU LECTEUR =====");
            System.out.println("1. Lister les livres disponibles");
            System.out.println("2. Emprunter un livre");
            System.out.println("3. Retourner un livre");
            System.out.println("4. Lister mes emprunts");
            System.out.println("5. Se déconnecter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    listerLivres();
                    break;
                case 2:
                    emprunterLivre();
                    break;
                case 3:
                    retournerLivre();
                    break;
                case 4:
                    ((Lecteur) utilisateurActuel).listerEmprunts();
                    break;
                case 5:
                    utilisateurActuel = null;
                    System.out.println("Déconnecté avec succès.");
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        } while (choix != 5);
    }
    private static void chargerUtilisateurs() {
        try (FileReader reader = new FileReader(FICHIER_UTILISATEURS)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Utilisateur>>() {}.getType();
            utilisateurs = gson.fromJson(reader, type);
            if (utilisateurs == null) utilisateurs = new ArrayList<>();
        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier d'utilisateurs trouvé. Une nouvelle liste sera créée.");
            utilisateurs = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void afficherMenuBibliothecaire() {
        int choix;
        do {
            System.out.println("\n===== MENU BIBLIOTHÉCAIRE =====");
            System.out.println("1. Lister les livres");
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Supprimer un livre");
            System.out.println("4. Se déconnecter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    listerLivres();
                    break;
                case 2:
                    ajouterLivre();
                    break;
                case 3:
                    supprimerLivre();
                    break;
                case 4:
                    utilisateurActuel = null;
                    System.out.println("Déconnecté avec succès.");
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        } while (choix != 4);
    }

    private static void creerCompte() {
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine();
        System.out.print("Entrez un mot de passe (doit commencer par 'biblio' ou 'lect') : ");
        String motDePasse = scanner.nextLine();

        Utilisateur nouvelUtilisateur = null;

        if (motDePasse.startsWith("biblio")) {
            nouvelUtilisateur = new Bibliothecaire(nom, email, motDePasse);
        } else if (motDePasse.startsWith("lect")) {
            nouvelUtilisateur = new Lecteur(nom, email, motDePasse);
        } else {
            System.out.println("Mot de passe invalide. Il doit commencer par 'biblio' ou 'lect'.");
            return;
        }

        utilisateurs.add(nouvelUtilisateur);
        sauvegarderUtilisateurs(); // Enregistre le nouveau compte
        System.out.println("Compte créé avec succès !");
    }



    // Méthodes d'action des livres pour Lecteur et Bibliothécaire
    private static void listerLivres() {
        if (bibliotheque.isEmpty()) {
            System.out.println("Aucun livre disponible.");
            return;
        }
        System.out.println("\n===== LIVRES DISPONIBLES =====");
        for (Livre livre : bibliotheque) {
            livre.afficherInfos();
        }
    }
    private static void sauvegarderUtilisateurs() {
        try (FileWriter writer = new FileWriter(FICHIER_UTILISATEURS)) {
            Gson gson = new Gson();
            gson.toJson(utilisateurs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ajouterLivre() {
        System.out.print("Titre du livre : ");
        String titre = scanner.nextLine();
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        System.out.print("ISBN : ");
        String ISBN = scanner.nextLine();
        System.out.print("Année de publication : ");
        int anneePub = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne
        Livre livre = new Livre(titre, auteur, ISBN, anneePub);
        bibliotheque.add(livre);
        System.out.println("Livre ajouté avec succès !");
    }

    private static void supprimerLivre() {
        if (bibliotheque.isEmpty()) {
            System.out.println("Aucun livre disponible à supprimer.");
            return;
        }
        listerLivres();
        System.out.print("Choisissez le numéro du livre à supprimer : ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consommer le retour à la ligne

        if (index >= 0 && index < bibliotheque.size()) {
            Livre livre = bibliotheque.remove(index);
            System.out.println("Livre '" + livre.getTitre() + "' supprimé avec succès !");
        } else {
            System.out.println("Numéro invalide.");
        }
    }

    private static void emprunterLivre() {
        listerLivres();
        System.out.print("Choisissez le livre à emprunter : ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consommer le retour à la ligne

        if (index >= 0 && index < bibliotheque.size()) {
            Livre livre = bibliotheque.get(index);
            ((Lecteur) utilisateurActuel).emprunterLivre(livre);
        } else {
            System.out.println("Numéro invalide.");
        }
    }

    private static void retournerLivre() {
        System.out.print("Entrez le titre du livre à retourner : ");
        String titre = scanner.nextLine();
        for (Livre livre : bibliotheque) {
            if (livre.getTitre().equals(titre)) {
                ((Lecteur) utilisateurActuel).retournerLivre(livre);
                return;
            }
        }
        System.out.println("Le livre n'existe pas dans la bibliothèque.");
    }

    // Simulation de l'initialisation des utilisateurs
    private static void initialiserUtilisateurs() {
        utilisateurs.add(new Lecteur("Alice", "alice@example.com", "lect123"));
        utilisateurs.add(new Bibliothecaire("Bob", "bob@example.com", "biblio123"));
    }

    private static void chargerDonnees() {
        // Charger les livres et emprunts depuis les fichiers JSON
    }

    private static void sauvegarderDonnees() {
        // Sauvegarder les livres et emprunts dans les fichiers JSON
    }
}
