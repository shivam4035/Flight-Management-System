import { Component, OnInit } from '@angular/core';
import { FlightRegistrationService } from '../flight-registration.service';
import { FlightDTO } from '../flight';
import {formatDate} from '@angular/common';
import { FormControl, Validators,FormGroup } from '@angular/forms';
import { FlightFareDTO } from '../flightFare';
@Component({
  selector: 'app-flight-get-id',
  templateUrl: './flight-get-id.component.html',
  styleUrls: ['./flight-get-id.component.css']
})
export class FlightGetIdComponent implements OnInit {

  siteURL : string = window.location.href;
  splitted = this.siteURL.split("/", 6); 
  
  flightId:string = this.splitted[this.splitted.length-1];
  field:FlightDTO;
  cityCodeList: any;
  selectedSource:string = '';
  selectedDestination:string = '';
  formfieldControl: FormControl ;
  public ownerForm: FormGroup;
  
  myDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  flightFare:FlightFareDTO = new FlightFareDTO(this.flightId,null,null,this.myDate)

  constructor(private service:FlightRegistrationService) { 
    let resp = service.getIATACodes();
    resp.subscribe((data)=>{
     this.cityCodeList = data;
     console.log(this.cityCodeList);
    });

  
    
  }

  ngOnInit(): void {
    
    this.ownerForm = new FormGroup({
      vendor: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      src: new FormControl('', [Validators.required,Validators.pattern("[a-z A-z]*"), Validators.maxLength(30)]),
      dest: new FormControl('', [Validators.required,Validators.pattern("[a-z A-z]*"), Validators.maxLength(30)]),
      depDate: new FormControl('', [Validators.required ,Validators.maxLength(10)]),
      depTime: new FormControl('', [Validators.required, Validators.pattern("[0-9][0-9]:[0-9][0-9]"), Validators.maxLength(5),Validators.minLength(5)]),
      arrDate: new FormControl('', [Validators.required, Validators.maxLength(10)]),
      arrTime: new FormControl('', [Validators.required, ,Validators.pattern("[0-9][0-9]:[0-9][0-9]"),Validators.maxLength(5),Validators.minLength(5)]),
      totalBusinessSeats: new FormControl('', [Validators.required]),
      availableBusinessSeats: new FormControl('', [Validators.required]),
      totalEcocnomySeats: new FormControl('', [Validators.required]),
      availableEconomySeats: new FormControl('', [Validators.required]),
      businessFare: new FormControl('', [Validators.required]),
      economyFare: new FormControl('', [Validators.required]),

    });


    let resp = this.service.getFlightById(this.flightId);
    
     resp.subscribe((data : FlightDTO)=>{
     this.field=data;
     console.log(this.field);
    
     for(let i=0;i<this.cityCodeList.length;i++){
      if (this.cityCodeList[i].cityCode==this.field.flightId.source) {
        this.selectedSource = this.cityCodeList[i].cityName ;
        
      }
      if(this.cityCodeList[i].cityCode==this.field.flightId.destination){
        this.selectedDestination = this.cityCodeList[i].cityName;
      }
    }
    this.findFlightFare()
    });
    
  }

  findFlightFare(){
    let resp2 = this.service.getFlightFareById(this.flightId);
    
    resp2.subscribe((data : FlightFareDTO)=>{
    this.flightFare=data;
    console.log(this.flightFare);

      
  });
}

  public hasError = (controlName: string, errorName: string) =>{
    return this.ownerForm.controls[controlName].hasError(errorName);
  }

    updateFlight(){
      this.field.flightId.source = this.selectedSource;
      this.field.flightId.destination = this.selectedDestination;
      let resp = this.service.updateFlight(this.field,this.flightId);
      resp.subscribe((data)=>{
        console.log(data);
        
       });

       let resp1 = this.service.updateFlightFare(this.flightFare,this.flightId);
       resp1.subscribe((data : FlightFareDTO)=>{
        console.log(data);
        this.flightFare=data;
        alert("Flight and Fare updated with id "+this.flightId);
       });
      
    }

}

