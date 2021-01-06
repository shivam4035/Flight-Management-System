import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { retry,catchError } from 'rxjs/operators';
import { throwError } from 'rxjs'; 
import { ErrorResponse } from './error-response';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FlightRegistrationService {

  constructor(private http: HttpClient,private route : Router) { }


  public addFlight(flight){
    return this.http.post("http://localhost:8080/fms/flight-service/flight",flight)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
    
  }

  public addFlightFare(flightFare,flightId){
    return this.http.post("http://localhost:8080/fms/flight-service/flight/"+flightId+"/fare",flightFare)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
  }

  public updateFlightFare(flightFare,flightId){
    return this.http.put("http://localhost:8080/fms/flight-service/flight/"+flightId+"/fare",flightFare)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
  }

  public getFlightById(flightId){
    return this.http.get("http://localhost:8080/fms/flight-service/flight/"+flightId)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        this.route.navigate(['/admin/flight']);
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
  }

  public getIATACodes(){
    return this.http.get("http://localhost:8080/fms/flight-service/iatacode");
    
  }

  public addIATACodes(iataCode){
    return this.http.post("http://localhost:8080/fms/flight-service/iatacode",iataCode);

  }

  public updateFlight(flight,flightId){
    return this.http.put("http://localhost:8080/fms/flight-service/flight/"+flightId+"/0",flight)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
    
  }

  public searchFlights(src,dest,date,berth,noOfTraveller){
    return this.http.get("http://localhost:8080/fms/flight-service/flight/"+src+"/"+dest+"/"+date
    +'/'+berth+'/'+noOfTraveller)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );

  }

  public getFlightFareById(flightId){
    return this.http.get("http://localhost:8080/fms/flight-service/flight/"+flightId+"/fare")
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
  }

  public addBooking(booking,flightId){
    return this.http.post("http://localhost:8080/fms/user-service/flight/"+flightId+
    "/booking",booking)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        
        return throwError(errorResponse);
      })
    );
  }

  public getBookingsByUserId(mobileNo){
    return this.http.get("http://localhost:8080/fms/user-service/bookings/mobile/"+mobileNo)
    .pipe(
      retry(1),
      catchError((error: HttpErrorResponse) => {
        let errorResponse: ErrorResponse;
        errorResponse = error.error;
        
        alert(errorResponse.message);
        this.route.navigate(['/home']);
        return throwError(errorResponse);
      })
    );
  }


}
