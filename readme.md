# User Service
### Rejestracja użytkownia - method - POST
```
127.0.0.1:8080/register
```
```json
{
    "email": "admin@admin.pl",
    "password": "123"
}
```

### Logowanie użytkownika - method - POST
```
127.0.0.1:8080/login
```
```json
{
    "email": "admin@admin.pl",
    "password": "123"
}
```

### Uaktualnienie danych na podstawie adresu email - method - POST
możliwe odpowiedzi od servera:  
HttpStatus.OK : true  
HttpStatus.BAD_REQUEST: "Incorrect data", "Username exist", "Something is wrong"
```
127.0.0.1:8080/user/changedata
```
```json
{
    "username": "admin",
    "email": "admin@admin.pl",
    "firstname": "adam",
    "lastname": "adminski"
}
```

# Car Service

### Pobranie wszystkich pojazdów użytkownika - method - GET
```
127.0.0.1:8080/cars/{username}
127.0.0.1:8080/cars/kornel
```

# Parking Service

### Pobranie wszystkich parkingów - method - GET
```
127.0.0.1:8080/parkings
```
### Pobranie parkingu o podanym id - method - GET
```
127.0.0.1:8080/parking/{id}
127.0.0.1:8080/parking/1
```

### Pobranie wszystkich parkingów w danym mieście - method - GET
```
127.0.0.1:8080/parkings/city/{city}
127.0.0.1:8080/parkings/city/Tarnów
```

# Place

### Pobranie wszystkich miejsc parkingowych na parkingu o id - method - GET
```
127.0.0.1:8080/places/{id}
127.0.0.1:8080/places/1
```
### Pobranie ilości wolnych/zajętych miejsc parkingowych na parkingu o id - method - GET
```
127.0.0.1:8080/places/countPlaces/{id}/{status}
127.0.0.1:8080/places/countPlaces/1/true
127.0.0.1:8080/places/countPlaces/1/false
```

# Reservation

### Pobranie wszystkich rezerwacji - method - GET
```
127.0.0.1:8080/reservations
```
### Pobranie danych rezerwacji o numerze id - method - GET
```
127.0.0.1:8080/reservation/{id}
127.0.0.1:8080/reservation/1
```
### Pobranie danych rezerwacji klienta o nazwie username - method - GET
```
127.0.0.1:8080/reservation/user/{username}
127.0.0.1:8080/reservation/user/admin
```
### Pobranie danych aktywnych rezerwacji klienta o nazwie username - method - GET
```
127.0.0.1:8080/reservation/active/{username}
127.0.0.1:8080/reservation/user/admin
```