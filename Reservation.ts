import { Item } from './Item';

export class Reservation {
    constructor(
        public item: Item,
        public startDate: Date,
        public duration: number
    ) {}

    get endDate(): Date {
        const endDate = new Date(this.startDate);
        endDate.setDate(endDate.getDate() + this.duration);
        return endDate;
    }

    overlaps(other: Reservation): boolean {
        return (
            (this.startDate <= other.endDate && this.endDate >= other.startDate)
        );
    }
}