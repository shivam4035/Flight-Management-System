import { Component, OnInit } from '@angular/core';
import { IATACodeDTO } from '../cityCode';
import { FlightRegistrationService } from '../flight-registration.service';
import { delay } from 'rxjs/operators';

@Component({
  selector: 'app-iata-dialog',
  templateUrl: './iata-dialog.component.html',
  styleUrls: ['./iata-dialog.component.css']
})
export class IATADialogComponent implements OnInit {

  iata: IATACodeDTO = new IATACodeDTO("","");
  added:boolean=false;
  constructor(private service: FlightRegistrationService) { }

  ngOnInit(): void {
  }

  addIATA(){
    
    this.iata.cityCode.toUpperCase();
    
    let resp = this.service.addIATACodes(this.iata);
    resp.subscribe((data : IATACodeDTO)=>{
      console.log(data);
      this.added = true;
      setTimeout(() => { this.iata.cityCode=""; this.iata.cityName="";
      this.added=false; }, 1000);
      
     });

     
    }
}
