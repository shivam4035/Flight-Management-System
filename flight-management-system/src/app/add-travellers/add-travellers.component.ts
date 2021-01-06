import { Component, OnInit,ViewChild,TemplateRef,ElementRef } from '@angular/core';
import { FlightRegistrationService } from '../flight-registration.service';
import { Router } from '@angular/router';
import { FlightDTO } from '../flight';
import { FlightFareDTO } from '../flightFare';
import { TravellerDTO } from '../traveller';
import { BookingsDTO } from '../booking';
import {MatDialog} from '@angular/material/dialog';
import * as jspdf from 'jspdf';
import html2canvas from 'html2canvas'; 
import { FormGroup,FormControl,Validators } from '@angular/forms';
import { FlightCompositeKeyDTO } from '../flight-composite-key';
import { IATACodeDTO } from '../cityCode';

@Component({
  selector: 'app-add-travellers',
  templateUrl: './add-travellers.component.html',
  styleUrls: ['./add-travellers.component.css']
})
export class AddTravellersComponent implements OnInit {

  siteURL : string = window.location.href;
  splitted = this.siteURL.split("/", 5); 
  id : string = this.splitted[4];
  berth;noOfTraveller;
  travellerNo : number;
  flight:FlightDTO=new FlightDTO("",new FlightCompositeKeyDTO("","","","",""),"","",null,null,null,null);
  flightFare:FlightFareDTO = new FlightFareDTO("",null,null,"");
  traveller:TravellerDTO=new TravellerDTO("","",null,null);
  travellersList:TravellerDTO[]=[];
  booking : BookingsDTO=new BookingsDTO("","","",null,null,null,"");
  ticket: BookingsDTO;
  arrTime:string;depTime:string ;
  time1:number;
  time2:number;
  hourDiff:number;
  minDiff:number;
  duration:string;
  split:string[];
  showList:boolean=false;
  mobile:string="";
  addedTraveller:number=0;
  cityCodeList: IATACodeDTO[];
  src:string;dest:string;
  flightId:string;
  bookingDate:Date;
  sliderValue:number=0;
  travellerForm: FormGroup;

  
  constructor(private service: FlightRegistrationService, private route:Router,
    public dialog: MatDialog) { 
      
    this.berth=sessionStorage.getItem('berth');
    this.noOfTraveller=sessionStorage.getItem('travellerNo');
    this.travellerNo= Number(this.noOfTraveller); 
    }
   
@ViewChild('secondDialog', { static: true }) secondDialog: TemplateRef<any>;
@ViewChild('content') content:ElementRef;  

  ngOnInit(): void {

    let resp = this.service.getFlightById(this.id);
    
     resp.subscribe((data : FlightDTO)=>{
     this.flight=data;
     console.log(this.flight);
     this.flightId = this.flight.id.substring(0,5);
     this.findDuration();
     this.flightFareInfo()
    });
  
}

flightFareInfo(){
  let resp2 = this.service.getFlightFareById(this.id);
    
    resp2.subscribe((data : FlightFareDTO)=>{
    this.flightFare=data;
    console.log(this.flightFare);

    this.findCity();
    
  });
}


findCity(){
  let resp1 = this.service.getIATACodes();
    resp1.subscribe((data: IATACodeDTO[])=>{
     this.cityCodeList = data;
     console.log(this.cityCodeList);
     this.src = this.getCityName(this.flight.flightId.source);
     this.dest = this.getCityName(this.flight.flightId.destination);
    });
}

getCityName(cityCode:string){
  for(let i=0;i<this.cityCodeList.length;i++){
    if(this.cityCodeList[i].cityCode==cityCode)
    return this.cityCodeList[i].cityName
  }
  
}

 findDuration(){
  this.arrTime = this.flight.arrTime;
  this.depTime = this.flight.flightId.depTime;

  this.time1 = this.removeColon(this.arrTime); 
  this.time2 = this.removeColon(this.depTime); 
 
// difference between hours 
 this.hourDiff = Math.floor((this.time1)/100) - Math.floor((this.time2)/100) - 1; 
 
// difference between minutes 
this.minDiff = (this.time1 % 100) + (60 - this.time2 % 100); 

if (this.minDiff >= 60) { 
    this.hourDiff++; 
    this.minDiff = this.minDiff - 60; 
} 

this.hourDiff = (this.hourDiff+24)%24;

// convert answer again in string with ':' 
this.duration = this.hourDiff.toString() + 'hrs ' + this.minDiff.toString()+'mins'; 
}

removeColon(s:string) 
   { 
      this.split=s.split(":");
      var str = this.split[0]+this.split[1]   
      
      return Number(str); 
   }
   
   openTravellersList(){
     this.sliderValue=33;
    this.showList=true;
    this.travellerForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.pattern("[a-z A-z]*")]),
      gender: new FormControl('',Validators.required),
      age: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")])
    });
    
   } 

   public hasError = (controlName: string, errorName: string) =>{
    return this.travellerForm.controls[controlName].hasError(errorName);
  }

   book(){
     this.booking.mobile=this.mobile;
     this.booking.numberOfSeats=this.travellerNo;
     this.booking.seatType=this.berth;
     this.booking.listOfTravellers=this.travellersList;

     console.log(this.booking);
     let resp=this.service.addBooking(this.booking,this.id);
     resp.subscribe((data : BookingsDTO)=>{
      this.ticket=data;
      console.log(this.ticket);

      this.bookingDate = new Date(this.ticket.bookedDate);
      
      this.sliderValue=100;
      
    });
   }

   openDialog(){
      this.dialog.open(this.secondDialog,{ disableClose: true });
   }

   saveTraveller(){
     this.travellersList.push(this.traveller);
     this.addedTraveller++;
     if(this.addedTraveller==this.travellerNo)
     this.sliderValue=66;
     this.traveller = new TravellerDTO("","",null,null);
   }

   deleteTraveller(j){
     this.travellersList.splice(j,1);
     this.addedTraveller--;
     this.sliderValue=33;
   }

   arrayOne(n){
      return new Array(n);
   }

   home(){
    this.route.navigate(['/home']);
   }

    formatLabel(value: number) {
      if (value == 0) {
        return 1;
      }
      else if(value == 33){
        return 2;
      }
      else if(value==66){
        return 3;
      }
      else
      return 4;
    }
    

}

