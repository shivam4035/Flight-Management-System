import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule} from '@angular/common/http';
import { FlightRegistrationService } from './flight-registration.service';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { FlightAddComponent } from './flight-add/flight-add.component';
import { FlightFareAddComponent } from './flight-fare-add/flight-fare-add.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatSliderModule } from '@angular/material/slider';

import { from } from 'rxjs';
import { MaterialModule } from './material/material.module'
import { FlightGetIdComponent } from './flight-get-id/flight-get-id.component';
import { IATADialogComponent } from './iata-dialog/iata-dialog.component';
import { DatePipe } from '@angular/common';
import { FlightSearchComponent } from './flight-search/flight-search.component';
import { CustomDatePipe } from './custom.datepipe';
import { AddTravellersComponent } from './add-travellers/add-travellers.component';
import { TicketComponent } from './ticket/ticket.component';
import { BookingHistoryComponent } from './booking-history/booking-history.component'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminComponent,
    FlightAddComponent,
    FlightFareAddComponent,
    FlightGetIdComponent,
    IATADialogComponent,
    FlightSearchComponent,
    CustomDatePipe,
    AddTravellersComponent,
    TicketComponent,
    BookingHistoryComponent
  ],
  entryComponents:[IATADialogComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule
    
  ],
  providers: [FlightRegistrationService,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
