import { FlightCompositeKeyDTO } from './flight-composite-key';

export class FlightDTO{
    constructor(

        public id:string,
        public flightId:FlightCompositeKeyDTO,
        public arrDate:string,
        public arrTime:string,
        public totalBusiness:number,
        public availableBusiness:number,
        public totalEconomy:number,
        public availableEconomy:number

    ){}
}