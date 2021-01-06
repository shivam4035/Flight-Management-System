export class FlightFareDTO
{

    constructor(

        public flightId:string,
        public businessFare:number,
        public economyFare:number,
        public date:string

    ){}

}