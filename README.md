# Rental Reservation System 🏠🚗

Un système de réservation d'objets louables (voitures, immobilier, etc.) avec des règles de validation et implémenté en 4 langages.

## Règles Métier
- Réservation minimale : 1 jour.
- Un objet ne peut pas être réservé pendant une période déjà occupée.
- Erreurs à gérer :
  - `ERR_DURATION_TOO_SHORT` : Durée < 1 jour.
  - `ERR_OVERLAPPING_RESERVATION` : Dates déjà réservées.
  - `ERR_ITEM_NOT_FOUND` : Objet inexistant.

## Branches Disponibles
- `main` : Documentation et structure de base.

## Comment Contribuer
1. Choisissez une branche : `git checkout nom-branche`.
2. Suivez les instructions du dossier de la branche.