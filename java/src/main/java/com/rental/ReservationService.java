package com.rental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final List<Item> inventory;
    private final List<Reservation> reservations;

    public ReservationService() {
        this.inventory = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void ajouterItem(Item item) {
        inventory.add(item);
    }

    public boolean estDisponible(Item item, LocalDate dateDebut, int duree) {
        if (!inventory.contains(item)) {
            return false;
        }

        Reservation reservationTest = new Reservation(item, dateDebut, duree);
        return reservations.stream()
                .filter(r -> r.getItem().equals(item))
                .noneMatch(r -> r.chevauche(reservationTest));
    }

    public Reservation reserver(Item item, LocalDate dateDebut, int duree) {
        if (!inventory.contains(item)) {
            throw new IllegalArgumentException("ERR_ITEM_NOT_FOUND: L'objet n'existe pas dans l'inventaire");
        }

        if (!estDisponible(item, dateDebut, duree)) {
            throw new IllegalArgumentException(
                    "ERR_OVERLAPPING_RESERVATION: L'objet est déjà réservé pendant cette période");
        }

        Reservation nouvelleReservation = new Reservation(item, dateDebut, duree);
        reservations.add(nouvelleReservation);
        return nouvelleReservation;
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public Optional<Item> trouverItem(String id) {
        return inventory.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }
}