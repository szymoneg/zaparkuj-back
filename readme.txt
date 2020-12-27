// ------------------------------------------------------------------------------------------------------------------
// *************************		User		*************************************************************
// ------------------------------------------------------------------------------------------------------------------

// ---- Rejestracja użytkownia ---- // method - POST

127.0.0.1:8080/register

{
    "email": "admin@admin.pl",
    "password": "123"
}

// ---- Logowanie użytkownika ---- // method - POST

127.0.0.1:8080/login

{
    "email": "admin@admin.pl",
    "password": "123"
}


// ------------------------------------------------------------------------------------------------------------------
// *************************		Car		*************************************************************
// ------------------------------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------------------------------
// *************************		Parking		*************************************************************
// ------------------------------------------------------------------------------------------------------------------

// ---- Pobranie wszystkich parkingów ---- // method - GET

127.0.0.1:8080/parkings

// ---- Pobranie parkingu o podanym id ---- // method - GET

127.0.0.1:8080/parking/{id}
127.0.0.1:8080/parking/1

// ---- Pobranie koordynatów parkingu o podanym id ---- // method - GET

127.0.0.1:8080/parking/coords/{id}
127.0.0.1:8080/parking/coords/1

// ---- Pobranie wszystkich parkingów w danym mieście ---- // method - GET

127.0.0.1:8080/parkings/city/{city}
127.0.0.1:8080/parkings/city/Tarnów


// ------------------------------------------------------------------------------------------------------------------
// *************************		Place		*************************************************************
// ------------------------------------------------------------------------------------------------------------------

// ---- Pobranie wszystkich miejsc parkingowych na parkingu o id ---- // method - GET

127.0.0.1:8080/places/{id}
127.0.0.1:8080/places/1

// ---- Pobranie ilości wolnych/zajętych miejsc parkingowych na parkingu o id ---- // method - GET

127.0.0.1:8080/places/countPlaces/{id}/{status}
127.0.0.1:8080/places/countPlaces/1/true
127.0.0.1:8080/places/countPlaces/1/false


// ------------------------------------------------------------------------------------------------------------------
// *************************		Reservation		*****************************************************
// ------------------------------------------------------------------------------------------------------------------

// ---- Pobranie wszystkich rezerwacji ---- // method - GET

127.0.0.1:8080/reservations

// ---- Pobranie danych rezerwacji o numerze id ---- // method - GET

127.0.0.1:8080/reservation/{id}
127.0.0.1:8080/reservation/1

// ---- Pobranie danych rezerwacji klienta o nazwie username ---- // method - GET

127.0.0.1:8080/reservation/user/{username}
127.0.0.1:8080/reservation/user/admin

// ---- Pobranie danych aktywnych rezerwacji klienta o nazwie username ---- // method - GET

127.0.0.1:8080/reservation/active/{username}
127.0.0.1:8080/reservation/user/admin
