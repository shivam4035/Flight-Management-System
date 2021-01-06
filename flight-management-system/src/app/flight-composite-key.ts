export class FlightCompositeKeyDTO {
    constructor(
        public vendor:string,
        public source:string,
        public destination:string,
        public depDate:string,
        public depTime:string
    ){}
}