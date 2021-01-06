import { Component, OnInit } from '@angular/core';
import { FlightFareDTO } from '../flightFare';
import {formatDate} from '@angular/common';
import { FlightRegistrationService } from '../flight-registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-flight-fare-add',
  templateUrl: './flight-fare-add.component.html',
  styleUrls: ['./flight-fare-add.component.css']
})
export class FlightFareAddComponent implements OnInit {

  siteURL : string = window.location.href;

  
  id = sessionStorage.getItem('flightId_fare');
 
  flightFare:FlightFareDTO = new FlightFareDTO(this.id,null,null,"")


  constructor(private service: FlightRegistrationService,private route:Router){
  }
 

  ngOnInit(): void {

    let resp = this.service.getFlightById(this.id);
     resp.subscribe((data : FlightFareDTO)=>{
     console.log(data);
     
    });

  }

  addFlightFare(){
    this.flightFare.date=formatDate(new Date(), 'yyyy-MM-dd', 'en');
    let resp = this.service.addFlightFare(this.flightFare,this.id);
     resp.subscribe((data : FlightFareDTO)=>{
     console.log(data);
     
    });
    this.route.navigate(['/admin/flight']);
    alert("Flight and Fare for flightId "+this.id+" successfully added");
  }

}
