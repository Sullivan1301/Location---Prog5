# 📆 Système de Réservation Multilangage

Ce projet implémente un système de réservation d’objets louables (voitures, assiettes, biens immobiliers…) dans **4 langages de programmation** différents :

- 🟦 Java  
- 🟨 TypeScript  
- 🟨 JavaScript  
- 🐍 Python  

Chaque implémentation permet de :
- Ajouter des objets à louer
- Réserver un objet pour une période donnée (en **jours**, avec un **minimum de 1 jour**)
- Empêcher les réservations qui **chevauchent** une période déjà réservée
- Voir les erreurs lorsqu’on tente une réservation invalide

---

## 🔧 Fonctionnalités communes

- ✅ Réservation avec date de début et durée (en jours)
- ⛔ Refus automatique des réservations en conflit
- ❗ Durée minimale d’une réservation : **1 jour**
- 📆 Un objet est indisponible pendant toute la durée de sa réservation
