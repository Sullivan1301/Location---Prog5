class Item {
    constructor(name) {
        this.name = name;
        this.reservations = [];
    }
}

class Reservation {
    constructor(item, startDate, duration) {
        this.item = item;
        this.startDate = startDate;
        this.duration = duration;
        this.endDate = new Date(startDate.getTime() + (duration - 1) * 24 * 60 * 60 * 1000);
    }
}

class ReservationService {
    constructor() {
        this.items = new Map();
    }

    addItem(name) {
        if (this.items.has(name)) {
            throw new Error("ERR_ITEM_ALREADY_EXISTS");
        }
        this.items.set(name, new Item(name));
    }

    reserve(name, startDate, duration) {
        if (duration < 1) {
            throw new Error("ERR_DURATION_TOO_SHORT");
        }

        const item = this.items.get(name);
        if (!item) {
            throw new Error("ERR_ITEM_NOT_FOUND");
        }

        const newReservation = new Reservation(item, startDate, duration);

        for (const reservation of item.reservations) {
            if (this.isOverlapping(newReservation, reservation)) {
                throw new Error("ERR_OVERLAPPING_RESERVATION");
            }
        }

        item.reservations.push(newReservation);
        return newReservation;
    }

    isOverlapping(reservation1, reservation2) {
        return (
            (reservation1.startDate <= reservation2.endDate &&
                reservation1.endDate >= reservation2.startDate)
        );
    }
}

module.exports = { ReservationService, Item, Reservation }; 