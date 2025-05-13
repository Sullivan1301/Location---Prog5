import unittest
from datetime import date
from reservation_service import ReservationService

class TestReservationService(unittest.TestCase):
    def setUp(self):
        """Initialisation des tests avec deux objets disponibles."""
        self.service = ReservationService()
        self.service.add_item("Voiture1")
        self.service.add_item("Maison1")

    def test_successful_reservation(self):
        """Test d'une réservation réussie d'un objet disponible."""
        reservation = self.service.reserve("Voiture1", date(2025, 5, 10), 3)
        self.assertEqual(reservation.item_name, "Voiture1")
        self.assertEqual(reservation.start_date, date(2025, 5, 10))
        self.assertEqual(reservation.duration, 3)

    def test_overlapping_reservation(self):
        """Test du rejet d'une réservation qui chevauche une période déjà réservée."""
        self.service.reserve("Voiture1", date(2025, 5, 10), 3)
        
        with self.assertRaises(ValueError) as context:
            self.service.reserve("Voiture1", date(2025, 5, 11), 2)
        
        self.assertIn("ERR_OVERLAPPING_RESERVATION", str(context.exception))

    def test_duration_too_short(self):
        """Test du rejet d'une réservation avec une durée inférieure à 1 jour."""
        with self.assertRaises(ValueError) as context:
            self.service.reserve("Voiture1", date(2025, 5, 10), 0)
        
        self.assertIn("ERR_DURATION_TOO_SHORT", str(context.exception))

    def test_non_overlapping_reservation(self):
        """Test d'une réservation non chevauchante qui doit être acceptée."""
        self.service.reserve("Voiture1", date(2025, 5, 10), 3)
        reservation = self.service.reserve("Voiture1", date(2025, 5, 13), 2)
        self.assertEqual(reservation.start_date, date(2025, 5, 13))
        self.assertEqual(reservation.duration, 2)

    def test_immediate_next_reservation(self):
        """Test d'une réservation commençant immédiatement après une réservation précédente."""
        self.service.reserve("Voiture1", date(2025, 5, 10), 3)
        reservation = self.service.reserve("Voiture1", date(2025, 5, 13), 2)
        self.assertEqual(reservation.start_date, date(2025, 5, 13))
        self.assertEqual(reservation.duration, 2)

    def test_nonexistent_item(self):
        """Test de la réservation d'un objet inexistant qui doit lever une erreur."""
        with self.assertRaises(ValueError) as context:
            self.service.reserve("ObjetInexistant", date(2025, 5, 10), 3)
        
        self.assertIn("ERR_ITEM_NOT_FOUND", str(context.exception))

if __name__ == "__main__":
    unittest.main() 