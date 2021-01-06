import { TravellerDTO } from './traveller';
import { FlightCompositeKeyDTO } from './flight-composite-key';

export class BookingsDTO
{

    constructor(

    public  mobile:string,
    public  pnr:string,
    public  seatType:string,
    public  numberOfSeats:number,
    public  listOfTravellers:TravellerDTO[],
    public  flightDetails:FlightCompositeKeyDTO,
    public  bookedDate:string

    ){}

}