import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FlightDTO } from '../flight';
import { FlightCompositeKeyDTO } from '../flight-composite-key';
import { FlightRegistrationService } from '../flight-registration.service';
import { Router } from '@angular/router';
import { IATACodeDTO } from '../cityCode';

@Component({
  selector: 'app-flight-add',
  templateUrl: './flight-add.component.html',
  styleUrls: ['./flight-add.component.css']
})
export class FlightAddComponent implements OnInit {

  flightId: FlightCompositeKeyDTO=new FlightCompositeKeyDTO("","","","","");
  flight: FlightDTO=new FlightDTO("",this.flightId,"","",null,null,null,null);
  field : string;
  cityCodeList: IATACodeDTO[];


  constructor(private service: FlightRegistrationService,private route:Router) { 

    let resp = service.getIATACodes();
    resp.subscribe((data : IATACodeDTO[])=>{
     this.cityCodeList = data;
     this.cityCodeList = this.cityCodeList.sort((n1, n2) => {
      return this.naturalCompare(n1.cityName, n2.cityName)
    });
     console.log(this.cityCodeList);
    });
  }

  naturalCompare(a, b) {
    var ax = [], bx = [];
 
    a.replace(/(\d+)|(\D+)/g, function (_, $1, $2) { ax.push([$1 || Infinity, $2 || ""]) });
    b.replace(/(\d+)|(\D+)/g, function (_, $1, $2) { bx.push([$1 || Infinity, $2 || ""]) });
 
    while (ax.length && bx.length) {
      var an = ax.shift();
      var bn = bx.shift();
      var nn = (an[0] - bn[0]) || an[1].localeCompare(bn[1]);
      if (nn) return nn;
    }
 
    return ax.length - bx.length;
 }


  ngOnInit(): void {
  }



  public handler(){
    this.addFlight();
    this.flightFarePage();
  }

  public addFlight(){
     let resp = this.service.addFlight(this.flight);
     resp.subscribe((data : FlightDTO)=>{
     console.log(data.id);
     this.field = data.id;
     this.flightFarePage();
    });
     
  }

  public flightFarePage()
  {
    console.log(this.field);
    sessionStorage.setItem('flightId_fare',this.field);
    this.route.navigate(['/admin/flight/fare']);
  
  }


}
