package com.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

class ReservationServiceTest {
    private ReservationService service;
    private Item voiture;
    private LocalDate aujourdhui;

    @BeforeEach
    void setUp() {
        service = new ReservationService();
        voiture = new Item("V001", "Renault Clio");
        service.ajouterItem(voiture);
        aujourdhui = LocalDate.now();
        System.out.println("✅ Configuration initiale terminée");
    }

    @Test
    @DisplayName("Test de réservation d'un objet libre")
    void reserverObjetLibre_DoitReussir() {
        System.out.println("\n📌 Test: Réservation d'un objet libre");
        // Act
        Reservation reservation = service.reserver(voiture, aujourdhui, 3);

        // Assert
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(voiture, reservation.getItem());
        Assertions.assertEquals(aujourdhui, reservation.getDateDebut());
        Assertions.assertEquals(3, reservation.getDuree());
        System.out.println("✅ Test réussi: L'objet a été réservé correctement");
    }

    @Test
    @DisplayName("Test de réservation d'un objet déjà réservé")
    void reserverObjetDejaReserve_DoitEchouer() {
        System.out.println("\n📌 Test: Tentative de réservation d'un objet déjà réservé");
        // Arrange
        service.reserver(voiture, aujourdhui, 3);
        System.out.println("ℹ️ Première réservation créée");

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.reserver(voiture, aujourdhui.plusDays(1), 2));
        Assertions.assertTrue(exception.getMessage().contains("ERR_OVERLAPPING_RESERVATION"));
        System.out.println("✅ Test réussi: La réservation a été correctement rejetée");
    }

    @Test
    @DisplayName("Test de réservation avec durée invalide")
    void reserverAvecDureeInvalide_DoitEchouer() {
        System.out.println("\n📌 Test: Tentative de réservation avec durée invalide");
        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.reserver(voiture, aujourdhui, 0));
        Assertions.assertTrue(exception.getMessage().contains("ERR_DURATION_TOO_SHORT"));
        System.out.println("✅ Test réussi: La réservation avec durée invalide a été rejetée");
    }

    @Test
    @DisplayName("Test de réservation sur une période non chevauchante")
    void reserverPeriodeNonChevauchante_DoitReussir() {
        System.out.println("\n📌 Test: Réservation sur une période non chevauchante");
        // Arrange
        service.reserver(voiture, aujourdhui, 3);
        System.out.println("ℹ️ Première réservation créée");

        // Act
        Reservation reservation = service.reserver(voiture, aujourdhui.plusDays(5), 2);

        // Assert
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(voiture, reservation.getItem());
        Assertions.assertEquals(aujourdhui.plusDays(5), reservation.getDateDebut());
        Assertions.assertEquals(2, reservation.getDuree());
        System.out.println("✅ Test réussi: La réservation non chevauchante a été créée");
    }

    @Test
    @DisplayName("Test de réservation le lendemain d'une fin de réservation")
    void reserverLendemainFinReservation_DoitReussir() {
        System.out.println("\n📌 Test: Réservation le lendemain d'une fin de réservation");
        // Arrange
        service.reserver(voiture, aujourdhui, 3);
        System.out.println("ℹ️ Première réservation créée");

        // Act
        Reservation reservation = service.reserver(voiture, aujourdhui.plusDays(3), 2);

        // Assert
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(voiture, reservation.getItem());
        Assertions.assertEquals(aujourdhui.plusDays(3), reservation.getDateDebut());
        Assertions.assertEquals(2, reservation.getDuree());
        System.out.println("✅ Test réussi: La réservation a été créée le lendemain de la fin de la première");
    }
}