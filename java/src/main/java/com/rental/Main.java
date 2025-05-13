package com.rental;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Créer le service de réservation
        ReservationService service = new ReservationService();

        // Ajouter quelques objets à l'inventaire
        Item voiture = new Item("V001", "Renault Clio");
        Item appartement = new Item("A001", "Studio Centre-Ville");
        service.ajouterItem(voiture);
        service.ajouterItem(appartement);

        try {
            // Faire une réservation
            LocalDate aujourdhui = LocalDate.now();
            Reservation reservation1 = service.reserver(voiture, aujourdhui, 3);
            System.out.println("Réservation créée : " + reservation1);

            // Essayer de faire une réservation qui chevauche
            try {
                service.reserver(voiture, aujourdhui.plusDays(1), 2);
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur attendue : " + e.getMessage());
            }

            // Faire une réservation valide pour l'appartement
            Reservation reservation2 = service.reserver(appartement, aujourdhui.plusDays(5), 7);
            System.out.println("Réservation créée : " + reservation2);

            // Afficher toutes les réservations
            System.out.println("\nToutes les réservations :");
            service.getReservations().forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}