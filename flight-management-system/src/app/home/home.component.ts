import { Component, OnInit } from '@angular/core';
import {FlightRegistrationService} from  '../flight-registration.service'
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { FormGroup,FormControl,Validators } from '@angular/forms';
import {FlightSearchDTO} from '../flight-search';
import {LocationStrategy} from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [DatePipe]
})
export class HomeComponent implements OnInit {

  siteURL : string = window.location.href;
  splitted = this.siteURL.split("/", 4); 
  last : string = this.splitted[3];

  cityCodeList: any;

  src: string = "";
  dest: string = "";
  date : Date;
  shortDate:string;
  berth: string;
  berthsName: string[] = ['Business', 'Economy'];
  noOfTraveller: number;
  userId:string;
  home:boolean=true;
  searchForm: FormGroup;
  nameControl = new FormControl('');
  flightSearch:FlightSearchDTO=new FlightSearchDTO("","","","",null);

  constructor(private service:FlightRegistrationService, private datepipe: DatePipe,
     private route: Router,private locationStrategy: LocationStrategy) { 
    let resp = service.getIATACodes();
    resp.subscribe((data)=>{
     this.cityCodeList = data;
     console.log(this.cityCodeList);
    });

    history.pushState(null, null, location.href);
    this.locationStrategy.onPopState(() => {
      history.pushState(null, null, location.href);
    });

    
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      source: new FormControl('', [Validators.required]),
      destination: new FormControl('',[Validators.required]),
      date: new FormControl('', [Validators.required]),
      berth: new FormControl('', [Validators.required]),
      travellerNo: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")])

    });
  }

  public hasError = (controlName: string, errorName: string) =>{
    return this.searchForm.controls[controlName].hasError(errorName);
  }


  searchFlight(){
    this.shortDate =this.datepipe.transform(this.date, 'yyyy-MM-dd');
    

    sessionStorage.setItem('src',this.src);
    sessionStorage.setItem('dest',this.dest);
    sessionStorage.setItem('date',this.shortDate);
    sessionStorage.setItem('berth',this.berth);
    sessionStorage.setItem('travellerNo',this.noOfTraveller.toString());

    this.route.navigate(['/flight/search']);
    
  }


  goToBookingHistory(){
    sessionStorage.setItem('userId',this.userId);
    this.route.navigate(['/bookings/'+this.userId]);
  }
}
