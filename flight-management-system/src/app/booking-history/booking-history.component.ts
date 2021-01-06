import { Component, OnInit } from '@angular/core';
import { FlightRegistrationService } from '../flight-registration.service';
import { BookingsDTO } from '../booking';
import { IATACodeDTO } from '../cityCode';

@Component({
  selector: 'app-booking-history',
  templateUrl: './booking-history.component.html',
  styleUrls: ['./booking-history.component.css']
})
export class BookingHistoryComponent implements OnInit {

  siteURL : string = window.location.href;
  splitted = this.siteURL.split("/", 5); 
  userId : string = this.splitted[4];
  elements:any;
  cityCodeList:any;
  headElements = ['PNR', 'Class', 'Travellers Details (Name, Gender, Age, SeatNo.)', 'Flight Details','Booked Date'];
  
  constructor(private service: FlightRegistrationService) { 

  }

  ngOnInit(): void {

    let resp = this.service.getBookingsByUserId(this.userId);
    
     resp.subscribe((data: BookingsDTO[])=>{
     this.elements=data;
     console.log(this.elements);
     
     this.elements.reverse();
     this.findCityName();
    });

  }

  findCityName(){
    let resp1 = this.service.getIATACodes();
    resp1.subscribe((data: IATACodeDTO[])=>{
     this.cityCodeList = data;
     console.log(this.cityCodeList);
     
     for(let j=0;j<this.elements.length;j++)
     {
        for(let i=0;i<this.cityCodeList.length;i++)
        {
            if(this.elements[j].flightDetails.source==this.cityCodeList[i].cityCode)
            this.elements[j].flightDetails.source=this.cityCodeList[i].cityName+", "+this.cityCodeList[i].cityCode;
            
            if(this.elements[j].flightDetails.destination==this.cityCodeList[i].cityCode)
            this.elements[j].flightDetails.destination=this.cityCodeList[i].cityName+", "+this.cityCodeList[i].cityCode;
        
        }
     }

    });
  }
}
