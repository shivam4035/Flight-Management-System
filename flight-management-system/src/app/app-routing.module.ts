import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FlightAddComponent } from './flight-add/flight-add.component';
import { FlightFareAddComponent } from './flight-fare-add/flight-fare-add.component';
import { FlightGetIdComponent } from './flight-get-id/flight-get-id.component';
import { FlightSearchComponent } from './flight-search/flight-search.component';
import { AddTravellersComponent } from './add-travellers/add-travellers.component';
import { BookingHistoryComponent } from './booking-history/booking-history.component';

const routes: Routes = [

  {path:"",redirectTo:'home',pathMatch:'full'},
  {path:'home',component:HomeComponent},
  {path:'admin/flight', component:FlightAddComponent},
  {path:'admin/flight/fare',component:FlightFareAddComponent},
  {path:'admin/flight/:data', component:FlightGetIdComponent},
  {path:'flight/search',component:FlightSearchComponent},
  {path:'flight/:flightId',component:AddTravellersComponent},
  {path:'bookings/:userId',component:BookingHistoryComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
