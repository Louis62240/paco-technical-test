# TravelQuest - Application de réservation de vols

## Présentation

TravelQuest est une application web fullstack permettant de rechercher, consulter et créer des vols.  
Le projet utilise un **backend Spring Reactive** exposant une API REST et un **frontend Thymeleaf** pour l’interface utilisateur.

---

## Backend

- API accessible sur : `http://localhost:8086`
- Endpoints principaux :
  - **GET /flight** : liste tous les vols, avec pagination (`?page=0`) et tri (`?sortBy=price` ou `?sortBy=location`)
  - **GET /flight/{id}** : récupère un vol par son UUID
  - **POST /flight** : crée un nouveau vol. L’UUID est généré automatiquement si non fourni
- CORS activé pour autoriser le frontend sur un autre port (ex. `localhost:8087`)

---

## Frontend

- Application accessible sur : `http://localhost:8087`
- Pages disponibles :
  - Liste des vols avec carte responsive et formatage des prix et dates
  - Formulaire de création de vol accessible sur `/flight/create`

### Formulaire de création

Le formulaire envoie un JSON au backend avec cette structure :

```json
{
  "departure": "2023-12-09T17:00:00",
  "arrival": "2023-12-10T05:00:00",
  "price": 125.5,
  "origin": { "iata": "LAX", "name": "Los Angeles Airport", "country": "US" },
  "destination": { "iata": "PEK", "name": "Beijing Capital Airport", "country": "China" },
  "image": "https://example.com/image.jpg"
}
