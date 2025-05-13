package com.rental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalSystem {
    private final List<Item> inventory;
    private final List<Reservation> reservations;

    public RentalSystem() {
        this.inventory = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void ajouterItem(Item item) {
        inventory.add(item);
    }

    public Reservation reserver(Item item, LocalDate dateDebut, int duree) {
        if (!inventory.contains(item)) {
            throw new IllegalArgumentException("ERR_ITEM_NOT_FOUND: L'objet n'existe pas dans l'inventaire");
        }

        Reservation nouvelleReservation = new Reservation(item, dateDebut, duree);

        boolean chevauchement = reservations.stream()
                .filter(r -> r.getItem().equals(item))
                .anyMatch(r -> r.chevauche(nouvelleReservation));

        if (chevauchement) {
            throw new IllegalArgumentException("ERR_OVERLAPPING_RESERVATION: L'objet est déjà réservé pendant cette période");
        }

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