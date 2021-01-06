export class ErrorResponse {
    constructor(
        public timestamp: Date,
        public message: string,
        public details: string
    ){}
}