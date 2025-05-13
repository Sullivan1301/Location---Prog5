import { ReservationService } from './ReservationService';

describe('ReservationService', () => {
    let service: ReservationService;

    beforeEach(() => {
        service = new ReservationService();
        service.addItem('Voiture1');
    });

    test('Réservation d\'un objet libre doit réussir', () => {
        expect(() => {
            service.reserve('Voiture1', new Date('2025-05-10'), 3);
        }).not.toThrow();
    });

    test('Réservation avec un conflit doit échouer', () => {
        service.reserve('Voiture1', new Date('2025-05-10'), 3);

        expect(() => {
            service.reserve('Voiture1', new Date('2025-05-11'), 2);
        }).toThrow('ERR_OVERLAPPING_RESERVATION');
    });

    test('Réservation avec une durée invalide doit échouer', () => {
        expect(() => {
            service.reserve('Voiture1', new Date('2025-05-10'), 0);
        }).toThrow('ERR_DURATION_TOO_SHORT');
    });

    test('Réservation sur une période totalement libre doit réussir', () => {
        service.reserve('Voiture1', new Date('2025-05-10'), 3);

        expect(() => {
            service.reserve('Voiture1', new Date('2025-05-20'), 2);
        }).not.toThrow();
    });

    test('Réservation qui commence juste après la fin d\'une autre doit réussir', () => {
        service.reserve('Voiture1', new Date('2025-05-10'), 3);

        expect(() => {
            service.reserve('Voiture1', new Date('2025-05-13'), 2);
        }).not.toThrow();
    });

    test('Réservation d\'un objet inexistant doit échouer', () => {
        expect(() => {
            service.reserve('VoitureInexistante', new Date('2025-05-10'), 3);
        }).toThrow('ERR_ITEM_NOT_FOUND');
    });
}); 