import { Component, OnInit } from '@angular/core';
import { FlightRegistrationService } from '../flight-registration.service';
import { Router } from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import { Observable} from 'rxjs';
import { stringify } from '@angular/compiler/src/util';
import { FlightDTO } from '../flight';
import  {FlightCompositeKeyDTO} from '../flight-composite-key';
import  {FlightFareDTO} from '../flightFare';
import { FlightSearchDTO } from '../flight-search';

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrls: ['./flight-search.component.css']
})
export class FlightSearchComponent implements OnInit {

  flightDTOList:FlightDTO[];
  siteURL : string = window.location.href;
  
  src;dest;shortDate;berth;noOfTraveller;
  counterBerth:string;
  cityCodeList:any;
  selectedItem = null;
  responseStatus: number;
  duration:string[]=[];
  flightDTO:FlightDTO;
  arrTime:string;depTime:string ;
  time1:number;
  time2:number;
  hourDiff:number;
  minDiff:number;
  split:string[];
  id:string;
  res:string;
  flightId: FlightCompositeKeyDTO=new FlightCompositeKeyDTO("","","","","");
  flight: FlightDTO=new FlightDTO("",this.flightId,"","",null,null,null,null);
  flightFare:FlightFareDTO;
  isShow:boolean=false;
  berthNew:string;
  departureDate;source;destination;arrivalDate;
  additionalSeats:boolean=false;
  fare_add:boolean=false;
  static_src=this.src; static_dest=this.dest;

  constructor(private service: FlightRegistrationService,
              private route:Router,private activeRoute: ActivatedRoute) { 
    let resp = service.getIATACodes();
    resp.subscribe((data)=>{
     this.cityCodeList = data;
     console.log(this.cityCodeList);
    });

    this.src = sessionStorage.getItem('src');
    this.dest = sessionStorage.getItem('dest');
    this.shortDate = sessionStorage.getItem('date');
    this.berth=sessionStorage.getItem('berth');
    this.noOfTraveller=sessionStorage.getItem('travellerNo');
    this.berthNew = this.berth

    this.static_src=this.src; this.static_dest=this.dest;
  }

  ngOnInit(): void {
  
    let resp = this.service.searchFlights(this.src,this.dest,this.shortDate,
      this.berthNew,this.noOfTraveller);
     resp.subscribe((data:FlightDTO[])=>{
       
     this.flightDTOList=data;
     this.findDuration();
    
  });

  
  }

  findDuration(){
    for(let i=0;i<this.flightDTOList.length;i++){

      this.flightDTO = this.flightDTOList[i];
      this.arrTime = this.flightDTO.arrTime;
      this.depTime = this.flightDTO.flightId.depTime;

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
    this.res = this.hourDiff.toString() + 'hrs ' + this.minDiff.toString()+'mins'; 
    
    this.duration.push(this.res);
    
      
    }

  }

   removeColon(s:string) 
   { 
      this.split=s.split(":");
      var str = this.split[0]+this.split[1]   
      
      return Number(str); 
   } 

  searchFlight(){
    sessionStorage.setItem('src',this.src);
    sessionStorage.setItem('dest',this.dest);
    sessionStorage.setItem('date',this.shortDate);
    sessionStorage.setItem('berth',this.berthNew);
    sessionStorage.setItem('travellerNo',this.noOfTraveller.toString());
    window.location.reload();
  }

  redirectTo(uri:string){
    this.route.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.route.navigate([uri]));
 }

 onClick(item) {
  this.selectedItem = item;
  }


  flightInfo(idFlight:string){

  if(this.berth=="Business")
  this.counterBerth = "Economy";
  else
  this.counterBerth = "Business";  
  this.additionalSeats=false;
  this.flightFare = new FlightFareDTO("",null,null,"");
  this.isShow=true;
  this.fare_add=false;
  let resp2 = this.service.getFlightFareById(idFlight);
    
    resp2.subscribe((data : FlightFareDTO)=>{
    this.flightFare=data;
    console.log(this.flightFare);
    this.fare_add=true;
      
  });
  let resp1 = this.service.getFlightById(idFlight);
    
    resp1.subscribe((data : FlightDTO)=>{
    this.flight=data;
    console.log(this.flight);
    this.departureDate = new Date(this.flight.flightId.depDate);
    this.arrivalDate = new Date(this.flight.arrDate);
    this.source = this.getCityName(this.flight.flightId.source);
    this.destination = this.getCityName(this.flight.flightId.destination);  
  });

  
  }

  getCityName(cityCode:string){
    for(let i=0;i<this.cityCodeList.length;i++){
      if(this.cityCodeList[i].cityCode==cityCode)
      return this.cityCodeList[i].cityName
    }
    
  }

  toggle(){
    this.additionalSeats = !this.additionalSeats;
  }

  changeBerth(){
    if(this.berthNew=="Business")
    this.berthNew="Economy";
    else
    this.berthNew="Business";
  }

  checkAvailableSeats(counterBerth){
    if(counterBerth=="Business")
      return ((this.flight.availableBusiness) >= Number(this.noOfTraveller));
    else
    return ((this.flight.availableEconomy) >= Number(this.noOfTraveller));
  }

  book(id){

    sessionStorage.setItem('berth',this.berthNew);
    sessionStorage.setItem('travellerNo',this.noOfTraveller);
    this.route.navigate(['/flight/'+id]);
    
  }
}