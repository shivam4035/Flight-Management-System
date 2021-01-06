import { Component, OnInit, TemplateRef } from '@angular/core';
import { FlightRegistrationService } from '../flight-registration.service';
import { FlightDTO } from '../flight';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { IATADialogComponent } from '../iata-dialog/iata-dialog.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  flightId:string="";
  field:any;

  constructor(private route:Router , public dialog: MatDialog, private service:FlightRegistrationService) { }

  ngOnInit(): void {
    
  }

  

  getFlightById(){
    let resp = this.service.getFlightById(this.flightId);
    
     resp.subscribe((data : FlightDTO)=>{
     console.log(this.field);
     this.navigateThen();
     });
    

  }

  navigateThen(){
    this.route.navigate(['/admin/flight/'+this.flightId]);
    window.location.replace('/admin/flight/'+this.flightId);
  }

  addIATACodes(){
    console.log(this.flightId);
    this.dialog.open(IATADialogComponent);
    
    
  }

}
