# TravelQuest – Application de réservation de vols ✈️

TravelQuest est une application fullstack permettant de rechercher, trier, consulter et créer des vols.  
Elle est composée de deux modules :

- **Backend** → API REST réactive (Spring WebFlux)
- **Frontend** → Interface utilisateur avec Thymeleaf

---

## 🚀 Lancer le projet

### 1. Backend
```bash
cd technical-test-api
mvn spring-boot:run
```
API disponible sur : http://localhost:8086

### Backend – Endpoints
| Méthode | URL            | Description                                      |
| ------- | -------------- | ------------------------------------------------ |
| GET     | `/flight`      | Liste paginée + tri (`?page=0`, `?sortBy=price`) |
| GET     | `/flight/{id}` | Récupère un vol par UUID                         |
| POST    | `/flight`      | Crée un vol (UUID généré automatiquement)        |

### Exemple JSON pour créer un vol
```bash
{
  "departure": "2023-12-09T17:00:00",
  "arrival": "2023-12-10T05:00:00",
  "price": 125.5,
  "origin": { "iata": "LAX", "name": "Los Angeles Airport", "country": "US" },
  "destination": { "iata": "PEK", "name": "Beijing Capital Airport", "country": "China" },
  "image": "https://example.com/image.jpg"
}
```

### 2. Frontend
```bash
cd technical-test-renderer
mvn spring-boot:run
```
Application accessible sur : http://localhost:8087


#### Fonctionnalités principales :
- Liste des vols (responsive grid)
- Tri : date, prix, localisation
- Pagination
- Formatage automatique des prix et dates
- Page de détails d’un vol
- Formulaire de création d’un vol
- UI modernisée (CSS custom)

#### Versions utilisées

Les deux modules partagent les mêmes versions :
- Java : 17
- Spring Boot : 3.1.2
- Lombok : 1.18.28
- MapStruct : 1.5.5.Final
- Lombok MapStruct Binding : 0.2.0
- Reactor Test : 3.4.25
- Testcontainers MongoDB : 1.18.3

### Structure du projet
```bash
/
├── technical-test-api/         # Backend WebFlux + MongoDB Reactive
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
│
└── technical-test-renderer/    # Frontend Thymeleaf
    ├── src/main/java
    ├── src/main/resources
    └── pom.xml
```