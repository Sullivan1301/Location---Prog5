package com.rental;

import java.time.LocalDate;

public class Reservation {
    private final Item item;
    private final LocalDate dateDebut;
    private final int duree;

    public Reservation(Item item, LocalDate dateDebut, int duree) {
        if (duree < 1) {
            throw new IllegalArgumentException("ERR_DURATION_TOO_SHORT: La durée doit être d'au moins 1 jour");
        }
        this.item = item;
        this.dateDebut = dateDebut;
        this.duree = duree;
    }

    public Item getItem() {
        return item;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateDebut.plusDays(duree - 1);
    }

    public int getDuree() {
        return duree;
    }

    public boolean chevauche(Reservation autre) {
        return !(this.getDateFin().isBefore(autre.getDateDebut()) || 
                this.getDateDebut().isAfter(autre.getDateFin()));
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "item=" + item +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + getDateFin() +
                ", duree=" + duree +
                '}';
    }
} 