export class FlightSearchDTO
{

    constructor(

        public source:string,
        public destination:string,
        public date:string,
        public berth:string,
        public travellerNo:number

    ){}

}