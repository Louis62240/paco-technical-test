# Test Technique FullStack - TravelQuest

**Avancement du projet :**  
- Backend : 🟩🟩🟩🟩🟩 100%  
- Frontend : ⬜⬜⬜⬜⬜ 0%  
- Exercice global : 🟩🟩🟩⬜⬜ 50%

---

## Présentation

Salut ! Je travaille sur le test technique TravelQuest proposé par Auchan.  
L'objectif est de construire une application de réservation de voyages avec une partie **backend Spring Reactive / MongoDB** et une partie **frontend Thymeleaf**.

Dans ce projet, les utilisateurs peuvent rechercher, trier et réserver des vols, tout en bénéficiant d’une expérience fluide et claire.

---

## Backend ✅ (100%)

J’ai travaillé sur toutes les fonctionnalités backend du projet.  

### Partie 1 : Chasse aux Bugs
- [x] Corrigé le problème qui empêchait le lancement de l’API
- [x] Corrigé le problème de récupération des vols

### Partie 2 : Évolutions Éclair
- [x] Création de vols → j’ai ajouté un endpoint POST `/flight` pour insérer des vols en base
- [x] Filtres → j’ai ajouté la possibilité de trier les résultats par **prix** ou **localisation**
- [x] Bonus : Pagination → j’ai limité le nombre de résultats à 6 par page pour optimiser les performances

---

## Frontend ⬜ (0%)

Je n’ai pas encore travaillé sur le frontend, voici ce qu’il reste à faire :  

### Partie 1 : Chasse aux Bugs
- [ ] Corriger l’affichage des tarifs
- [ ] Optimiser le chargement des images

### Partie 2 : Évolutions Éclair
- [ ] Page d’administration pour créer des vols
- [ ] Filtrage des résultats de recherche (tri par prix, localisation, dates)
- [ ] Filtrage avancé (conserver les filtres après sélection)
- [ ] Bonus : Pagination côté frontend

---

## Notes sur le backend

- L’API `/flight` supporte maintenant :
  - **Tri** par prix ou localisation (`?sortBy=price` ou `?sortBy=location`)
  - **Pagination** (6 résultats par page) avec `?page=0`, `?page=1`, etc.
- Tout le backend est fonctionnel et prêt à être connecté au frontend.

---
