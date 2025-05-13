import { Item } from './Item';
import { Reservation } from './Reservation';

export class ReservationService {
    private items: Map<string, Item> = new Map();
    private reservations: Reservation[] = [];

    addItem(name: string): void {
        if (this.items.has(name)) {
            throw new Error('ERR_ITEM_ALREADY_EXISTS');
        }
        this.items.set(name, new Item(name));
    }

    reserve(itemName: string, startDate: Date, duration: number): void {
        const item = this.items.get(itemName);
        if (!item) {
            throw new Error('ERR_ITEM_NOT_FOUND');
        }

        if (duration < 1) {
            throw new Error('ERR_DURATION_TOO_SHORT');
        }

        const newReservation = new Reservation(item, startDate, duration);

        const hasConflict = this.reservations.some(reservation =>
            reservation.item.name === itemName &&
            reservation.overlaps(newReservation)
        );

        if (hasConflict) {
            throw new Error('ERR_OVERLAPPING_RESERVATION');
        }

        this.reservations.push(newReservation);
    }

    getReservations(itemName: string): Reservation[] {
        return this.reservations.filter(r => r.item.name === itemName);
    }
} 