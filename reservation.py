from datetime import date, timedelta
from typing import List

class Reservation:
    def __init__(self, item_name: str, start_date: date, duration: int):
        if duration < 1:
            raise ValueError("ERR_DURATION_TOO_SHORT: La durée doit être d'au moins 1 jour")
        
        self.item_name = item_name
        self.start_date = start_date
        self.duration = duration
        self.end_date = start_date + timedelta(days=duration-1)

    def overlaps_with(self, other: 'Reservation') -> bool:
        return (self.start_date <= other.end_date and 
                other.start_date <= self.end_date)

    def __str__(self) -> str:
        return f"Réservation(objet={self.item_name}, début={self.start_date}, durée={self.duration})"

    def __repr__(self) -> str:
        return self.__str__() 