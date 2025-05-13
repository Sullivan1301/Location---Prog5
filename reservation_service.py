from datetime import date
from typing import Dict, List
from item import Item
from reservation import Reservation

class ReservationService:
    def __init__(self):
        self.items: Dict[str, Item] = {}
        self.reservations: List[Reservation] = []

    def add_item(self, name: str) -> None:
        """Ajoute un nouvel objet louable au service."""
        if name in self.items:
            raise ValueError(f"L'objet {name} existe déjà")
        self.items[name] = Item(name)

    def reserve(self, item_name: str, start_date: date, duration: int) -> Reservation:
        """Crée une nouvelle réservation pour un objet."""
        if item_name not in self.items:
            raise ValueError(f"ERR_ITEM_NOT_FOUND: L'objet {item_name} n'existe pas")

        new_reservation = Reservation(item_name, start_date, duration)

        for existing_reservation in self.reservations:
            if (existing_reservation.item_name == item_name and 
                new_reservation.overlaps_with(existing_reservation)):
                raise ValueError("ERR_OVERLAPPING_RESERVATION: L'objet est déjà réservé pour ces dates")

        self.reservations.append(new_reservation)
        return new_reservation

    def get_item_reservations(self, item_name: str) -> List[Reservation]:
        """Récupère toutes les réservations pour un objet spécifique."""
        return [r for r in self.reservations if r.item_name == item_name] 