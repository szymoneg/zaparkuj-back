### Swagger API
```
http://localhost:8080/swagger-ui.html#/
```
# User Service

### Rejestracja użytkownia - method - POST
możliwe odpowiedzi od servera:  
HttpStatus.OK : JSON z danymi użytkownika  
HttpStatus.BAD_REQUEST : "email exist"
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
możliwe odpowiedzi od servera:  
HttpStatus.OK : JSON JwtResponse.class  
HttpStatus.BAD_REQUEST :
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
HttpStatus.BAD_REQUEST : "Incorrect data", "Username exist", "Something is wrong"
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
odpowiedź  
HttpStatus.OK : ArrayList<CarDTO.class>
```
127.0.0.1:8080/cars/{username}
127.0.0.1:8080/cars/kornel
```

### Dodanie nowego auto do użytkownia - method - POST
możliwe odpowiedzi od servera:  
HttpStatus.CREATED : "created"  
HttpStatus.BAD_REQUEST : "licence plate exist", "bad car mark", "bad car model", "bad licence plate"
```
127.0.0.1:8080/addcar/{username}
127.0.0.1:8080/addcar/kornel
```
```JSON
{
    "mark": "Audi",
    "model": "RS5",
    "licencePlate": "KBR 0000"
}
```

### Usunięcię pojazdu o danym id - method - DELETE
możliwe odpowiedzi od servera:  
HttpStatus.OK : true  
HttpStatus.NOT_FOUND : false  
HttpStatus.NOT_ACCEPTABLE : false
```
127.0.0.1:8080/deletecar/{id}
127.0.0.1:8080/deletecar/1
```

# Parking Service

### Pobranie wszystkich parkingów - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<Parking.class>
```
127.0.0.1:8080/parkings
```
### Pobranie parkingu o podanym id - method - GET
odpowiedź  
HttpStatus.OK : Parking.class
```
127.0.0.1:8080/parking/{id}
127.0.0.1:8080/parking/1
```

### Pobranie wszystkich parkingów w danym mieście - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<Parking.class>
```
127.0.0.1:8080/parkings/city/{city}
127.0.0.1:8080/parkings/city/Tarnów
```

# Sector Service

### Pobranie sektora o danych id - method - GET
odpowiedź  
HttpStatus.OK : SectorDTO.class
```
127.0.0.1:8080/sector/{id}
127.0.0.1:8080/sector/1
```

### Pobranie wszystkich sektorów na parkingu o podanym id
odpowiedź  
HttpStatus.OK : ArrayList<SectorDTO.class>
```
127.0.0.1:8080/sectors/{id}
127.0.0.1:8080/sectors/1
```

# Place Service

### Pobranie miesjca parkingowego o podanym id - method - GET
odpowiedź  
HttpStatus.OK : PlaceDTO.class
```
127.0.0.1:8080/place/{id}
127.0.0.1:8080/place/1
```

### Pobranie wszystkich miejsc parkingowych w sektorze o danym id - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<PlaceDTO.class>
```
127.0.0.1:8080/places/{id}
127.0.0.1:8080/places/1
```
### Pobranie ilości wolnych/zajętych miejsc parkingowych w sektorze o danym id - method - GET
odpowiedź  
HttpStatus.OK : int
```
127.0.0.1:8080/places/countPlaces/{id}/{status}
127.0.0.1:8080/places/countPlaces/1/true
127.0.0.1:8080/places/countPlaces/1/false
```

# Reservation Service

### Pobranie wszystkich rezerwacji - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<ReservationResponse.class>
```
127.0.0.1:8080/reservations
```
### Pobranie danych rezerwacji o numerze id - method - GET
odpowiedź  
HttpStatus.OK : ReservationResponse.class
```
127.0.0.1:8080/reservation/id/{id}
127.0.0.1:8080/reservation/id/1
```
### Pobranie danych rezerwacji klienta o nazwie username - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<ReservationResponse.class>
```
127.0.0.1:8080/reservation/user/{username}
127.0.0.1:8080/reservation/user/kornel
```
### Pobranie danych aktywnych rezerwacji klienta o nazwie username - method - GET
odpowiedź  
HttpStatus.OK : ArrayList<ReservationResponse.class>
```
127.0.0.1:8080/reservation/status/{status}/{username}
127.0.0.1:8080/reservation/status/true/kornel
127.0.0.1:8080/reservation/status/false/kornel
```

### Dodanie Rezerwacji (data jest Stringiem) - method - POST
możliwe odpowiedzi od servera:  
HttpStatus.CREATED : "created"  
HttpStatus.BAD_REQUEST : "not found data", "bad date begin and end reservation",  
	"date begin and end reservation are the same", "bad time reservation"
```
127.0.0.1:8080/reservation/add
```
```JSON
{
    "idPlace": 3,
    "idCar": 1,
    "dateBegin": "2020-01-01T12:00:00.000+00:00",
    "dateEnd":  "2020-01-01T14:00:00.000+00:00"
}
```