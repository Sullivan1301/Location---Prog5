const { ReservationService } = require('./ReservationService');

describe('Service de Réservation', () => {
    let service;

    beforeEach(() => {
        service = new ReservationService();
        service.addItem("Voiture1");
    });

    test('devrait ajouter un objet avec succès', () => {
        service.addItem("Maison1");
        expect(() => service.reserve("Maison1", new Date("2025-05-10"), 3)).not.toThrow();
    });

    test('devrait lever une erreur lors de l\'ajout d\'un objet en double', () => {
        expect(() => service.addItem("Voiture1")).toThrow("ERR_ITEM_ALREADY_EXISTS");
    });

    test('devrait effectuer une réservation valide avec succès', () => {
        const reservation = service.reserve("Voiture1", new Date("2025-05-10"), 3);
        expect(reservation).toBeTruthy();
        expect(reservation.duration).toBe(3);
    });

    test('devrait lever une erreur pour une durée inférieure à 1 jour', () => {
        expect(() => {
            service.reserve("Voiture1", new Date("2025-05-10"), 0);
        }).toThrow("ERR_DURATION_TOO_SHORT");
    });

    test('devrait lever une erreur pour un objet inexistant', () => {
        expect(() => {
            service.reserve("ObjetInexistant", new Date("2025-05-10"), 3);
        }).toThrow("ERR_ITEM_NOT_FOUND");
    });

    test('devrait lever une erreur pour une réservation qui chevauche une période déjà réservée', () => {
        service.reserve("Voiture1", new Date("2025-05-10"), 3);

        expect(() => {
            service.reserve("Voiture1", new Date("2025-05-11"), 2);
        }).toThrow("ERR_OVERLAPPING_RESERVATION");
    });

    test('devrait permettre une réservation juste après la fin d\'une autre', () => {
        service.reserve("Voiture1", new Date("2025-05-10"), 3);

        expect(() => {
            service.reserve("Voiture1", new Date("2025-05-13"), 2);
        }).not.toThrow();
    });

    test('devrait permettre des réservations sans chevauchement', () => {
        service.reserve("Voiture1", new Date("2025-05-10"), 3);

        expect(() => {
            service.reserve("Voiture1", new Date("2025-06-01"), 2);
        }).not.toThrow();
    });
}); 